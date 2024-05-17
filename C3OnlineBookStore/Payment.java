import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Payment {
    private int paymentID;
    private int orderID;
    private int userID;
    private double amount;
    private String paymentMethod;
    private String paymentDate;
    private String cardNumber;
    private String expirationDate;
    private String CVV;
    private String status;

    // Constructor
    public Payment(int paymentID, int orderID, int userID, double amount, String paymentMethod, String paymentDate, String cardNumber, String expirationDate, String CVV, String status) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.userID = userID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.CVV = CVV;
        this.status = status;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Database Interaction Methods
    public static Payment loadFromDatabase(int paymentID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Payments WHERE PaymentID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Payment(
                    rs.getInt("PaymentID"),
                    rs.getInt("OrderID"),
                    rs.getInt("UserID"),
                    rs.getDouble("Amount"),
                    rs.getString("PaymentMethod"),
                    rs.getString("PaymentDate"),
                    rs.getString("CardNumber"),
                    rs.getString("ExpirationDate"),
                    rs.getString("CVV"),
                    rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Payments (OrderID, UserID, Amount, PaymentMethod, PaymentDate, CardNumber, ExpirationDate, CVV, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);
            stmt.setInt(2, userID);
            stmt.setDouble(3, amount);
            stmt.setString(4, paymentMethod);
            stmt.setString(5, paymentDate);
            stmt.setString(6, cardNumber);
            stmt.setString(7, expirationDate);
            stmt.setString(8, CVV);
            stmt.setString(9, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Payments WHERE PaymentID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, paymentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
