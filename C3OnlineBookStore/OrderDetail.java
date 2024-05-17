import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetail {
    private int orderDetailID;
    private int orderID;
    private int bookID;
    private int quantity;
    private double price;
    private double subtotal;

    // Constructor
    public OrderDetail(int orderDetailID, int orderID, int bookID, int quantity, double price) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = price * quantity;
    }

    // Getters and Setters
    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Database Interaction Methods
    public static OrderDetail loadFromDatabase(int orderDetailID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM OrderDetails WHERE OrderDetailID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderDetailID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OrderDetail(
                    rs.getInt("OrderDetailID"),
                    rs.getInt("OrderID"),
                    rs.getInt("BookID"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO OrderDetails (OrderID, BookID, Quantity, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);
            stmt.setInt(2, bookID);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM OrderDetails WHERE OrderDetailID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderDetailID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Additional Functionalities
    public static ResultSet getOrderDetails(int orderID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM OrderDetails WHERE OrderID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
