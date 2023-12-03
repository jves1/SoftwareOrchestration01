public class ClubMembershipPlanner {
    public static void main(String[] args) {
        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/ClubMembershipDB";
        String jdbcUsername = "root";
        String jdbcPassword = "";

        // Create an instance of the DatabaseManager
        DatabaseManager dbManager = new DatabaseManager(jdbcURL, jdbcUsername, jdbcPassword);

        // Create and display the main menu screen
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuScreen menu = new MenuScreen(dbManager);
                menu.setVisible(true);
            }
        });
    }
}
