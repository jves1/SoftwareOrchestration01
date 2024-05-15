import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseManager {
   private Connection connection;

   // Constructor to initialize database connection
   public TaskDatabaseManager(String databaseURL, String username, String password) throws SQLException {
      connection = DriverManager.getConnection(databaseURL, username, password);
   }

   // Method to add a task
   public boolean addTask(Task task) {
      String sql = "INSERT INTO Tasks (Title, Description, Priority, Deadline, Status) VALUES (?, ?, ?, ?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, task.getTitle());
         statement.setString(2, task.getDescription());
         statement.setString(3, task.getPriority());
         statement.setDate(4, task.getDeadline());
         statement.setString(5, task.getStatus());
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   // Method to update a task
   public boolean updateTask(Task task) {
      String sql = "UPDATE Tasks SET Title = ?, Description = ?, Priority = ?, Deadline = ?, Status = ? WHERE TaskID = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, task.getTitle());
         statement.setString(2, task.getDescription());
         statement.setString(3, task.getPriority());
         statement.setDate(4, task.getDeadline());
         statement.setString(5, task.getStatus());
         statement.setInt(6, task.getTaskId());
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   // Method to delete a task
   public boolean deleteTask(int taskId) {
      String sql = "DELETE FROM Tasks WHERE TaskID = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, taskId);
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   // Method to retrieve a list of all tasks
   public List<Task> getAllTasks() {
      List<Task> tasks = new ArrayList<>();
      String sql = "SELECT * FROM Tasks";
      try (Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery(sql)) {
         while (resultSet.next()) {
            Task task = new Task(
                   resultSet.getInt("TaskID"),
                   resultSet.getString("Title"),
                   resultSet.getString("Description"),
                   resultSet.getString("Priority"),
                   resultSet.getDate("Deadline"),
                   resultSet.getString("Status")
               );
            tasks.add(task);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return tasks;
   }

   // Close the connection when done
   public void close() {
      try {
         if (connection != null && !connection.isClosed()) {
            connection.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   
   // Method to retrieve a task by ID
   public Task getTaskById(int taskId) {
      String sql = "SELECT * FROM Tasks WHERE TaskID = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, taskId);
         ResultSet resultSet = statement.executeQuery();
         if (resultSet.next()) {
            return new Task(
               resultSet.getInt("TaskID"),
               resultSet.getString("Title"),
               resultSet.getString("Description"),
               resultSet.getString("Priority"),
               resultSet.getDate("Deadline"),
               resultSet.getString("Status")
               );
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }
}
