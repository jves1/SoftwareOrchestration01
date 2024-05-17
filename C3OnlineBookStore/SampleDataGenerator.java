import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SampleDataGenerator {

    public static void insertSampleData() {
        insertAuthors();
        insertBooks();
    }

    private static void insertAuthors() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Authors (Name, Biography, Photo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, "Author One");
            stmt.setString(2, "Biography of Author One");
            stmt.setString(3, "photo1.jpg");
            stmt.executeUpdate();

            stmt.setString(1, "Author Two");
            stmt.setString(2, "Biography of Author Two");
            stmt.setString(3, "photo2.jpg");
            stmt.executeUpdate();

            stmt.setString(1, "Author Three");
            stmt.setString(2, "Biography of Author Three");
            stmt.setString(3, "photo3.jpg");
            stmt.executeUpdate();

            System.out.println("Sample authors inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertBooks() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Books (Title, AuthorID, ISBN, Price, PublishDate, CategoryID, Thumbnail, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, "Book One");
            stmt.setInt(2, 1); // Assuming Author One has ID 1
            stmt.setString(3, "1234567890123");
            stmt.setDouble(4, 19.99);
            stmt.setString(5, "2023-01-01");
            stmt.setString(6, "Fiction");
            stmt.setString(7, "thumbnail1.jpg");
            stmt.setString(8, "Description of Book One");
            stmt.executeUpdate();

            stmt.setString(1, "Book Two");
            stmt.setInt(2, 2); // Assuming Author Two has ID 2
            stmt.setString(3, "1234567890124");
            stmt.setDouble(4, 25.99);
            stmt.setString(5, "2023-02-01");
            stmt.setString(6, "Non-Fiction");
            stmt.setString(7, "thumbnail2.jpg");
            stmt.setString(8, "Description of Book Two");
            stmt.executeUpdate();

            stmt.setString(1, "Book Three");
            stmt.setInt(2, 3); // Assuming Author Three has ID 3
            stmt.setString(3, "1234567890125");
            stmt.setDouble(4, 15.99);
            stmt.setString(5, "2023-03-01");
            stmt.setString(6, "Science Fiction");
            stmt.setString(7, "thumbnail3.jpg");
            stmt.setString(8, "Description of Book Three");
            stmt.executeUpdate();

            System.out.println("Sample books inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}
