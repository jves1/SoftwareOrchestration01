import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // CREATE - Add a new review to the database
    public boolean addReview(Review review) {
        String query = "INSERT INTO Reviews (ReviewID, BookID, UserID, Rating, Comment, DatePosted) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, review.getReviewID());
            statement.setInt(2, review.getBookID());
            statement.setInt(3, review.getUserID());
            statement.setInt(4, review.getRating());
            statement.setString(5, review.getComment());
            statement.setDate(6, new java.sql.Date(review.getDatePosted().getTime()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get a review by ID
    public Review getReviewById(int reviewId) {
        String query = "SELECT * FROM Reviews WHERE ReviewID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reviewId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractReviewFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all reviews
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM Reviews";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Review review = extractReviewFromResultSet(resultSet);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // UPDATE - Update an existing review
    public boolean updateReview(Review review) {
        String query = "UPDATE Reviews SET BookID = ?, UserID = ?, Rating = ?, Comment = ?, DatePosted = ? WHERE ReviewID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, review.getBookID());
            statement.setInt(2, review.getUserID());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setDate(5, new java.sql.Date(review.getDatePosted().getTime()));
            statement.setInt(6, review.getReviewID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove a review from the database
    public boolean deleteReview(int reviewId) {
        String query = "DELETE FROM Reviews WHERE ReviewID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reviewId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract review data from ResultSet
    private Review extractReviewFromResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();

        review.setReviewID(resultSet.getInt("ReviewID"));
        review.setBookID(resultSet.getInt("BookID"));
        review.setUserID(resultSet.getInt("UserID"));
        review.setRating(resultSet.getInt("Rating"));
        review.setComment(resultSet.getString("Comment"));
        review.setDatePosted(resultSet.getDate("DatePosted"));

        return review;
    }
}
