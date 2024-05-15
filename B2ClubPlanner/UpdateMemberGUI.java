import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class UpdateMemberGUI extends JFrame {

   private JTextField memberIdField, firstNameField, lastNameField, emailAddressField;
   private JComboBox<String> membershipLevelField, statusField;
   private JCheckBox duesPaidField;
   private JSpinner duesPaidDateField, renewalDateField;
   private JButton updateButton, searchButton;
   private DatabaseUtility dbUtility;

   public UpdateMemberGUI() {
      dbUtility = new DatabaseUtility();
   
      setTitle("Update Member");
      setSize(600, 400);
      setLayout(new BorderLayout(5, 5));
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null);
      
      
   
      // Main panel that holds all form elements
      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
   
      // Panel for member ID and search button
      JPanel memberIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      memberIdPanel.add(new JLabel("Member ID:"));
      memberIdField = new JTextField(20);
      memberIdPanel.add(memberIdField);
      searchButton = new JButton("Search");
      memberIdPanel.add(searchButton);
      mainPanel.add(memberIdPanel);
   
      // Panel for the rest of the form
      JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5)); // 8 rows for the fields
   
      formPanel.add(new JLabel("First Name:"));
      firstNameField = new JTextField();
      formPanel.add(firstNameField);
   
      formPanel.add(new JLabel("Last Name:"));
      lastNameField = new JTextField();
      formPanel.add(lastNameField);
   
      String[] membershipLevels = {"Level1", "Level2", "Level3"};
      formPanel.add(new JLabel("Membership Level:"));
      membershipLevelField = new JComboBox<>(membershipLevels);
      formPanel.add(membershipLevelField);
   
      String[] statuses = {"active", "inactive"};
      formPanel.add(new JLabel("Status:"));
      statusField = new JComboBox<>(statuses);
      formPanel.add(statusField);
   
      formPanel.add(new JLabel("Email Address:"));
      emailAddressField = new JTextField();
      formPanel.add(emailAddressField);
   
      formPanel.add(new JLabel("Dues Paid:"));
      duesPaidField = new JCheckBox();
      formPanel.add(duesPaidField);
   
      formPanel.add(new JLabel("Dues Paid Date:"));
      duesPaidDateField = new JSpinner(new SpinnerDateModel());
      formPanel.add(duesPaidDateField);
   
      formPanel.add(new JLabel("Renewal Date:"));
      renewalDateField = new JSpinner(new SpinnerDateModel());
      formPanel.add(renewalDateField);
   
      mainPanel.add(formPanel);
   
      // Add the main panel to the center of the BorderLayout
      add(mainPanel, BorderLayout.CENTER);
   
      // Panel for the bottom buttons
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      updateButton = new JButton("Update");
      buttonPanel.add(updateButton);
      JButton cancelButton = new JButton("Cancel");
      buttonPanel.add(cancelButton);
   
      // Add button panel to the south of the BorderLayout
      add(buttonPanel, BorderLayout.SOUTH);
   
      // Action listeners for buttons
      searchButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               searchMember();
            }
         });
   
      updateButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               updateMember();
            }
         });
   
      cancelButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
         });
   
      setFieldsEnabled(false);
   }
   private void searchMember() {
      try {
         int memberId = Integer.parseInt(memberIdField.getText());
         Member member = dbUtility.getMember(memberId);
      
         if (member != null) {
            setMemberData(member);
            setFieldsEnabled(true);
         } else {
            JOptionPane.showMessageDialog(this, "Member not found!");
         }
      } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Invalid Member ID!");
      }
   }

   private void setMemberData(Member member) {
      firstNameField.setText(member.getFirstName());
      lastNameField.setText(member.getLastName());
      membershipLevelField.setSelectedItem(member.getMembershipLevel());
      statusField.setSelectedItem(member.getStatus());
      emailAddressField.setText(member.getEmailAddress());
      duesPaidField.setSelected(member.isDuesPaid());
      duesPaidDateField.setValue(member.getDuesPaidDate());
      renewalDateField.setValue(member.getRenewalDate());
   }

   private void setFieldsEnabled(boolean enabled) {
      firstNameField.setEnabled(enabled);
      lastNameField.setEnabled(enabled);
      membershipLevelField.setEnabled(enabled);
      statusField.setEnabled(enabled);
      emailAddressField.setEnabled(enabled);
      duesPaidField.setEnabled(enabled);
      duesPaidDateField.setEnabled(enabled);
      renewalDateField.setEnabled(enabled);
      updateButton.setEnabled(enabled);
   }

   private void updateMember() {
      try {
         int memberId = Integer.parseInt(memberIdField.getText());
         String firstName = firstNameField.getText();
         String lastName = lastNameField.getText();
         String membershipLevel = (String) membershipLevelField.getSelectedItem();
         String status = (String) statusField.getSelectedItem();
         String email = emailAddressField.getText();
         boolean duesPaid = duesPaidField.isSelected();
         Date duesPaidDate = new Date(((java.util.Date) duesPaidDateField.getValue()).getTime());
         Date renewalDate = new Date(((java.util.Date) renewalDateField.getValue()).getTime());
      
         dbUtility.updateMember(memberId, firstName, lastName, membershipLevel, status, email, duesPaid, duesPaidDate, renewalDate);
      
         JOptionPane.showMessageDialog(this, "Member Updated Successfully!");
      } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Invalid input!");
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(
         new Runnable() {
            @Override
            public void run() {
               new UpdateMemberGUI().setVisible(true);
            }
         });
   }
}
