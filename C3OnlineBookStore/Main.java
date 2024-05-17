import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static User currentUser;
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTextArea searchResults;
    private JTextField searchField;
    private JTextArea cartDetails;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Online Book Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Login", createLoginPanel());
        tabbedPane.addTab("Register", createRegisterPanel());
        tabbedPane.addTab("Search Books", createSearchPanel());
        tabbedPane.addTab("Shopping Cart", createShoppingCartPanel());
        tabbedPane.addTab("Orders", createOrdersPanel());
        tabbedPane.addTab("Logoff", createLogoffPanel());

        showLoginScreen();

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private void showLoginScreen() {
        tabbedPane.setSelectedIndex(0);
        for (int i = 1; i < tabbedPane.getTabCount(); i++) {
            if (!tabbedPane.getTitleAt(i).equals("Register")) {
                tabbedPane.setEnabledAt(i, false);
            }
        }
    }

    private void showMainScreen() {
        tabbedPane.setSelectedIndex(2);
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, true);
        }
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                currentUser = UserAuthentication.authenticateUser(username, password);
                if (currentUser != null) {
                    JOptionPane.showMessageDialog(panel, "Login successful!");
                    showMainScreen();
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid username or password.");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());  // Empty cell
        panel.add(loginButton);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                currentUser = UserAuthentication.registerUser(username, password, email);
                if (currentUser != null) {
                    JOptionPane.showMessageDialog(panel, "Registration successful!");
                    showMainScreen();
                } else {
                    JOptionPane.showMessageDialog(panel, "Registration failed.");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel());  // Empty cell
        panel.add(registerButton);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 25));

        searchResults = new JTextArea();
        searchResults.setEditable(false);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setPreferredSize(new Dimension(120, 25));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSelectedBookToCart();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(searchResults), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void performSearch() {
        String keyword = searchField.getText();
        List<String> results = Search.searchBooks(keyword);
        searchResults.setText("");  // Clear previous results
        for (String result : results) {
            searchResults.append(result);
        }
    }

    private void addSelectedBookToCart() {
        String selectedText = searchResults.getSelectedText();
        if (selectedText != null && currentUser != null) {
            String bookIDString = selectedText.split("\n")[1].split(":")[1].trim();
            int bookID = Integer.parseInt(bookIDString);
            ShoppingCart cart = new ShoppingCart(0, currentUser.getUserID(), bookID, 1);
            cart.saveToDatabase();
            JOptionPane.showMessageDialog(frame, "Book added to cart!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book and ensure you are logged in.");
        }
    }

    private JPanel createShoppingCartPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        cartDetails = new JTextArea();
        cartDetails.setEditable(false);

        JButton loadCartButton = new JButton("Load Cart");
        JButton checkoutButton = new JButton("Checkout");
        JButton clearCartButton = new JButton("Clear Cart");

        loadCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCart();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });

        clearCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearCart();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadCartButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(clearCartButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(cartDetails), BorderLayout.CENTER);

        return panel;
    }

    private void loadCart() {
        if (currentUser != null) {
            List<String> cartItems = ShoppingCart.getUserCart(currentUser.getUserID());
            cartDetails.setText("");  // Clear previous cart details
            for (String item : cartItems) {
                cartDetails.append(item);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please login first.");
        }
    }

    private void clearCart() {
        if (currentUser != null) {
            ShoppingCart.clearUserCart(currentUser.getUserID());
            cartDetails.setText("");  // Clear cart display
            JOptionPane.showMessageDialog(frame, "Cart cleared!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please login first.");
        }
    }

    private void checkout() {
        if (currentUser != null) {
            String paymentDetails = JOptionPane.showInputDialog(frame, "Enter Payment Details (Card Number, Expiry Date, CVV):");
            if (paymentDetails != null && !paymentDetails.isEmpty()) {
                List<OrderDetail> orderDetails = new ArrayList<>();
                double totalAmount = 0.0;

                // Get cart items and calculate total amount
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String query = "SELECT ShoppingCart.BookID, ShoppingCart.Quantity, Books.Price " +
                                   "FROM ShoppingCart JOIN Books ON ShoppingCart.BookID = Books.BookID " +
                                   "WHERE ShoppingCart.UserID = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, currentUser.getUserID());
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        int bookID = rs.getInt("BookID");
                        int quantity = rs.getInt("Quantity");
                        double price = rs.getDouble("Price");
                        double amount = price * quantity;

                        orderDetails.add(new OrderDetail(0, 0, bookID, quantity, amount));
                        totalAmount += amount;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Create a new order and save payment details
                Order order = new Order(0, currentUser.getUserID(), orderDetails, java.time.LocalDate.now().toString(), totalAmount, "Pending");
                order.saveToDatabase();

                // Clear the cart after placing the order
                ShoppingCart.clearUserCart(currentUser.getUserID());
                cartDetails.setText("");  // Clear cart display

                JOptionPane.showMessageDialog(frame, "Order placed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Payment details are required to place an order.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please login first.");
        }
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea ordersDetails = new JTextArea();
        ordersDetails.setEditable(false);

        JButton loadOrdersButton = new JButton("Load Orders");

        loadOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    List<String> orders = Order.getUserOrders(currentUser.getUserID());
                    ordersDetails.setText("");  // Clear previous order details
                    for (String order : orders) {
                        ordersDetails.append(order);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please login first.");
                }
            }
        });

        panel.add(loadOrdersButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(ordersDetails), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLogoffPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton logoffButton = new JButton("Logoff");

        logoffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = null;
                showLoginScreen();
            }
        });

        panel.add(logoffButton, BorderLayout.CENTER);

        return panel;
    }
}
