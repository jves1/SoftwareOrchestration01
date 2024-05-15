import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteMember extends JFrame {
    private JTextField txtMemberID;
    private DatabaseUtil dbUtil; // DatabaseUtil instance

    // Constructor
    public DeleteMember() {
        // Initialize DatabaseUtil instance
        this.dbUtil = new DatabaseUtil();

        // Setting up the frame
        setTitle("Delete Member");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // North Panel for Title
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Delete Member"));
        add(northPanel, BorderLayout.NORTH);

        // Center Panel for Member ID input
        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Member ID:"));
        txtMemberID = new JTextField(10);
        centerPanel.add(txtMemberID);
        add(centerPanel, BorderLayout.CENTER);

        // South Panel for Buttons
        JPanel southPanel = new JPanel();
        JButton btnDelete = new JButton("Delete");
        JButton btnCancel = new JButton("Cancel");
        southPanel.add(btnDelete);
        southPanel.add(btnCancel);
        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = txtMemberID.getText().trim();
                if (memberID.isEmpty()) {
                    JOptionPane.showMessageDialog(DeleteMember.this, "Please enter a Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(DeleteMember.this, "Are you sure you want to delete this member?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = dbUtil.deleteMember(memberID);
                    if (success) {
                        JOptionPane.showMessageDialog(DeleteMember.this, "Member deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(DeleteMember.this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window without deleting
            }
        });
    }

    // Main method for testing
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new DeleteMember().setVisible(true);
        });
    }
}
