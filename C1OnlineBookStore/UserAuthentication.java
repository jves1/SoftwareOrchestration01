import java.util.Date;

public class UserAuthentication {
    
    private UserAuthenticationDAO userAuthDAO;

    // Constructor
    public UserAuthentication() {
        this.userAuthDAO = new UserAuthenticationDAO();
    }

    // Method for user login
    public boolean login(String username, String password) {
        // Password should be hashed. In a real application, apply a hashing algorithm here.
        String passwordHash = hashPassword(password);
        return userAuthDAO.authenticateUser(username, passwordHash);
    }

    // Method for user registration
    public boolean register(String username, String password, String email, String role) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(hashPassword(password)); // Hash the password
        newUser.setEmail(email);
        newUser.setRole(role);
        newUser.setDateJoined(new Date()); // Use current date for registration date

        return userAuthDAO.registerUser(newUser);
    }

    // Method to hash a password (placeholder for a real hashing algorithm)
    private String hashPassword(String password) {
        // Implement password hashing here. This is just a placeholder.
        return "hashed_" + password;
    }

    // Main method for testing
    public static void main(String[] args) {
        UserAuthentication userAuth = new UserAuthentication();

        // Test user registration
        boolean isRegistered = userAuth.register("john_doe", "password123", "john@example.com", "customer");
        if (isRegistered) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed.");
        }

        // Test user login
        boolean isLoggedIn = userAuth.login("john_doe", "password123");
        if (isLoggedIn) {
            System.out.println("User logged in successfully.");
        } else {
            System.out.println("Login failed.");
        }
    }
}
