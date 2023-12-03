import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    // CREATE - Add a new author to the database
    public boolean addAuthor(Author author) {
        String query = "INSERT INTO Authors (AuthorID, Name, Biography, Photo) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, author.getAuthorID());
            statement.setString(2, author.getName());
            statement.setString(3, author.getBiography());
            statement.setString(4, author.getPhoto());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get an author by ID
    public Author getAuthorById(int authorId) {
        String query = "SELECT * FROM Authors WHERE AuthorID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, authorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractAuthorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all authors
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Authors";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    // UPDATE - Update an existing author
    public boolean updateAuthor(Author author) {
        String query = "UPDATE Authors SET Name = ?, Biography = ?, Photo = ? WHERE AuthorID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, author.getName());
            statement.setString(2, author.getBiography());
            statement.setString(3, author.getPhoto());
            statement.setInt(4, author.getAuthorID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove an author from the database
    public boolean deleteAuthor(int authorId) {
        String query = "DELETE FROM Authors WHERE AuthorID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, authorId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract author data from ResultSet
    private Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();

        author.setAuthorID(resultSet.getInt("AuthorID"));
        author.setName(resultSet.getString("Name"));
        author.setBiography(resultSet.getString("Biography"));
        author.setPhoto(resultSet.getString("Photo"));

        return author;
    }
}
