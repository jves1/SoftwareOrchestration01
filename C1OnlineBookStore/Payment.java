import java.util.Date;

public class Payment {
    private int paymentID;
    private int orderID;
    private double amount;
    private Date paymentDate;
    private String paymentStatus; // e.g., "processed", "failed"

    // Constructor
    public Payment() {
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", orderID=" + orderID +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }

    // Main method for testing
    public static void main(String[] args) {
        PaymentDAO paymentDao = new PaymentDAO();

        // Creating a new payment instance
        Payment newPayment = new Payment();
        newPayment.setPaymentID(1); // Example payment ID
        newPayment.setOrderID(1); // Example order ID
        newPayment.setAmount(29.99);
        newPayment.setPaymentDate(new Date()); // Current date for example
        newPayment.setPaymentStatus("processed");

        // Add the new payment to the database
        boolean isAdded = paymentDao.addPayment(newPayment);
        if (isAdded) {
            System.out.println("Payment added successfully.");
        } else {
            System.out.println("Failed to add the payment.");
        }

        // Retrieve a payment by ID
        Payment retrievedPayment = paymentDao.getPaymentById(1);
        if (retrievedPayment != null) {
            System.out.println("Payment Retrieved: " + retrievedPayment.toString());
        } else {
            System.out.println("Payment not found.");
        }
    }
}
