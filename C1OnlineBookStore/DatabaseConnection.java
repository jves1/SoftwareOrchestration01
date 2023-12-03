import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
   private static final String URL = "jdbc:mysql://localhost:3306/bookstoreDB";
   private static final String USER = "root";
   private static final String PASSWORD = "";

   public static Connection getConnection() {
      try {
         return DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (SQLException e) {
         throw new RuntimeException("Error connecting to the database", e);
      }
   }
   public static void main(String[] args) {
      // Test database connection
      try {
         Connection connection = DatabaseConnection.getConnection();
         if (connection != null) {
            System.out.println("Connection successful!");
            connection.close();
         } else {
            System.out.println("Failed to connect to the database.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
