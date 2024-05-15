public class Task {
   private int taskId;
   private String title;
   private String description;
   private String priority; // Can be 'Low', 'Medium', 'High'
   private java.sql.Date deadline;
   private String status; // Can be 'Pending', 'In Progress', 'Completed'

   // Constructor
   public Task(int taskId, String title, String description, String priority, java.sql.Date deadline, String status) {
      this.taskId = taskId;
      this.title = title;
      this.description = description;
      this.priority = priority;
      this.deadline = deadline;
      this.status = status;
   }

   // Getters and Setters
   public int getTaskId() { 
      return taskId; }
   public void setTaskId(int taskId) { this.taskId = taskId; }

   public String getTitle() { 
      return title; }
   public void setTitle(String title) { this.title = title; }

   public String getDescription() { 
      return description; }
   public void setDescription(String description) { this.description = description; }

   public String getPriority() { 
      return priority; }
   public void setPriority(String priority) { this.priority = priority; }

   public java.sql.Date getDeadline() { 
      return deadline; }
   public void setDeadline(java.sql.Date deadline) { this.deadline = deadline; }

   public String getStatus() { 
      return status; }
   public void setStatus(String status) { this.status = status; }
   
   @Override
   public String toString() {
      return "Task ID: " + taskId + ", Title: " + title + ", Description: " + description 
             + ", Priority: " + priority + ", Deadline: " + deadline + ", Status: " + status;
   }
}
