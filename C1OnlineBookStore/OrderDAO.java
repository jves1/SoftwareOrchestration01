import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // CREATE - Add a new order to the database
    public boolean addOrder(Order order) {
        String query = "INSERT INTO Orders (OrderID, UserID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getOrderID());
            statement.setInt(2, order.getUserID());
            statement.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(4, order.getTotalAmount());
            statement.setString(5, order.getStatus());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get an order by ID
    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM Orders WHERE OrderID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractOrderFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all orders
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = extractOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // UPDATE - Update an existing order
    public boolean updateOrder(Order order) {
        String query = "UPDATE Orders SET UserID = ?, OrderDate = ?, TotalAmount = ?, Status = ? WHERE OrderID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getUserID());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(3, order.getTotalAmount());
            statement.setString(4, order.getStatus());
            statement.setInt(5, order.getOrderID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove an order from the database
    public boolean deleteOrder(int orderId) {
        String query = "DELETE FROM Orders WHERE OrderID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract order data from ResultSet
    private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();

        order.setOrderID(resultSet.getInt("OrderID"));
        order.setUserID(resultSet.getInt("UserID"));
        order.setOrderDate(resultSet.getDate("OrderDate"));
        order.setTotalAmount(resultSet.getDouble("TotalAmount"));
        order.setStatus(resultSet.getString("Status"));

        return order;
    }
}
