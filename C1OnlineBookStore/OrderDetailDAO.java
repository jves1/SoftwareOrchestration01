import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    // CREATE - Add a new order detail to the database
    public boolean addOrderDetail(OrderDetail orderDetail) {
        String query = "INSERT INTO OrderDetails (OrderDetailID, OrderID, BookID, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderDetail.getOrderDetailID());
            statement.setInt(2, orderDetail.getOrderID());
            statement.setInt(3, orderDetail.getBookID());
            statement.setInt(4, orderDetail.getQuantity());
            statement.setDouble(5, orderDetail.getPrice());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get order details by order ID
    public List<OrderDetail> getOrderDetailsByOrderID(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM OrderDetails WHERE OrderID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDetail orderDetail = extractOrderDetailFromResultSet(resultSet);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    // UPDATE - Update an existing order detail
    public boolean updateOrderDetail(OrderDetail orderDetail) {
        String query = "UPDATE OrderDetails SET OrderID = ?, BookID = ?, Quantity = ?, Price = ? WHERE OrderDetailID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderDetail.getOrderID());
            statement.setInt(2, orderDetail.getBookID());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setDouble(4, orderDetail.getPrice());
            statement.setInt(5, orderDetail.getOrderDetailID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove an order detail from the database
    public boolean deleteOrderDetail(int orderDetailId) {
        String query = "DELETE FROM OrderDetails WHERE OrderDetailID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderDetailId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract order detail data from ResultSet
    private OrderDetail extractOrderDetailFromResultSet(ResultSet resultSet) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderDetailID(resultSet.getInt("OrderDetailID"));
        orderDetail.setOrderID(resultSet.getInt("OrderID"));
        orderDetail.setBookID(resultSet.getInt("BookID"));
        orderDetail.setQuantity(resultSet.getInt("Quantity"));
        orderDetail.setPrice(resultSet.getDouble("Price"));

        return orderDetail;
    }
}
