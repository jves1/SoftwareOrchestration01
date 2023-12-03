public class DataValidator {

    // Validates that a string is not empty
    public static boolean validateNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validates that an email is in a proper format
    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Simple regex for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Validates that a string is a valid date in the format "yyyy-MM-dd"
    public static boolean validateDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        // Simple regex for date validation (yyyy-MM-dd)
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateRegex);
    }

    // Validates that a string is a valid double value
    public static boolean validateDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Main method for standalone testing
    public static void main(String[] args) {
        // Test the validation methods
        System.out.println("Testing Validation Methods:");
        System.out.println("Is 'email@example.com' a valid email? " + validateEmail("email@example.com"));
        System.out.println("Is '2021-12-31' a valid date? " + validateDate("2021-12-31"));
        System.out.println("Is '123.45' a valid double? " + validateDouble("123.45"));
        System.out.println("Is '   ' not empty? " + validateNotEmpty("   "));
    }
}
