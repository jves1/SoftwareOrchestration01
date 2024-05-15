import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDatabase {
    private final String url = "jdbc:mysql://localhost:3306/PersonalTaskManagerDB";
    private final String user = "root"; // Replace with actual database username
    private final String password = ""; // Replace with actual database password

    // Method to establish database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Add a new task to the database
    public void addTask(Task task) {
        String sql = "INSERT INTO Tasks(Title, Description, Priority, Deadline, Status) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getPriority());
            pstmt.setDate(4, Date.valueOf(task.getDeadline()));
            pstmt.setString(5, task.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update an existing task
    public void updateTask(Task task) {
        String sql = "UPDATE Tasks SET Title = ?, Description = ?, Priority = ?, Deadline = ?, Status = ? WHERE TaskID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getPriority());
            pstmt.setDate(4, Date.valueOf(task.getDeadline()));
            pstmt.setString(5, task.getStatus());
            pstmt.setInt(6, task.getTaskId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a task from the database
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM Tasks WHERE TaskID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve a task by its ID
    public Task getTask(int taskId) {
        String sql = "SELECT * FROM Tasks WHERE TaskID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Task(
                        rs.getInt("TaskID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Priority"),
                        rs.getDate("Deadline").toLocalDate(),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Retrieve all tasks
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("TaskID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Priority"),
                        rs.getDate("Deadline").toLocalDate(),
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    // Search tasks by title
    public List<Task> searchTasksByTitle(String title) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks WHERE Title LIKE ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("TaskID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Priority"),
                        rs.getDate("Deadline").toLocalDate(),
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }
}
