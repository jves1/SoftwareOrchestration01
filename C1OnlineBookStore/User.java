import java.util.Date;

public class User {
    private int userID;
    private String username;
    private String passwordHash; // Remember to hash passwords before setting
    private String email;
    private String role;
    private Date dateJoined;

    // Constructor
    public User() {
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", dateJoined=" + dateJoined +
                '}';
    }

    // Main method for testing
    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();

        // Creating a new user instance
        User newUser = new User();
        newUser.setUserID(1); // Set this to a unique ID
        newUser.setUsername("john_doe");
        newUser.setPasswordHash("hashed_password"); // Remember to hash the password in real scenarios
        newUser.setEmail("john@example.com");
        newUser.setRole("customer");
        newUser.setDateJoined(new java.util.Date()); // Sets the current date

        // Add the new user to the database
        boolean isAdded = userDao.addUser(newUser);
        if (isAdded) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add the user.");
        }

        // Retrieve a user by ID
        User retrievedUser = userDao.getUserById(1);
        if (retrievedUser != null) {
            System.out.println("User Retrieved: " + retrievedUser.toString());
        } else {
            System.out.println("User not found.");
        }
    }
}
