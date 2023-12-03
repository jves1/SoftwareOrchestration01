import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // CREATE - Add a new payment to the database
    public boolean addPayment(Payment payment) {
        String query = "INSERT INTO Payments (PaymentID, OrderID, Amount, PaymentDate, PaymentStatus) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, payment.getPaymentID());
            statement.setInt(2, payment.getOrderID());
            statement.setDouble(3, payment.getAmount());
            statement.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            statement.setString(5, payment.getPaymentStatus());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get a payment by ID
    public Payment getPaymentById(int paymentId) {
        String query = "SELECT * FROM Payments WHERE PaymentID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, paymentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractPaymentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all payments
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payments";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Payment payment = extractPaymentFromResultSet(resultSet);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // UPDATE - Update an existing payment
    public boolean updatePayment(Payment payment) {
        String query = "UPDATE Payments SET OrderID = ?, Amount = ?, PaymentDate = ?, PaymentStatus = ? WHERE PaymentID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, payment.getOrderID());
            statement.setDouble(2, payment.getAmount());
            statement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            statement.setString(4, payment.getPaymentStatus());
            statement.setInt(5, payment.getPaymentID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove a payment from the database
    public boolean deletePayment(int paymentId) {
        String query = "DELETE FROM Payments WHERE PaymentID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, paymentId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract payment data from ResultSet
    private Payment extractPaymentFromResultSet(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();

        payment.setPaymentID(resultSet.getInt("PaymentID"));
        payment.setOrderID(resultSet.getInt("OrderID"));
        payment.setAmount(resultSet.getDouble("Amount"));
        payment.setPaymentDate(resultSet.getDate("PaymentDate"));
        payment.setPaymentStatus(resultSet.getString("PaymentStatus"));

        return payment;
    }
}
