public class OrderDetail {
    private int orderDetailID;
    private int orderID;
    private int bookID;
    private int quantity;
    private double price;

    // Constructor
    public OrderDetail() {
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

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailID=" + orderDetailID +
                ", orderID=" + orderID +
                ", bookID=" + bookID +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    // Main method for testing
    public static void main(String[] args) {
        OrderDetailDAO orderDetailDao = new OrderDetailDAO();

        // Creating a new order detail instance
        OrderDetail newOrderDetail = new OrderDetail();
        newOrderDetail.setOrderDetailID(1); // Example order detail ID
        newOrderDetail.setOrderID(1); // Example order ID
        newOrderDetail.setBookID(1); // Example book ID
        newOrderDetail.setQuantity(2);
        newOrderDetail.setPrice(19.99);

        // Add the new order detail to the database
        boolean isAdded = orderDetailDao.addOrderDetail(newOrderDetail);
        if (isAdded) {
            System.out.println("OrderDetail added successfully.");
        } else {
            System.out.println("Failed to add the OrderDetail.");
        }

        // Retrieve an order detail by ID
        OrderDetail retrievedOrderDetail = orderDetailDao.getOrderDetailsByOrderID(1).get(0); // Assuming at least one detail exists
        if (retrievedOrderDetail != null) {
            System.out.println("OrderDetail Retrieved: " + retrievedOrderDetail.toString());
        } else {
            System.out.println("OrderDetail not found.");
        }
    }
}
