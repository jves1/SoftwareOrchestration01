import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteMemberGUI extends JFrame {
   private JTextField memberIdField, firstNameField, lastNameField;
   private JButton deleteButton, searchButton;
   private DatabaseUtility dbUtility;
   //private JPanel memberDetailsPanel;   
   private JPanel memberDetailsPanel;

   
   public DeleteMemberGUI() {
      dbUtility = new DatabaseUtility();
   
      setTitle("Delete Member");
      setSize(600, 400);
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
      JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2 rows for the fields
   
      formPanel.add(new JLabel("First Name:"));
      firstNameField = new JTextField();
      formPanel.add(firstNameField);
   
      formPanel.add(new JLabel("Last Name:"));
      lastNameField = new JTextField();
      formPanel.add(lastNameField);
      
      firstNameField.setEditable(false);
      lastNameField.setEditable(false);
   
   
      mainPanel.add(formPanel);
   
      // Add the main panel to the center of the BorderLayout
      add(mainPanel, BorderLayout.CENTER);
   
      // Panel for the bottom buttons
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        
    // Initialize buttons
      deleteButton = new JButton("Delete");
      //searchButton = new JButton("Search");
      deleteButton.setEnabled(false);
      
    // Bottom panel for delete button
      //JPanel buttonPanel = new JPanel();
      buttonPanel.add(deleteButton);
   
      add(buttonPanel, BorderLayout.SOUTH);
   
    // Add action listeners to buttons
      searchButton.addActionListener(e -> searchMember());
      deleteButton.addActionListener(e -> deleteMember());
   
   }

   private void searchMember() {
    // Clear previous member details
      firstNameField.setText("");
      lastNameField.setText("");

      deleteButton.setEnabled(false);
   
    // Validate input and search for the member in the database
      String memberIdText = memberIdField.getText();
      if (memberIdText.isEmpty()) {
         JOptionPane.showMessageDialog(this, "Member ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
         return;
      }
   
      try {
         int memberId = Integer.parseInt(memberIdText);
         boolean exists = dbUtility.checkMemberExists(memberId);
         if (exists) {
            Member member = dbUtility.getMember(memberId); // Make sure this method is implemented
            displayMemberDetails(member);
            deleteButton.setEnabled(true);
         } else {
            JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
         }
      } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Invalid Member ID format.", "Error", JOptionPane.ERROR_MESSAGE);
      } finally {
        
      }
   }


   private void deleteMember() {
      // Delete the member from the database
      int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
         try {
            int memberId = Integer.parseInt(memberIdField.getText());
            dbUtility.deleteMember(memberId);
            JOptionPane.showMessageDialog(this, "Member deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            memberIdField.setText("");
            deleteButton.setEnabled(false);
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Member ID format.", "Error", JOptionPane.ERROR_MESSAGE);
         }
      }
   }
   
   private void displayMemberDetails(Member member) {
        // Assuming member is the object retrieved from the database
      if (member != null) {
         firstNameField.setText(member.getFirstName());
         System.out.println(firstNameField.getText());
         lastNameField.setText(member.getLastName());
         firstNameField.setEnabled(false);
         lastNameField.setEnabled(false);         
      }       
        
   }
       
   public static void main(String[] args) {
      EventQueue.invokeLater(
         new Runnable() {
            @Override
            public void run() {
               new DeleteMemberGUI().setVisible(true);
            }
         });
   }
}
