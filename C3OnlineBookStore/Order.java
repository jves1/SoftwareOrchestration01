import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private int userID;
    private List<OrderDetail> orderDetails;
    private String orderDate;
    private double totalAmount;
    private String status;

    // Constructor
    public Order(int orderID, int userID, List<OrderDetail> orderDetails, String orderDate, double totalAmount, String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDetails = orderDetails;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
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

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Database Interaction Methods
    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Orders (UserID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userID);
            stmt.setString(2, orderDate);
            stmt.setDouble(3, totalAmount);
            stmt.setString(4, status);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.orderID = rs.getInt(1);
            }

            if (orderDetails != null) {
                for (OrderDetail detail : orderDetails) {
                    detail.setOrderID(this.orderID);
                    detail.saveToDatabase();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getUserOrders(int userID) {
        List<String> orders = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Orders WHERE UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add("OrderID: " + rs.getInt("OrderID") + "\n" +
                           "OrderDate: " + rs.getString("OrderDate") + "\n" +
                           "TotalAmount: " + rs.getDouble("TotalAmount") + "\n" +
                           "Status: " + rs.getString("Status") + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
