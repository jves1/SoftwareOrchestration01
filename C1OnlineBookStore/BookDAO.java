import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // CREATE - Add a new book to the database
   public boolean addBook(Book book) {
      String query = "INSERT INTO Books (BookID, Title, AuthorID, ISBN, Price, PublishDate, CategoryID, Thumbnail, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setInt(1, book.getBookID());
         statement.setString(2, book.getTitle());
         statement.setInt(3, book.getAuthorID());
         statement.setString(4, book.getISBN());
         statement.setDouble(5, book.getPrice());
        
         java.sql.Date sqlPublishDate = book.getPublishDate() != null ? new java.sql.Date(book.getPublishDate().getTime()) : null;
         statement.setDate(6, sqlPublishDate);
        
         statement.setInt(7, book.getCategoryID());
         statement.setString(8, book.getThumbnail());
         statement.setString(9, book.getDescription());
      
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

    // READ - Get a book by ID
   public Book getBookById(int bookId) {
      String query = "SELECT * FROM Books WHERE BookID = ?";
      try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setInt(1, bookId);
         ResultSet resultSet = statement.executeQuery();
      
         if (resultSet.next()) {
            return extractBookFromResultSet(resultSet);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

    // READ - Get all books
   public List<Book> getAllBooks() {
      List<Book> books = new ArrayList<>();
      String query = "SELECT * FROM Books";
      try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
      
         while (resultSet.next()) {
            Book book = extractBookFromResultSet(resultSet);
            books.add(book);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return books;
   }

    // UPDATE - Update an existing book
   public boolean updateBook(Book book) {
      String query = "UPDATE Books SET Title = ?, AuthorID = ?, ISBN = ?, Price = ?, PublishDate = ?, CategoryID = ?, Thumbnail = ?, Description = ? WHERE BookID = ?";
      try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setString(1, book.getTitle());
         statement.setInt(2, book.getAuthorID());
         statement.setString(3, book.getISBN());
         statement.setDouble(4, book.getPrice());
         statement.setDate(5, new java.sql.Date(book.getPublishDate().getTime()));
         statement.setInt(6, book.getCategoryID());
         statement.setString(7, book.getThumbnail());
         statement.setString(8, book.getDescription());
         statement.setInt(9, book.getBookID());
      
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

    // DELETE - Remove a book from the database
   public boolean deleteBook(int bookId) {
      String query = "DELETE FROM Books WHERE BookID = ?";
      try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setInt(1, bookId);
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

    // Helper method to extract book data from ResultSet
   private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
      Book book = new Book();
        
      book.setBookID(resultSet.getInt("BookID"));
      book.setTitle(resultSet.getString("Title"));
      book.setAuthorID(resultSet.getInt("AuthorID"));
      book.setISBN(resultSet.getString("ISBN"));
      book.setPrice(resultSet.getDouble("Price"));
      book.setPublishDate(resultSet.getDate("PublishDate"));
      book.setCategoryID(resultSet.getInt("CategoryID"));
      book.setThumbnail(resultSet.getString("Thumbnail"));
      book.setDescription(resultSet.getString("Description"));
   
      return book;
   }
}
