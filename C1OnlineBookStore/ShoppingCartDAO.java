import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartDAO {

    // CREATE - Add a new shopping cart or update an existing one
    public boolean saveOrUpdateCart(ShoppingCart cart) {
        // Delete any existing items in the cart
        if (!deleteCartItems(cart.getCartID())) {
            return false;
        }

        // Add new items
        String insertQuery = "INSERT INTO ShoppingCart (CartID, BookID, Quantity) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            for (Map.Entry<Integer, Integer> item : cart.getItems().entrySet()) {
                statement.setInt(1, cart.getCartID());
                statement.setInt(2, item.getKey());
                statement.setInt(3, item.getValue());
                statement.addBatch();
            }

            int[] rowsAffected = statement.executeBatch();
            return rowsAffected.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get a shopping cart by user ID
    public ShoppingCart getCartByUserID(int userID) {
        String query = "SELECT * FROM ShoppingCart WHERE UserID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            ShoppingCart cart = new ShoppingCart();
            cart.setUserID(userID);
            Map<Integer, Integer> items = new HashMap<>();

            while (resultSet.next()) {
                int bookID = resultSet.getInt("BookID");
                int quantity = resultSet.getInt("Quantity");
                items.put(bookID, quantity);
            }

            cart.setItems(items);
            return cart;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DELETE - Remove items from a shopping cart
    public boolean deleteCartItems(int cartID) {
        String query = "DELETE FROM ShoppingCart WHERE CartID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, cartID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        ShoppingCartDAO cartDao = new ShoppingCartDAO();
        ShoppingCart cart = new ShoppingCart();
        cart.setCartID(1); // Example cart ID
        cart.setUserID(1); // Example user ID
        cart.addItem(101, 2); // Add items to the cart

        boolean isSaved = cartDao.saveOrUpdateCart(cart);
        if (isSaved) {
            System.out.println("Shopping cart saved or updated successfully.");
        } else {
            System.out.println("Failed to save or update the shopping cart.");
        }

        // Retrieve the cart by user ID
        ShoppingCart retrievedCart = cartDao.getCartByUserID(1);
        if (retrievedCart != null) {
            System.out.println("Retrieved Cart: " + retrievedCart.toString());
        } else {
            System.out.println("Cart not found.");
        }
    }
}
