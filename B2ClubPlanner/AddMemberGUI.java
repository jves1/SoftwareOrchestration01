import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class AddMemberGUI extends JFrame {

    private JTextField firstNameField, lastNameField, emailAddressField;
    private JComboBox<String> membershipLevelField, statusField;
    private JCheckBox duesPaidField;
    private JSpinner duesPaidDateField, renewalDateField;
    private DatabaseUtility dbUtility;

    public AddMemberGUI() {
        dbUtility = new DatabaseUtility(); // Initialize database utility

        setTitle("Add Member");
        setSize(600, 400);
        setLayout(new GridLayout(10, 2, 5, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Form fields
        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        String[] membershipLevels = {"Level1", "Level2", "Level3"};
        add(new JLabel("Membership Level:"));
        membershipLevelField = new JComboBox<>(membershipLevels);
        add(membershipLevelField);

        String[] statuses = {"active", "inactive"};
        add(new JLabel("Status:"));
        statusField = new JComboBox<>(statuses);
        add(statusField);

        add(new JLabel("Email Address:"));
        emailAddressField = new JTextField();
        add(emailAddressField);

        add(new JLabel("Dues Paid:"));
        duesPaidField = new JCheckBox();
        add(duesPaidField);

        add(new JLabel("Dues Paid Date:"));
        duesPaidDateField = new JSpinner(new SpinnerDateModel());
        add(duesPaidDateField);

        add(new JLabel("Renewal Date:"));
        renewalDateField = new JSpinner(new SpinnerDateModel());
        add(renewalDateField);

        JButton submitButton = new JButton("Submit");
        add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitMember();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void submitMember() {
        // Collect data from form fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String membershipLevel = (String) membershipLevelField.getSelectedItem();
        String status = (String) statusField.getSelectedItem();
        String email = emailAddressField.getText();
        boolean duesPaid = duesPaidField.isSelected();
        Date duesPaidDate = new Date(((java.util.Date) duesPaidDateField.getValue()).getTime());
        Date renewalDate = new Date(((java.util.Date) renewalDateField.getValue()).getTime());

        // Call DatabaseUtility to add member
        dbUtility.addMember(firstName, lastName, membershipLevel, status, email, duesPaid, duesPaidDate, renewalDate);

        JOptionPane.showMessageDialog(this, "Member Added Successfully!");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddMemberGUI().setVisible(true);
            }
        });
    }
}
