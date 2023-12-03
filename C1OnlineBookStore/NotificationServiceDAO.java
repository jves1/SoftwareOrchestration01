import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceDAO {

    // CREATE - Add a new notification to the database
    public boolean addNotification(Notification notification) {
        String query = "INSERT INTO Notifications (NotificationID, UserID, Message, DateCreated, IsRead) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, notification.getNotificationID());
            statement.setInt(2, notification.getUserID());
            statement.setString(3, notification.getMessage());
            statement.setTimestamp(4, new java.sql.Timestamp(notification.getDateCreated().getTime()));
            statement.setBoolean(5, notification.isRead());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get all notifications for a specific user
    public List<Notification> getNotificationsForUser(int userID) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notifications WHERE UserID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(resultSet.getInt("NotificationID"));
                notification.setUserID(resultSet.getInt("UserID"));
                notification.setMessage(resultSet.getString("Message"));
                notification.setDateCreated(resultSet.getTimestamp("DateCreated"));
                notification.setRead(resultSet.getBoolean("IsRead"));
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // UPDATE - Mark a notification as read
    public boolean markNotificationAsRead(int notificationID) {
        String query = "UPDATE Notifications SET IsRead = TRUE WHERE NotificationID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, notificationID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Delete a notification
    public boolean deleteNotification(int notificationID) {
        String query = "DELETE FROM Notifications WHERE NotificationID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, notificationID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods can be implemented here, such as:
    // - deleteAllNotificationsForUser(int userID)
    // - getUnreadNotificationsCount(int userID)
    // - etc.

    // Main method for testing
    public static void main(String[] args) {
        NotificationServiceDAO notificationService = new NotificationServiceDAO();

        // Example: Add a new notification
        Notification newNotification = new Notification();
        newNotification.setNotificationID(1); // Example notification ID
        newNotification.setUserID(1); // Example user ID
        newNotification.setMessage("Your order has been shipped.");
        newNotification.setDateCreated(new java.util.Date()); // Current date for example
        newNotification.setRead(false);

        boolean isAdded = notificationService.addNotification(newNotification);
        if (isAdded) {
            System.out.println("Notification added successfully.");
        } else {
            System.out.println("Failed to add notification.");
        }

        // Example: Retrieve notifications for a user
        int userID = 1; // Example user ID
        List<Notification> notifications = notificationService.getNotificationsForUser(userID);
        if (!notifications.isEmpty()) {
            System.out.println("Notifications for User ID " + userID + ":");
            for (Notification notification : notifications) {
                System.out.println(notification.toString());
            }
        } else {
            System.out.println("No notifications found for User ID: " + userID);
        }
    }
}
