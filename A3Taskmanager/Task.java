import java.time.LocalDate;

public class Task {
    private int taskId; // Unique identifier for each task
    private String title;
    private String description;
    private String priority; // Values: "Low", "Medium", "High"
    private LocalDate deadline; // Using Java's LocalDate class for date representation
    private String status; // Values: "Pending", "In Progress", "Completed"

    // Constructor
    public Task(int taskId, String title, String description, String priority, LocalDate deadline, String status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                '}';
    }
}
