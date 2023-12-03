public class Category {
    private int categoryID;
    private String name;

    // Constructor
    public Category() {
    }

    // Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "Category{" +
                "categoryID=" + categoryID +
                ", name='" + name + '\'' +
                '}';
    }

    // Main method for testing
    public static void main(String[] args) {
        CategoryDAO categoryDao = new CategoryDAO();

        // Creating a new category instance
        Category newCategory = new Category();
        newCategory.setCategoryID(1); // Set this to a unique ID
        newCategory.setName("Fiction");

        // Add the new category to the database
        boolean isAdded = categoryDao.addCategory(newCategory);
        if (isAdded) {
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Failed to add the category.");
        }

        // Retrieve a category by ID
        Category retrievedCategory = categoryDao.getCategoryById(1);
        if (retrievedCategory != null) {
            System.out.println("Category Retrieved: " + retrievedCategory.toString());
        } else {
            System.out.println("Category not found.");
        }
    }
}
