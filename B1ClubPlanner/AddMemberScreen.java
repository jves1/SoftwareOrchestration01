import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMemberScreen extends JFrame {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> comboMembershipLevel;
    private JComboBox<String> comboStatus;
    private JTextField txtEmail;
    private JTextField txtDuesPaid;
    private JTextField txtDuesPaidDate;
    private JTextField txtRenewalDate;
    private JButton btnAddMember;
    private DatabaseManager dbManager;

    public AddMemberScreen(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Add New Member");
        setSize(300, 400);
        setLayout(new GridLayout(9, 2));

        // Initialize components
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        comboMembershipLevel = new JComboBox<>(new String[]{"Level1", "Level2", "Level3"});
        comboStatus = new JComboBox<>(new String[]{"Active", "Inactive"});
        txtEmail = new JTextField();
        txtDuesPaid = new JTextField();
        txtDuesPaidDate = new JTextField();
        txtRenewalDate = new JTextField();
        btnAddMember = new JButton("Add Member");

        // Add components to frame
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
        add(btnAddMember);

        // Action listener for the button
        btnAddMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });
    }

    private void addMember() {
        try {
            Member newMember = new Member(
                0, // ID will be auto-generated by the database
                txtFirstName.getText(),
                txtLastName.getText(),
                (String) comboMembershipLevel.getSelectedItem(),
                (String) comboStatus.getSelectedItem(),
                txtEmail.getText(),
                Double.parseDouble(txtDuesPaid.getText()),
                txtDuesPaidDate.getText(),
                txtRenewalDate.getText()
            );

            if (dbManager.insertMember(newMember)) {
                JOptionPane.showMessageDialog(this, "Member added successfully!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add member.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding member: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid dues paid. Please enter a valid number.");
        }
    }

    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        comboMembershipLevel.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        txtEmail.setText("");
        txtDuesPaid.setText("");
        txtDuesPaidDate.setText("");
        txtRenewalDate.setText("");
    }

    // Main method for standalone testing
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/ClubMembershipDB", "root", "");
        AddMemberScreen screen = new AddMemberScreen(dbManager);
        screen.setVisible(true);
    }
}
