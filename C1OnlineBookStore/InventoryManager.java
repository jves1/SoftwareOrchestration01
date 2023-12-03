public class InventoryManager {

    private InventoryManagerDAO inventoryManagerDAO;

    // Constructor
    public InventoryManager() {
        this.inventoryManagerDAO = new InventoryManagerDAO();
    }

    // Method to update the inventory quantity for a book
    public boolean updateBookQuantity(int bookID, int newQuantity) {
        return inventoryManagerDAO.updateBookQuantity(bookID, newQuantity);
    }

    // Method to get the current quantity of a book
    public int getBookQuantity(int bookID) {
        return inventoryManagerDAO.getBookQuantity(bookID);
    }

    // Additional inventory management methods can be implemented here, such as:
    // - reorderBook(int bookID)
    // - checkLowInventory()
    // - etc.

    // Main method for testing
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();

        // Test updating book quantity
        int bookID = 101; // Example book ID
        int newQuantity = 50; // Example new quantity
        boolean isUpdated = inventoryManager.updateBookQuantity(bookID, newQuantity);
        if (isUpdated) {
            System.out.println("Book quantity updated successfully.");
        } else {
            System.out.println("Failed to update book quantity.");
        }

        // Test getting book quantity
        int currentQuantity = inventoryManager.getBookQuantity(bookID);
        if (currentQuantity >= 0) {
            System.out.println("Current Quantity of Book ID " + bookID + ": " + currentQuantity);
        } else {
            System.out.println("Failed to retrieve book quantity.");
        }
    }
}
