import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplication extends JFrame {

    public MainApplication() {
        // Set up the frame
        setTitle("Club Membership Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create buttons for each operation
        JButton addButton = new JButton("Add Member");
        JButton updateButton = new JButton("Update Member");
        JButton deleteButton = new JButton("Delete Member");
        JButton reportButton = new JButton("Generate Report");

        // Set up layout
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column, 10px gaps

        // Add buttons to the frame
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(reportButton);

        // Action Listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Add Member GUI
                new AddMemberGUI().setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Update Member GUI
                new UpdateMemberGUI().setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Delete Member GUI
                new DeleteMemberGUI().setVisible(true);
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Report Generator GUI
                new ReportGeneratorGUI().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // Run the application
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApplication().setVisible(true);
            }
        });
    }
}
