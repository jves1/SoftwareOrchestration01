import java.util.Date;

public class Review {
    private int reviewID;
    private int bookID;
    private int userID;
    private int rating;
    private String comment;
    private Date datePosted;

    // Constructor
    public Review() {
    }

    // Getters and Setters
    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", bookID=" + bookID +
                ", userID=" + userID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", datePosted=" + datePosted +
                '}';
    }

    // Main method for testing
    public static void main(String[] args) {
        ReviewDAO reviewDao = new ReviewDAO();

        // Creating a new review instance
        Review newReview = new Review();
        newReview.setReviewID(1); // Example review ID
        newReview.setBookID(1); // Example book ID
        newReview.setUserID(1); // Example user ID
        newReview.setRating(5); // Example rating
        newReview.setComment("Great book!"); // Example comment
        newReview.setDatePosted(new Date()); // Current date for example

        // Add the new review to the database
        boolean isAdded = reviewDao.addReview(newReview);
        if (isAdded) {
            System.out.println("Review added successfully.");
        } else {
            System.out.println("Failed to add the review.");
        }

        // Retrieve a review by ID
        Review retrievedReview = reviewDao.getReviewById(1);
        if (retrievedReview != null) {
            System.out.println("Review Retrieved: " + retrievedReview.toString());
        } else {
            System.out.println("Review not found.");
        }
    }
}
