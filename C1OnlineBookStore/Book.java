import java.util.Date;

public class Book {
   private int bookID;
   private String title;
   private int authorID;
   private String ISBN;
   private double price;
   private Date publishDate;
   private int categoryID;
   private String thumbnail;
   private String description;

    // Constructor
   public Book() {
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

   public Date getPublishDate() {
      return publishDate;
   }

   public void setPublishDate(Date publishDate) {
      this.publishDate = publishDate;
   }

   public int getCategoryID() {
      return categoryID;
   }

   public void setCategoryID(int categoryID) {
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

    // toString method for debugging and logging purposes
   @Override
    public String toString() {
      return "Book{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", authorID=" + authorID +
                ", ISBN='" + ISBN + '\'' +
                ", price=" + price +
                ", publishDate=" + publishDate +
                ", categoryID=" + categoryID +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                '}';
   }

    // Additional methods like saveToDatabase, getBookByID, etc. can be added here or in a separate DAO class
    
   public static void main(String[] args) {
      BookDAO bookDao = new BookDAO();
   
    // Creating a new book instance
      Book newBook = new Book();
      newBook.setBookID(1); // Example ID
      newBook.setTitle("Example Book");
      newBook.setAuthorID(1);
      newBook.setISBN("1234567890");
      newBook.setPrice(19.99);
      newBook.setPublishDate(new java.util.Date()); // Set current date as publish date
      newBook.setCategoryID(1);
      newBook.setThumbnail("example_thumbnail.jpg");
      newBook.setDescription("This is an example book.");
    
    // Save the new book to the database
      boolean isAdded = bookDao.addBook(newBook);
      if (isAdded) {
         System.out.println("New book added successfully.");
      } else {
         System.out.println("Failed to add the new book.");
      }
   
    // Retrieve a book by ID
      Book retrievedBook = bookDao.getBookById(1);
      if (retrievedBook != null) {
         System.out.println("Book Retrieved: " + retrievedBook.toString());
      } else {
         System.out.println("Book not found.");
      }
   }
}
