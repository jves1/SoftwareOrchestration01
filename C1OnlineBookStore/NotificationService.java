import java.util.Date;
import java.util.List;

public class NotificationService {

    private NotificationServiceDAO notificationServiceDAO;

    // Constructor
    public NotificationService() {
        this.notificationServiceDAO = new NotificationServiceDAO();
    }

    // Method to send a notification
    public boolean sendNotification(int userID, String message) {
        Notification notification = new Notification();
        notification.setUserID(userID);
        notification.setMessage(message);
        notification.setDateCreated(new Date());
        notification.setRead(false);

        return notificationServiceDAO.addNotification(notification);
    }

    // Method to retrieve all notifications for a user
    public List<Notification> getNotificationsForUser(int userID) {
        return notificationServiceDAO.getNotificationsForUser(userID);
    }

    // Method to mark a notification as read
    public boolean markNotificationAsRead(int notificationID) {
        return notificationServiceDAO.markNotificationAsRead(notificationID);
    }

    // Main method for testing
    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService();

        // Test sending a notification
        int userID = 1; // Example user ID
        String message = "Your order has been shipped.";
        boolean isSent = notificationService.sendNotification(userID, message);
        if (isSent) {
            System.out.println("Notification sent successfully.");
        } else {
            System.out.println("Failed to send notification.");
        }

        // Test retrieving notifications for a user
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
