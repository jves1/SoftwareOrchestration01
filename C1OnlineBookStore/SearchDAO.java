import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchDAO {

   // Method to search books by title
   public List<Book> searchBooksByTitle(String title) {
      List<Book> books = new ArrayList<>();
      String query = "SELECT * FROM Books WHERE Title LIKE ?";
      try (Connection connection = DatabaseConnection.getConnection();
          PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setString(1, "%" + title + "%");
         ResultSet resultSet = statement.executeQuery();
      
         while (resultSet.next()) {
            Book book = new Book();
            book.setBookID(resultSet.getInt("BookID"));
            book.setTitle(resultSet.getString("Title"));
            book.setAuthorID(resultSet.getInt("AuthorID"));
            // ... set other book attributes
            books.add(book);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return books;
   }
   
   public Book getBookByID(int bookID) {
      String query = "SELECT * FROM Books WHERE BookID = ?";
      try (Connection connection = DatabaseConnection.getConnection(); // Replace with your actual connection method
             PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setInt(1, bookID);
         ResultSet resultSet = statement.executeQuery();
      
         if (resultSet.next()) {
            Book newBook = new Book();
            newBook.setBookID(resultSet.getInt("BookID"));
            newBook.setTitle(resultSet.getString("Title"));
            newBook.setAuthorID(resultSet.getInt("AuthorID"));
            newBook.setISBN(resultSet.getString("ISBN"));
            newBook.setPrice(resultSet.getDouble("Price"));
                // Assuming PublishDate is stored as a SQL Date/Timestamp
            newBook.setPublishDate(new java.util.Date(resultSet.getTimestamp("PublishDate").getTime()));
            newBook.setCategoryID(resultSet.getInt("CategoryID"));
            newBook.setThumbnail(resultSet.getString("Thumbnail"));
            newBook.setDescription(resultSet.getString("Description"));
            return newBook;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null; // Return null if the book is not found or in case of an error
   }

    // Method to get the price of a book by its ID
   public double getPriceByBookID(int bookID) {
      String query = "SELECT Price FROM Books WHERE BookID = ?";
      try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
      
         statement.setInt(1, bookID);
         ResultSet resultSet = statement.executeQuery();
      
         if (resultSet.next()) {
            return resultSet.getDouble("Price");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0.0; // Return 0.0 if the book is not found or in case of an error
   }


   // Additional search methods can be added here, like:
   // - searchBooksByAuthor(String authorName)
   // - searchBooksByCategory(String categoryName)
   // - etc.

   // Main method for testing
   public static void main(String[] args) {
      SearchDAO searchDao = new SearchDAO();
   
      // Example search by title
      String searchTitle = "Example Book Title";
      List<Book> foundBooks = searchDao.searchBooksByTitle(searchTitle);
   
      if (!foundBooks.isEmpty()) {
         System.out.println("Books found: ");
         for (Book book : foundBooks) {
            System.out.println(book.toString());
         }
      } else {
         System.out.println("No books found with title: " + searchTitle);
      }
   }
}
