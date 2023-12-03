import javax.swing.*;
import java.sql.*;

public class ErrorHandler {

    // Displays a generic error message with the given text
    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Displays a more specific error message based on the exception
    public static void handleException(Exception ex) {
        // You can customize this method to handle different types of exceptions differently
        // For example, SQL exceptions can have a different message than generic exceptions
        if (ex instanceof SQLException) {
            showErrorDialog("A database error occurred: " + ex.getMessage());
        } else {
            showErrorDialog("An unexpected error occurred: " + ex.getMessage());
        }
    }

    // Optional: Method to log errors to a file or external system
    // public static void logError(Exception ex) {
    //     // Implement logging functionality here
    //     // This could write to a log file, send to an error tracking service, etc.
    // }

    // Main method for standalone testing
    public static void main(String[] args) {
        // Test displaying a generic error message
        showErrorDialog("This is a test error message.");

        // Test handling an exception
        try {
            throw new SQLException("Test SQL Exception");
        } catch (Exception ex) {
            handleException(ex);
        }
    }
}
