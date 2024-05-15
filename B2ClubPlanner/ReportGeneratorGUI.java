import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class ReportGeneratorGUI extends JFrame {
    private DatabaseUtility dbUtility;
    private JTable membersTable;
    private JButton refreshButton;

    public ReportGeneratorGUI() {
        dbUtility = new DatabaseUtility();

        setTitle("Member Report Generator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the JTable and its model
        membersTable = new JTable();
        membersTable.setModel(new DefaultTableModel(
            new Object[]{"Member ID", "First Name", "Last Name", "Email", "Membership Level"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(membersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh button to load data
        refreshButton = new JButton("Refresh Report");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReportData();
            }
        });
        add(refreshButton, BorderLayout.SOUTH);

        // Load initial data
        loadReportData();
    }

    private void loadReportData() {
        // Fetch data from database and populate the table
        DefaultTableModel model = (DefaultTableModel) membersTable.getModel();
        model.setRowCount(0); // Clear existing data

        java.util.List<Member> members = dbUtility.getAllMembers(); // Implement this method in DatabaseUtility
        for (Member member : members) {
            model.addRow(new Object[]{
                member.getMemberId(), 
                member.getFirstName(), 
                member.getLastName(), 
                member.getEmailAddress(), 
                member.getMembershipLevel()
            });
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReportGeneratorGUI().setVisible(true);
            }
        });
    }
}
