import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateMemberScreen extends JFrame {
   private JTextField txtMemberId;
   private JTextField txtFirstName;
   private JTextField txtLastName;
   private JComboBox<String> comboMembershipLevel;
   private JComboBox<String> comboStatus;
   private JTextField txtEmail;
   private JTextField txtDuesPaid;
   private JTextField txtDuesPaidDate;
   private JTextField txtRenewalDate;
   private JButton btnUpdateMember;
   private JButton btnSearchMember;
   private DatabaseManager dbManager;

   public UpdateMemberScreen(DatabaseManager dbManager) {
      this.dbManager = dbManager;
      initializeUI();
   }

   private void initializeUI() {
      setTitle("Update Member");
      setSize(400, 500);
      setLayout(new GridLayout(11, 2));
   
        // Initialize components
      txtMemberId = new JTextField();
      txtFirstName = new JTextField();
      txtLastName = new JTextField();
      comboMembershipLevel = new JComboBox<>(new String[]{"Level1", "Level2", "Level3"});
      comboStatus = new JComboBox<>(new String[]{"Active", "Inactive"});
      txtEmail = new JTextField();
      txtDuesPaid = new JTextField();
      txtDuesPaidDate = new JTextField();
      txtRenewalDate = new JTextField();
      btnUpdateMember = new JButton("Update Member");
      btnSearchMember = new JButton("Search Member");
   
        // Add components to frame
      add(new JLabel("Member ID:"));
      add(txtMemberId);
      add(btnSearchMember);
      add(new JLabel()); // Placeholder
      add(new JLabel("First Name:"));
      add(txtFirstName);
      add(new JLabel("Last Name:"));
      add(txtLastName);
      add(new JLabel("Membership Level:"));
      add(comboMembershipLevel);
      add(new JLabel("Status:"));
      add(comboStatus);
      add(new JLabel("Email:"));
      add(txtEmail);
      add(new JLabel("Dues Paid:"));
      add(txtDuesPaid);
      add(new JLabel("Dues Paid Date:"));
      add(txtDuesPaidDate);
      add(new JLabel("Renewal Date:"));
      add(txtRenewalDate);
      add(btnUpdateMember);
   
        // Action listeners for the buttons
      btnUpdateMember.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               updateMember();
            }
         });
   
      btnSearchMember.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               searchMember();
            }
         });
   }

   private void updateMember() {
      try {
         Member updatedMember = new Member(
                Integer.parseInt(txtMemberId.getText()),
                txtFirstName.getText(),
                txtLastName.getText(),
                (String) comboMembershipLevel.getSelectedItem(),
                (String) comboStatus.getSelectedItem(),
                txtEmail.getText(),
                Double.parseDouble(txtDuesPaid.getText()),
                txtDuesPaidDate.getText(),
                txtRenewalDate.getText()
            );
      
         if (dbManager.updateMember(updatedMember)) {
            JOptionPane.showMessageDialog(this, "Member updated successfully!");
         } else {
            JOptionPane.showMessageDialog(this, "Failed to update member.");
         }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(this, "Error updating member: " + ex.getMessage());
      } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Invalid input. Please check the data.");
      }
   }

   private void searchMember() {
      try {
         int memberId = Integer.parseInt(txtMemberId.getText());
         Member member = dbManager.getMemberById(memberId);
      
         if (member != null) {
            // Populate the form fields with the member data
            txtFirstName.setText(member.getFirstName());
            txtLastName.setText(member.getLastName());
            comboMembershipLevel.setSelectedItem(member.getMembershipLevel());
            comboStatus.setSelectedItem(member.getStatus());
            txtEmail.setText(member.getEmail());
            txtDuesPaid.setText(String.valueOf(member.getDuesPaid()));
            txtDuesPaidDate.setText(member.getDuesPaidDate());
            txtRenewalDate.setText(member.getRenewalDate());
         } else {
            JOptionPane.showMessageDialog(this, "Member not found.");
         }
      } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Invalid Member ID. Please enter a numeric value.");
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(this, "Error retrieving member data: " + ex.getMessage());
      }
   }

    // Main method for standalone testing
   public static void main(String[] args) {
      DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/ClubMembershipDB", "root", "");
      UpdateMemberScreen screen = new UpdateMemberScreen(dbManager);
      screen.setVisible(true);
   }
}
