import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Report extends JFrame {
    private JComboBox<String> comboReportType;
    private JTextArea txtReportArea;
    private DatabaseUtil dbUtil; // DatabaseUtil instance

    // Constructor
    public Report() {
        // Initialize DatabaseUtil instance
        this.dbUtil = new DatabaseUtil();

        // Setting up the frame
        setTitle("Membership Reports");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // North Panel for Title and Report Type Selection
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Select Report Type:"));
        comboReportType = new JComboBox<>(new String[]{"All Members", "Active Members", "Inactive Members", "Level 1", "Level 2", "Level 3"});
        northPanel.add(comboReportType);
        JButton btnGenerate = new JButton("Generate Report");
        northPanel.add(btnGenerate);
        add(northPanel, BorderLayout.NORTH);

        // Center Panel for Displaying Report
        txtReportArea = new JTextArea();
        txtReportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtReportArea);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listener for Generate Report Button
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reportType = (String) comboReportType.getSelectedItem();
                txtReportArea.setText(""); // Clear previous report

                List<Member> reportData = dbUtil.generateReport(reportType);
                if (reportData != null && !reportData.isEmpty()) {
                    StringBuilder reportBuilder = new StringBuilder();
                    for (Member member : reportData) {
                        reportBuilder.append(member.toString()).append("\n");
                    }
                    txtReportArea.setText(reportBuilder.toString());
                } else {
                    txtReportArea.setText("No data found for the selected report type.");
                }
            }
        });
    }

    // Main method for testing
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Report().setVisible(true);
        });
    }
}
