import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
   // Constructor
   public MainMenu() {
      // Setting up the frame
      setTitle("Club Membership Management");
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null); // Center the window
   
      // Creating buttons
      JButton btnAddMember = new JButton("Add Member");
      JButton btnUpdateMember = new JButton("Update Member");
      JButton btnDeleteMember = new JButton("Delete Member");
      JButton btnGenerateReport = new JButton("Generate Report");
   
      // Setting up the layout
      setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column, 10px gaps
      add(btnAddMember);
      add(btnUpdateMember);
      add(btnDeleteMember);
      add(btnGenerateReport);
   
      // Adding action listeners to buttons
      btnAddMember.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new AddMember().setVisible(true);
            }
         });
   
      btnUpdateMember.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new UpdateMember().setVisible(true);
            }
         });
   
      btnDeleteMember.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new DeleteMember().setVisible(true);
            }
         });
   
      btnGenerateReport.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new Report().setVisible(true);
            }
         });
   }

   // Main method to launch the application
   public static void main(String[] args) {
      EventQueue.invokeLater(
         new Runnable() {
            @Override
            public void run() {
               new MainMenu().setVisible(true);
            }
         });
   }
}
