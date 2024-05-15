import java.util.List;

public class TaskController {
    private TaskDatabase taskDatabase;

    public TaskController() {
        this.taskDatabase = new TaskDatabase();
    }

    // Method to add a new task
    public void addTask(Task task) {
        taskDatabase.addTask(task);
    }

    // Method to update an existing task
    public void updateTask(Task task) {
        taskDatabase.updateTask(task);
    }

    // Method to delete a task
    public void deleteTask(int taskId) {
        taskDatabase.deleteTask(taskId);
    }

    // Method to retrieve a task by ID
    public Task getTask(int taskId) {
        return taskDatabase.getTask(taskId);
    }

    // Method to retrieve all tasks
    public List<Task> getAllTasks() {
        return taskDatabase.getAllTasks();
    }

    // Method to search tasks by title
    public List<Task> searchTasksByTitle(String title) {
        return taskDatabase.searchTasksByTitle(title);
    }
}
