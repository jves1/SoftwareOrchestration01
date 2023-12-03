import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteMemberScreen extends JFrame {
    private JTextField txtMemberId;
    private JButton btnDeleteMember;
    private DatabaseManager dbManager;

    public DeleteMemberScreen(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Delete Member");
        setSize(300, 200);
        setLayout(new GridLayout(2, 2));

        txtMemberId = new JTextField();
        btnDeleteMember = new JButton("Delete Member");

        add(new JLabel("Member ID:"));
        add(txtMemberId);
        add(btnDeleteMember);

        btnDeleteMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void deleteMember() {
        try {
            int memberId = Integer.parseInt(txtMemberId.getText());
            Member member = new Member(memberId, null, null, null, null, null, 0, null, null);
            boolean isDeleted = dbManager.deleteMember(member);

            if (isDeleted) {
                JOptionPane.showMessageDialog(this, "Member deleted successfully!");
                txtMemberId.setText(""); // Clear the input field
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete member. Please check the Member ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Member ID. Please enter a numeric value.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting member: " + ex.getMessage());
        }
    }

    // Main method for standalone testing
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/ClubMembershipDB", "root", "");
        DeleteMemberScreen screen = new DeleteMemberScreen(dbManager);
        screen.setVisible(true);
    }
}
