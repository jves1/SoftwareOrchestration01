import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationSystemDAO {

    // Method to recommend books based on user's previous interests or purchases
    public List<Book> recommendBooksByCategory(int userID) {
        List<Book> recommendedBooks = new ArrayList<>();
        String query = 
            "SELECT b.* FROM Books b " +
            "JOIN Orders o ON b.BookID = o.BookID " +
            "JOIN OrderDetails od ON o.OrderID = od.OrderID " +
            "WHERE od.UserID = ? " +
            "GROUP BY b.CategoryID " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 10"; // Limit to 10 recommendations for simplicity

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                // Assuming Book class has these methods
                book.setBookID(resultSet.getInt("BookID"));
                book.setTitle(resultSet.getString("Title"));
                book.setAuthorID(resultSet.getInt("AuthorID"));
                book.setCategoryID(resultSet.getInt("CategoryID"));
                // ... set other book attributes
                recommendedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendedBooks;
    }

    // Main method for testing
    public static void main(String[] args) {
        RecommendationSystemDAO recommendationSystem = new RecommendationSystemDAO();

        // Test book recommendation for a user
        int userID = 1; // Example user ID
        List<Book> recommendedBooks = recommendationSystem.recommendBooksByCategory(userID);
        
        if (!recommendedBooks.isEmpty()) {
            System.out.println("Recommended Books: ");
            for (Book book : recommendedBooks) {
                System.out.println(book.toString());
            }
        } else {
            System.out.println("No recommendations found for user ID: " + userID);
        }
    }
}
