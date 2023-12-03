import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ReportGenerator extends JFrame {
    private DatabaseManager dbManager;
    private JButton btnActiveMembers;
    private JButton btnInactiveMembers;
    private JButton btnMembersByLevel;
    private JTextArea textAreaReport;

    public ReportGenerator(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Membership Reports");
        setSize(500, 400);
        setLayout(new BorderLayout());

        btnActiveMembers = new JButton("Active Members");
        btnInactiveMembers = new JButton("Inactive Members");
        btnMembersByLevel = new JButton("Members by Level");

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnActiveMembers);
        panelButtons.add(btnInactiveMembers);
        panelButtons.add(btnMembersByLevel);

        textAreaReport = new JTextArea();
        textAreaReport.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaReport);

        add(panelButtons, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action listeners for the buttons
        btnActiveMembers.addActionListener(e -> generateReport("Active"));
        btnInactiveMembers.addActionListener(e -> generateReport("Inactive"));
        btnMembersByLevel.addActionListener(e -> generateReportByLevel());

    }

    private void generateReport(String status) {
        try {
            List<Member> members = dbManager.getMembersByStatus(status);
            displayReport(members);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
        }
    }

    private void generateReportByLevel() {
        try {
            List<Member> membersLevel1 = dbManager.getMembersByLevel("Level1");
            List<Member> membersLevel2 = dbManager.getMembersByLevel("Level2");
            List<Member> membersLevel3 = dbManager.getMembersByLevel("Level3");

            textAreaReport.setText("Level 1 Members:\n");
            displayReport(membersLevel1);
            textAreaReport.append("\nLevel 2 Members:\n");
            displayReport(membersLevel2);
            textAreaReport.append("\nLevel 3 Members:\n");
            displayReport(membersLevel3);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
        }
    }

    private void displayReport(List<Member> members) {
        StringBuilder reportBuilder = new StringBuilder();
        for (Member member : members) {
            reportBuilder.append(member.toString()).append("\n");
        }
        textAreaReport.append(reportBuilder.toString());
    }

    // Main method for standalone testing
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/ClubMembershipDB", "root", "");
        ReportGenerator reportGenerator = new ReportGenerator(dbManager);
        reportGenerator.setVisible(true);
    }
}
