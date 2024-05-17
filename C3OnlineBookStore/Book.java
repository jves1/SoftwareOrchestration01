import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private int bookID;
    private String title;
    private int authorID;
    private String ISBN;
    private double price;
    private String publishDate;
    private String categoryID;
    private String thumbnail;
    private String description;

    // Constructor
    public Book(int bookID, String title, int authorID, String ISBN, double price, String publishDate, String categoryID, String thumbnail, String description) {
        this.bookID = bookID;
        this.title = title;
        this.authorID = authorID;
        this.ISBN = ISBN;
        this.price = price;
        this.publishDate = publishDate;
        this.categoryID = categoryID;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    // Getters and Setters
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Database Interaction Methods
    public static Book loadFromDatabase(int bookID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Books WHERE BookID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("BookID"),
                    rs.getString("Title"),
                    rs.getInt("AuthorID"),
                    rs.getString("ISBN"),
                    rs.getDouble("Price"),
                    rs.getString("PublishDate"),
                    rs.getString("CategoryID"),
                    rs.getString("Thumbnail"),
                    rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Books (Title, AuthorID, ISBN, Price, PublishDate, CategoryID, Thumbnail, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setInt(2, authorID);
            stmt.setString(3, ISBN);
            stmt.setDouble(4, price);
            stmt.setString(5, publishDate);
            stmt.setString(6, categoryID);
            stmt.setString(7, thumbnail);
            stmt.setString(8, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Books WHERE BookID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Additional Functionalities
    public static ResultSet searchBooks(String keyword) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Books WHERE Title LIKE ? OR Description LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
