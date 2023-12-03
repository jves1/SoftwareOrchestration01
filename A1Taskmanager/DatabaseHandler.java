import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate; // Import statement for LocalDate


public class DatabaseHandler {
   private Connection connection;

   // Constructor
   public DatabaseHandler() {
      // Initialize the database connection
      connectDatabase();
   }

   // Method to establish a database connection
   private void connectDatabase() {
      try {
         // Assuming MySQL JDBC Driver is already installed and added to the classpath
         String url = "jdbc:mysql://localhost:3306/TaskManagerDB"; // Replace with your database URL
         String user = "root"; // Replace with your database username
         String password = ""; // Replace with your database password
      
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to add a task to the database
   public void addTask(Task task) {
      String query = "INSERT INTO tasks (title, description, priority, deadline, completion_status) VALUES (?, ?, ?, ?, ?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
         preparedStatement.setString(1, task.getTitle());
         preparedStatement.setString(2, task.getDescription());
         preparedStatement.setString(3, task.getPriority());
         preparedStatement.setDate(4, Date.valueOf(task.getDeadline()));
         preparedStatement.setBoolean(5, task.isCompletionStatus());
      
         preparedStatement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to update an existing task
   public void updateTask(Task task) {
      String query = "UPDATE tasks SET title = ?, description = ?, priority = ?, deadline = ?, completion_status = ? WHERE task_id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
         preparedStatement.setString(1, task.getTitle());
         preparedStatement.setString(2, task.getDescription());
         preparedStatement.setString(3, task.getPriority());
         preparedStatement.setDate(4, Date.valueOf(task.getDeadline()));
         preparedStatement.setBoolean(5, task.isCompletionStatus());
         preparedStatement.setInt(6, task.getId());
      
         preparedStatement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to delete a task
   public void deleteTask(int taskId) {
      String query = "DELETE FROM tasks WHERE task_id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
         preparedStatement.setInt(1, taskId);
      
         preparedStatement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to get all tasks
   public List<Task> getAllTasks() {
      List<Task> tasks = new ArrayList<>();
      String query = "SELECT * FROM tasks";
   
      try (Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery(query)) {
         
         while (resultSet.next()) {
            Task task = new Task(
               resultSet.getInt("task_id"),
               resultSet.getString("title"),
               resultSet.getString("description"),
               resultSet.getString("priority"),
               resultSet.getDate("deadline").toLocalDate(),
               resultSet.getBoolean("completion_status")
               );
            tasks.add(task);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   
      return tasks;
   }

   // Close the database connection
   public void closeConnection() {
      try {
         if (connection != null && !connection.isClosed()) {
            connection.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   
   
   // Method to get a single task by ID
   public Task getTask(int taskId) {
      String query = "SELECT * FROM tasks WHERE task_id = ?";
      Task task = null;
   
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
         preparedStatement.setInt(1, taskId);
      
         try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
               int id = resultSet.getInt("task_id");
               String title = resultSet.getString("title");
               String description = resultSet.getString("description");
               String priority = resultSet.getString("priority");
               LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
               boolean completionStatus = resultSet.getBoolean("completion_status");
            
               task = new Task(id, title, description, priority, deadline, completionStatus);
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   
      return task;
   }
}
