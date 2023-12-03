import java.util.List;

public class Search {

    private SearchDAO searchDAO;

    // Constructor
    public Search() {
        this.searchDAO = new SearchDAO();
    }

    // Method to search books by title
    public List<Book> searchBooksByTitle(String title) {
        return searchDAO.searchBooksByTitle(title);
    }

    // Additional search methods can be added here, like:
    // - searchBooksByAuthor(String authorName)
    // - searchBooksByCategory(String categoryName)
    // - etc.

    // Main method for testing
    public static void main(String[] args) {
        Search search = new Search();

        // Example search by title
        String searchTitle = "Example Book";
        List<Book> foundBooks = search.searchBooksByTitle(searchTitle);

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
