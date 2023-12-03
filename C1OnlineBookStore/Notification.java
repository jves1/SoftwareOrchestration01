import java.util.Date;

public class Notification {
    private int notificationID;
    private int userID;
    private String message;
    private Date dateCreated;
    private boolean isRead;

    // Constructor
    public Notification() {
    }

    // Getters and Setters
    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", userID=" + userID +
                ", message='" + message + '\'' +
                ", dateCreated=" + dateCreated +
                ", isRead=" + isRead +
                '}';
    }
}
