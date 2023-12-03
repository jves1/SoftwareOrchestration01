public class Author {
   private int authorID;
   private String name;
   private String biography;
   private String photo;

   // Constructor
   public Author() {
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

   // toString method for debugging and logging purposes
   @Override
   public String toString() {
      return "Author{" +
             "authorID=" + authorID +
             ", name='" + name + '\'' +
             ", biography='" + biography + '\'' +
             ", photo='" + photo + '\'' +
             '}';
   }
   // Main method for testing
   public static void main(String[] args) {
      AuthorDAO authorDao = new AuthorDAO();
   
      // Creating a new author instance
      Author newAuthor = new Author();
      newAuthor.setAuthorID(1); // Set this to a unique ID
      newAuthor.setName("John Doe");
      newAuthor.setBiography("John Doe is an acclaimed author...");
      newAuthor.setPhoto("path/to/photo.jpg");
   
      // Add the new author to the database
      boolean isAdded = authorDao.addAuthor(newAuthor);
      if (isAdded) {
         System.out.println("Author added successfully.");
      } else {
         System.out.println("Failed to add the author.");
      }
   
      // Retrieve an author by ID
      Author retrievedAuthor = authorDao.getAuthorById(1);
      if (retrievedAuthor != null) {
         System.out.println("Author Retrieved: " + retrievedAuthor.toString());
      } else {
         System.out.println("Author not found.");
      }
   }
}
