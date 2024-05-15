import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;


public class AddMember extends JFrame {
   private static int lastMemberID;
   private JTextField txtMemberID;
   private JTextField txtFirstName;
   private JTextField txtLastName;
   private JTextField txtEmail;
   private JTextField txtDuesPaid;
   private JTextField txtDuesPaidDate;
   private JTextField txtRenewalDate;
   private JComboBox<String> comboMembershipLevel;
   private JComboBox<String> comboStatus;
   private DatabaseUtil dbUtil; // DatabaseUtil instance

   // Constructor
   public AddMember() {
      // Initialize DatabaseUtil instance
      this.dbUtil = new DatabaseUtil();
   
      // Setting up the frame
      setTitle("Add Member");
      setSize(500, 400);
      setLayout(new BorderLayout());
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null); // Center the window
   
      // North Panel for Title
      JPanel northPanel = new JPanel();
      northPanel.add(new JLabel("Add New Member"));
      add(northPanel, BorderLayout.NORTH);
   
      // Center Panel for Form
      JPanel centerPanel = new JPanel(new GridLayout(0, 2)); // Dynamic row, 2 columns
   
      txtMemberID = new JTextField(6);
      txtMemberID.setEditable(false);
     // Initialize Member ID
      initializeMemberID();
      
      txtFirstName = new JTextField(10);
      txtLastName = new JTextField(10);
      txtEmail = new JTextField(10);
      txtDuesPaid = new JTextField(10);
      txtDuesPaidDate = new JTextField(10);
      txtRenewalDate = new JTextField(10);
      comboMembershipLevel = new JComboBox<>(new String[]{"Level1", "Level2", "Level3"});
      comboStatus = new JComboBox<>(new String[]{"Active", "Inactive"});
   
      centerPanel.add(new JLabel("Member ID:"));
      centerPanel.add(txtMemberID);
      centerPanel.add(new JLabel("First Name:"));
      centerPanel.add(txtFirstName);
      centerPanel.add(new JLabel("Last Name:"));
      centerPanel.add(txtLastName);
      centerPanel.add(new JLabel("Email:"));
      centerPanel.add(txtEmail);
      centerPanel.add(new JLabel("Dues Paid:"));
      centerPanel.add(txtDuesPaid);
      centerPanel.add(new JLabel("Dues Paid Date (yyyy-mm-dd):"));
      centerPanel.add(txtDuesPaidDate);
      centerPanel.add(new JLabel("Renewal Date (yyyy-mm-dd):"));
      centerPanel.add(txtRenewalDate);
      centerPanel.add(new JLabel("Membership Level:"));
      centerPanel.add(comboMembershipLevel);
      centerPanel.add(new JLabel("Status:"));
      centerPanel.add(comboStatus);
   
      add(centerPanel, BorderLayout.CENTER);
   
      // South Panel for Buttons
      JPanel southPanel = new JPanel();
      JButton btnSubmit = new JButton("Submit");
      JButton btnCancel = new JButton("Cancel");
      southPanel.add(btnSubmit);
      southPanel.add(btnCancel);
      add(southPanel, BorderLayout.SOUTH);
   
      // Action Listeners
      btnSubmit.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Create Member object with data from input fields
               Member newMember = new Member(
                  txtMemberID.getText(),
                  txtFirstName.getText(),
                  txtLastName.getText(),
                  (String) comboMembershipLevel.getSelectedItem(),
                  (String) comboStatus.getSelectedItem(),
                  txtEmail.getText(),
                  new BigDecimal(txtDuesPaid.getText()),
                  Date.valueOf(txtDuesPaidDate.getText()),
                  Date.valueOf(txtRenewalDate.getText())
                  );
            
            // Add the new member to the database
               boolean success = dbUtil.addMember(newMember);
               if (success) {
                  JOptionPane.showMessageDialog(AddMember.this, "Member added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                  initializeMemberID(); // Increment for next member
                  clearForm();
               } else {
                  JOptionPane.showMessageDialog(AddMember.this, "Error adding member.", "Error", JOptionPane.ERROR_MESSAGE);
               }
            }
         });
   
      btnCancel.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               clearForm();
               dispose();
            }
         });
   }

 
 
   private void clearForm() {
      txtFirstName.setText("");
      txtLastName.setText("");
      txtEmail.setText("");
      txtDuesPaid.setText("");
      txtDuesPaidDate.setText("");
      txtRenewalDate.setText("");
      comboMembershipLevel.setSelectedIndex(0);
      comboStatus.setSelectedIndex(0);
   }
   
   private void initializeMemberID() {
      int lastMemberID = dbUtil.getLastMemberID();
      if (lastMemberID == -1) {
         lastMemberID = 1; // Start with '000001' if the table is empty
      } else {
         lastMemberID++; // Increment the last member ID
      }
      txtMemberID.setText(formatMemberID(lastMemberID));
   }

   private String formatMemberID(int memberID) {
      DecimalFormat df = new DecimalFormat("000000");
      return df.format(memberID);
   }

   // Main method for testing
   public static void main(String[] args) {
      EventQueue.invokeLater(
         () -> {
            new AddMember().setVisible(true);
         });
   }
   
   
}
