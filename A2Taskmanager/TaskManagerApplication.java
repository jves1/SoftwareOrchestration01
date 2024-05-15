public class TaskManagerApplication {

    public static void main(String[] args) {
        // Database credentials and URL (should be replaced with actual values)
        String databaseURL = "jdbc:mysql://localhost:3306/TaskManagerA2";
        String username = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        try {
            // Initialize the database manager
            TaskDatabaseManager dbManager = new TaskDatabaseManager(databaseURL, username, password);

            // Initialize and display the user interface
            javax.swing.SwingUtilities.invokeLater(() -> new TaskManagerUI(dbManager));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to start the application: " + e.getMessage());
        }
    }
}
