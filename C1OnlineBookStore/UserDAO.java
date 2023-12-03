import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // CREATE - Add a new user to the database
    public boolean addUser(User user) {
        String query = "INSERT INTO Users (UserID, Username, PasswordHash, Email, Role, DateJoined) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());
            statement.setDate(6, new java.sql.Date(user.getDateJoined().getTime()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get a user by ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = extractUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // UPDATE - Update an existing user
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET Username = ?, PasswordHash = ?, Email = ?, Role = ?, DateJoined = ? WHERE UserID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());
            statement.setDate(5, new java.sql.Date(user.getDateJoined().getTime()));
            statement.setInt(6, user.getUserID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove a user from the database
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract user data from ResultSet
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setUserID(resultSet.getInt("UserID"));
        user.setUsername(resultSet.getString("Username"));
        user.setPasswordHash(resultSet.getString("PasswordHash"));
        user.setEmail(resultSet.getString("Email"));
        user.setRole(resultSet.getString("Role"));
        user.setDateJoined(resultSet.getDate("DateJoined"));

        return user;
    }
}
