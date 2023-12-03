import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
   private int cartID;
   private int userID;
   private Map<Integer, Integer> items; // Map of BookID to Quantity
   private SearchDAO searchDAO; // Assuming you have a SearchDAO for fetching book prices

    

   // Constructor
   public ShoppingCart() {
      this.items = new HashMap<>();
      this.searchDAO = new SearchDAO(); // Initialize SearchDAO
   }
   
   // Method to calculate the total amount of the items in the cart
   public double calculateTotalAmount() {
      double totalAmount = 0.0;
   
      for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
         int bookID = entry.getKey();
         int quantity = entry.getValue();
      
         double price = searchDAO.getPriceByBookID(bookID); // Fetch the price of the book
         totalAmount += price * quantity; // Multiply by quantity and add to total
      }
   
      return totalAmount;
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

   public Map<Integer, Integer> getItems() {
      return items;
   }

   public void setItems(Map<Integer, Integer> items) {
      this.items = items;
   }

   // Method to add an item to the cart
   public void addItem(int bookID, int quantity) {
      items.put(bookID, items.getOrDefault(bookID, 0) + quantity);
   }

   // Method to remove an item from the cart
   public void removeItem(int bookID) {
      items.remove(bookID);
   }

// Method to check if the cart is empty
   public boolean isEmpty() {
      return items.isEmpty();
   }
   
   
   // Method to clear all items from the cart
   public void clear() {
      items.clear(); // Clears the map of items
   }
    
    
   // toString method for debugging
   @Override
   public String toString() {
      return "ShoppingCart{" +
             "cartID=" + cartID +
             ", userID=" + userID +
             ", items=" + items +
             '}';
   }

   // Main method for testing
   public static void main(String[] args) {
      ShoppingCart cart = new ShoppingCart();
      cart.setCartID(1); // Example cart ID
      cart.setUserID(1); // Example user ID
   
      // Add items to the cart
      cart.addItem(101, 2); // Adds 2 quantities of book with ID 101
      cart.addItem(102, 1); // Adds 1 quantity of book with ID 102
   
      System.out.println(cart.toString());
   
      // Remove an item from the cart
      cart.removeItem(101); // Removes book with ID 101
   
      System.out.println("After removing an item: " + cart.toString());
   }
}
