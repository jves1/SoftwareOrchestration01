import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {

    public static List<String> searchBooks(String keyword) {
        List<String> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Books WHERE Title LIKE ? OR Description LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add("Title: " + rs.getString("Title") + "\n" +
                            "AuthorID: " + rs.getInt("AuthorID") + "\n" +
                            "ISBN: " + rs.getString("ISBN") + "\n" +
                            "Price: " + rs.getDouble("Price") + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
