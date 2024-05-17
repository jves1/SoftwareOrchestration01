import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {
    public static User registerUser(String username, String password, String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Users (Username, PasswordHash, Email, DateJoined) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // In a real application, hash the password
            stmt.setString(3, email);
            stmt.executeUpdate();
            return new User(0, username, password, email, null); // UserID will be assigned by DB
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Users WHERE Username = ? AND PasswordHash = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // In a real application, hash the password
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("UserID"),
                    rs.getString("Username"),
                    rs.getString("PasswordHash"),
                    rs.getString("Email"),
                    rs.getString("DateJoined")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
