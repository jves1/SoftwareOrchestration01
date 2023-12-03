import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthenticationDAO {

    // Method to authenticate a user during login
    public boolean authenticateUser(String username, String passwordHash) {
        String query = "SELECT * FROM Users WHERE Username = ? AND PasswordHash = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to register a new user
    public boolean registerUser(User user) {
        String query = "INSERT INTO Users (Username, PasswordHash, Email, Role, DateJoined) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());
            statement.setDate(5, new java.sql.Date(user.getDateJoined().getTime()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods can be added here, like:
    // - updateUserPassword(String username, String newPasswordHash)
    // - etc.

    // Main method for testing
    public static void main(String[] args) {
        UserAuthenticationDAO authDao = new UserAuthenticationDAO();

        // Example user registration
        User newUser = new User();
        newUser.setUsername("new_user");
        newUser.setPasswordHash("hashed_password"); // Hashed password
        newUser.setEmail("new_user@example.com");
        newUser.setRole("customer");
        newUser.setDateJoined(new java.util.Date()); // Current date for example

        boolean isRegistered = authDao.registerUser(newUser);
        if (isRegistered) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Failed to register user.");
        }

        // Example user authentication
        boolean isAuthenticated = authDao.authenticateUser("new_user", "hashed_password");
        if (isAuthenticated) {
            System.out.println("User authenticated successfully.");
        } else {
            System.out.println("Authentication failed.");
        }
    }
}
