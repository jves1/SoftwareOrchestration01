import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // CREATE - Add a new category to the database
    public boolean addCategory(Category category) {
        String query = "INSERT INTO Categories (CategoryID, Name) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, category.getCategoryID());
            statement.setString(2, category.getName());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get a category by ID
    public Category getCategoryById(int categoryId) {
        String query = "SELECT * FROM Categories WHERE CategoryID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractCategoryFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Categories";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Category category = extractCategoryFromResultSet(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // UPDATE - Update an existing category
    public boolean updateCategory(Category category) {
        String query = "UPDATE Categories SET Name = ? WHERE CategoryID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getName());
            statement.setInt(2, category.getCategoryID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove a category from the database
    public boolean deleteCategory(int categoryId) {
        String query = "DELETE FROM Categories WHERE CategoryID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract category data from ResultSet
    private Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();

        category.setCategoryID(resultSet.getInt("CategoryID"));
        category.setName(resultSet.getString("Name"));

        return category;
    }
}
