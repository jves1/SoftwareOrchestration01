import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {
    private int authorID;
    private String name;
    private String biography;
    private String photo;

    // Constructor
    public Author(int authorID, String name, String biography, String photo) {
        this.authorID = authorID;
        this.name = name;
        this.biography = biography;
        this.photo = photo;
    }

    // Getters and Setters
    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Database Interaction Methods
    public static Author loadFromDatabase(int authorID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Authors WHERE AuthorID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, authorID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Author(
                    rs.getInt("AuthorID"),
                    rs.getString("Name"),
                    rs.getString("Biography"),
                    rs.getString("Photo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Authors (Name, Biography, Photo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, biography);
            stmt.setString(3, photo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Authors WHERE AuthorID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, authorID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
