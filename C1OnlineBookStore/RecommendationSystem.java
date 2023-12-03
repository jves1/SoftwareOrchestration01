import java.util.List;

public class RecommendationSystem {

    private RecommendationSystemDAO recommendationSystemDAO;

    // Constructor
    public RecommendationSystem() {
        this.recommendationSystemDAO = new RecommendationSystemDAO();
    }

    // Method to get book recommendations for a user
    public List<Book> getRecommendations(int userID) {
        return recommendationSystemDAO.recommendBooksByCategory(userID);
    }

    // Main method for testing
    public static void main(String[] args) {
        RecommendationSystem recommendationSystem = new RecommendationSystem();

        // Test getting book recommendations for a user
        int userID = 1; // Example user ID
        List<Book> recommendedBooks = recommendationSystem.getRecommendations(userID);

        if (!recommendedBooks.isEmpty()) {
            System.out.println("Recommended Books for User ID " + userID + ":");
            for (Book book : recommendedBooks) {
                System.out.println(book.toString());
            }
        } else {
            System.out.println("No recommendations found for User ID: " + userID);
        }
    }
}
