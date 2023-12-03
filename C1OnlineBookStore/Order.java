import java.util.Date;

public class Order {
    private int orderID;
    private int userID;
    private Date orderDate;
    private double totalAmount;
    private String status; // e.g., "pending", "completed"

    // Constructor
    public Order() {
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }

    // Main method for testing
    public static void main(String[] args) {
        OrderDAO orderDao = new OrderDAO();

        // Creating a new order instance
        Order newOrder = new Order();
        newOrder.setOrderID(1); // Example order ID
        newOrder.setUserID(1); // Example user ID
        newOrder.setOrderDate(new Date()); // Current date for example
        newOrder.setTotalAmount(29.99);
        newOrder.setStatus("pending");

        // Add the new order to the database
        boolean isAdded = orderDao.addOrder(newOrder);
        if (isAdded) {
            System.out.println("Order added successfully.");
        } else {
            System.out.println("Failed to add the order.");
        }

        // Retrieve an order by ID
        Order retrievedOrder = orderDao.getOrderById(1);
        if (retrievedOrder != null) {
            System.out.println("Order Retrieved: " + retrievedOrder.toString());
        } else {
            System.out.println("Order not found.");
        }
    }
}
