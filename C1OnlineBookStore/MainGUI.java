import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class MainGUI {
   private JFrame frame;
   private JTextField searchField;
   private JButton searchButton;
   private JList<Book> searchResultsList;
   private JButton addToCartButton;
   private JButton viewCartButton;
   private JButton processOrderButton;
   private SearchDAO searchDAO; // SearchDAO instance
   private DefaultListModel<Book> searchResultsModel; // Model for JList
   private ShoppingCart shoppingCart;  

   
   // Constructor
   public MainGUI() {
      this.searchDAO = new SearchDAO(); // Initialize SearchDAO
      this.searchResultsModel = new DefaultListModel<>(); // Initialize the model for search results
      searchResultsList = new JList<>(searchResultsModel);
      this.shoppingCart = new ShoppingCart(); // Initialize the ShoppingCart
   
      initializeUI();
   }

   // Initialize the UI components
   private void initializeUI() {
      frame = new JFrame("Online Bookstore");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
   
      // Create and add components here
      createSearchPanel();
      createResultsPanel();
      createCartAndOrderPanel();
    
      frame.pack();
      frame.setSize(600, 400); // Set the size
      frame.setVisible(true);
   }

   // Create search panel
   private void createSearchPanel() {
      JPanel searchPanel = new JPanel();
      searchField = new JTextField(20);
      searchButton = new JButton("Search");
      searchButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               performSearch();
            }
         });
   
      searchPanel.add(searchField);
      searchPanel.add(searchButton);
      frame.add(searchPanel, BorderLayout.NORTH);
   }

   // Perform book search
 // Method to perform book search
   private void performSearch() {
      String query = searchField.getText();
      List<Book> results = searchDAO.searchBooksByTitle(query); // Corrected to use the initialized searchDAO
      updateSearchResults(results);
   }

    // Method to update search results in the list model
   private void updateSearchResults(List<Book> results) {
      searchResultsModel.clear();
      if (results != null) {
         for (Book book : results) {
            searchResultsModel.addElement(book);
         }
      }
      searchResultsList.revalidate();
      searchResultsList.repaint();
      
   }   
   
   // Create results panel
   
   private void createResultsPanel() {
      //searchResultsList = new JList<>();
      frame.add(new JScrollPane(searchResultsList), BorderLayout.CENTER);
      addToCartButton = new JButton("Add to Cart");
      addToCartButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               addToCart();
            }
         });
      frame.add(addToCartButton, BorderLayout.EAST);
   }

   // Add selected book to shopping cart
   private void addToCart() {
      Book selectedBook = searchResultsList.getSelectedValue();
      if (selectedBook != null) {
         shoppingCart.addItem(selectedBook.getBookID(), 1); // Add 1 quantity of the selected book
         JOptionPane.showMessageDialog(frame, selectedBook.getTitle() + " added to cart.");
      } else {
         JOptionPane.showMessageDialog(frame, "No book selected.", "Error", JOptionPane.ERROR_MESSAGE);
      }   
   }
   
   

      // Create panel for cart and order processing
   private void createCartAndOrderPanel() {
      JPanel cartPanel = new JPanel();
      viewCartButton = new JButton("View Cart");
      viewCartButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               viewCart();
            }
         });
   
      processOrderButton = new JButton("Process Order");
        // Add ActionListener to processOrderButton if needed
      processOrderButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               processOrder();
            }
         });
   
      cartPanel.add(viewCartButton);
      cartPanel.add(processOrderButton);
      frame.add(cartPanel, BorderLayout.SOUTH);
   }
   
    // Method to view the contents of the shopping cart
   private void viewCart() {
      Map<Integer, Integer> cartItems = shoppingCart.getItems(); // BookID to Quantity
      StringBuilder cartContents = new StringBuilder();
    
      for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
         int bookID = entry.getKey();
         int quantity = entry.getValue();
        
        // Fetch the book details using the bookID
         Book book = searchDAO.getBookByID(bookID); // Assuming such a method exists
         if (book != null) {
            // Format the book details along with quantity
            String bookDetails = String.format("%s - Qty: %d\n", book.toString(), quantity);
            cartContents.append(bookDetails);
         }
      }
   
      JOptionPane.showMessageDialog(frame, cartContents.toString(), "Shopping Cart", JOptionPane.INFORMATION_MESSAGE);
   }
   
   
   private void processOrder() {
      if (shoppingCart.isEmpty()) {
         JOptionPane.showMessageDialog(frame, "Your cart is empty.", "Order Processing", JOptionPane.INFORMATION_MESSAGE);
         return;
      }
   
    // Create a new order object
      Order newOrder = new Order();
    // Assuming you have methods to calculate total amount and get current user's ID
      //newOrder.setUserID(currentUser.getUserID()); // currentUser should be the logged-in user
      newOrder.setUserID(1); // Example user ID
   
      newOrder.setOrderDate(new java.util.Date()); // Set the current date as the order date
      newOrder.setTotalAmount(shoppingCart.calculateTotalAmount());
      newOrder.setStatus("Processed"); // Set an appropriate status
   
      OrderDAO orderDAO = new OrderDAO();
      boolean orderProcessed = orderDAO.addOrder(newOrder);
   
      if (orderProcessed) {
         shoppingCart.clear(); // Assuming ShoppingCart has a clear() method to empty the cart
         JOptionPane.showMessageDialog(frame, "Your order has been processed successfully.", "Order Processed", JOptionPane.INFORMATION_MESSAGE);
      } else {
         JOptionPane.showMessageDialog(frame, "There was a problem processing your order.", "Order Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   
   // Main method to launch the GUI
   public static void main(String[] args) {
      SwingUtilities.invokeLater(
         new Runnable() {
            @Override
            public void run() {
               new MainGUI();
            }
         });
   }
}
