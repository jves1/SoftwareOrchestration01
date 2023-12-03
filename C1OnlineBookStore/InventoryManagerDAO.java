import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryManagerDAO {

    // Method to update the inventory quantity for a book
    public boolean updateBookQuantity(int bookID, int newQuantity) {
        String query = "UPDATE Books SET Quantity = ? WHERE BookID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, newQuantity);
            statement.setInt(2, bookID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get the current quantity of a book
    public int getBookQuantity(int bookID) {
        String query = "SELECT Quantity FROM Books WHERE BookID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicative of an error or not found
    }

    // Additional inventory management methods can be implemented here, such as:
    // - reorderBook(int bookID)
    // - checkLowInventory()
    // - etc.

    // Main method for testing
    public static void main(String[] args) {
        InventoryManagerDAO inventoryManager = new InventoryManagerDAO();

        // Example: Update book quantity
        int bookID = 101; // Example book ID
        int newQuantity = 50; // Example new quantity
        boolean isUpdated = inventoryManager.updateBookQuantity(bookID, newQuantity);
        if (isUpdated) {
            System.out.println("Book quantity updated successfully.");
        } else {
            System.out.println("Failed to update book quantity.");
        }

        // Example: Get book quantity
        int currentQuantity = inventoryManager.getBookQuantity(bookID);
        if (currentQuantity != -1) {
            System.out.println("Current Quantity of Book ID " + bookID + ": " + currentQuantity);
        } else {
            System.out.println("Failed to retrieve book quantity.");
        }
    }
}
