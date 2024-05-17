import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private int cartID;
    private int userID;
    private int bookID;
    private int quantity;

    // Constructor
    public ShoppingCart(int cartID, int userID, int bookID, int quantity) {
        this.cartID = cartID;
        this.userID = userID;
        this.bookID = bookID;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    // Database Interaction Methods
    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO ShoppingCart (UserID, BookID, Quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setInt(2, bookID);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getUserCart(int userID) {
        List<String> cartItems = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT ShoppingCart.BookID, ShoppingCart.Quantity, Books.Title, Books.Price " +
                           "FROM ShoppingCart JOIN Books ON ShoppingCart.BookID = Books.BookID " +
                           "WHERE ShoppingCart.UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double amount = rs.getDouble("Price") * rs.getInt("Quantity");
                cartItems.add("Title: " + rs.getString("Title") + "\n" +
                              "Quantity: " + rs.getInt("Quantity") + "\n" +
                              "Amount: $" + amount + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public static void clearUserCart(int userID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM ShoppingCart WHERE UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM ShoppingCart WHERE CartID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cartID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
