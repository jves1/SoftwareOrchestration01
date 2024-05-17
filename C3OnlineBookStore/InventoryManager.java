import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryManager {
    private int bookID;
    private int stockQuantity;

    // Constructor
    public InventoryManager(int bookID, int stockQuantity) {
        this.bookID = bookID;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // Database Interaction Methods
    public static InventoryManager loadFromDatabase(int bookID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Inventory WHERE BookID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new InventoryManager(
                    rs.getInt("BookID"),
                    rs.getInt("StockQuantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Inventory (BookID, StockQuantity) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            stmt.setInt(2, stockQuantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStockQuantity(int newQuantity) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Inventory SET StockQuantity = ? WHERE BookID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, bookID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Inventory WHERE BookID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
