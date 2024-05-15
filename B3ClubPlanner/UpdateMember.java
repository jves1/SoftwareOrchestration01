import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;

public class UpdateMember extends JFrame {
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
    public UpdateMember() {
        // Initialize DatabaseUtil instance
        this.dbUtil = new DatabaseUtil();

        // Setting up the frame
        setTitle("Update Member");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // North Panel for Title
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Update Member"));
        add(northPanel, BorderLayout.NORTH);

        // Center Panel for Form
        JPanel centerPanel = new JPanel(new GridLayout(0, 2)); // Dynamic row, 2 columns

        txtMemberID = new JTextField(10);
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
        JButton btnSearch = new JButton("Search");
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");
        southPanel.add(btnSearch);
        southPanel.add(btnSubmit);
        southPanel.add(btnCancel);
        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = txtMemberID.getText().trim();
                if (memberID.isEmpty()) {
                    JOptionPane.showMessageDialog(UpdateMember.this, "Please enter a Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Member member = dbUtil.getMember(memberID);
                if (member != null) {
                    txtFirstName.setText(member.getFirstName());
                    txtLastName.setText(member.getLastName());
                    txtEmail.setText(member.getEmail());
                    txtDuesPaid.setText(member.getDuesPaid().toString());
                    txtDuesPaidDate.setText(member.getDuesPaidDate().toString());
                    txtRenewalDate.setText(member.getRenewalDate().toString());
                    comboMembershipLevel.setSelectedItem(member.getMembershipLevel());
                    comboStatus.setSelectedItem(member.getStatus());
                } else {
                    JOptionPane.showMessageDialog(UpdateMember.this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create Member object with updated data
                Member updatedMember = new Member(
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

                // Update the member in the database
                boolean success = dbUtil.updateMember(updatedMember);
                if (success) {
                    JOptionPane.showMessageDialog(UpdateMember.this, "Member updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(UpdateMember.this, "Error updating member.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window without saving
            }
        });
    }

    // Main method for testing
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new UpdateMember().setVisible(true);
        });
    }
}
