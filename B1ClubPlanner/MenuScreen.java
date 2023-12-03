import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends JFrame {

    private DatabaseManager dbManager;

    public MenuScreen(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Club Membership Planner - Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1)); // Grid layout for the menu options

        JButton btnAddMember = new JButton("Add Member");
        JButton btnUpdateMember = new JButton("Update Member");
        JButton btnDeleteMember = new JButton("Delete Member");
        JButton btnGenerateReport = new JButton("Generate Reports");

        btnAddMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddMemberScreen();
            }
        });

        btnUpdateMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUpdateMemberScreen();
            }
        });

        btnDeleteMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDeleteMemberScreen();
            }
        });

        btnGenerateReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openReportGeneratorScreen();
            }
        });

        add(btnAddMember);
        add(btnUpdateMember);
        add(btnDeleteMember);
        add(btnGenerateReport);

        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void openAddMemberScreen() {
        AddMemberScreen addMemberScreen = new AddMemberScreen(dbManager);
        addMemberScreen.setVisible(true);
    }

    private void openUpdateMemberScreen() {
        UpdateMemberScreen updateMemberScreen = new UpdateMemberScreen(dbManager);
        updateMemberScreen.setVisible(true);
    }

    private void openDeleteMemberScreen() {
        DeleteMemberScreen deleteMemberScreen = new DeleteMemberScreen(dbManager);
        deleteMemberScreen.setVisible(true);
    }

    private void openReportGeneratorScreen() {
        ReportGenerator reportGenerator = new ReportGenerator(dbManager);
        reportGenerator.setVisible(true);
    }

    public static void main(String[] args) {
        // Assuming the DatabaseManager is initialized here with appropriate parameters
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/ClubMembershipDB", "root", "");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuScreen(dbManager).setVisible(true);
            }
        });
    }
}
