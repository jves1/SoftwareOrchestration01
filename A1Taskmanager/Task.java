import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private String priority;
    private LocalDate deadline;
    private boolean completionStatus;

    // Constructor to create a new task
    public Task(int id, String title, String description, String priority, LocalDate deadline, boolean completionStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completionStatus = completionStatus;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    // toString method for debugging and display purposes
    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", priority='" + priority + '\'' +
               ", deadline=" + deadline +
               ", completionStatus=" + completionStatus +
               '}';
    }
}
