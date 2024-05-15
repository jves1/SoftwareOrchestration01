import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class TaskManagerUI {
   private JFrame frame;
   private JTextField titleField, descriptionField, deadlineField;
   private JComboBox<String> priorityBox, statusBox;
   private JButton addButton, updateButton, deleteButton, searchButton;
   private JList<Task> taskList;
   private DefaultListModel<Task> taskListModel;
   private TaskController taskController;
   private JButton loadButton; 

   public TaskManagerUI() {
      this.taskController = new TaskController();
      initialize();
   }

   private void initialize() {
      // Frame setup
      frame = new JFrame("Personal Task Manager");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      frame.setSize(800, 600);
   
      // Task list setup
      taskListModel = new DefaultListModel<>();
      taskList = new JList<>(taskListModel);
      frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
   
      // Input fields
      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new GridLayout(5, 2, 10, 10)); // Grid layout for better organization
   
      titleField = new JTextField(15);
      descriptionField = new JTextField(25);
      deadlineField = new JTextField(10);
      priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
      statusBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
      
      inputPanel.add(new JLabel("Status:"),0,0);
      inputPanel.add(statusBox,0,1);
      inputPanel.add(new JLabel("Priority:"),1,0);
      inputPanel.add(priorityBox,1,1);
      inputPanel.add(new JLabel("Deadline (YYYY-MM-DD):"),2,0);
      inputPanel.add(deadlineField,2,1);
      inputPanel.add(new JLabel("Description:"),3,0);
      inputPanel.add(descriptionField,3,1);
      inputPanel.add(new JLabel("Title:"),4,0);
      inputPanel.add(titleField, 4,1);
                   
      
      frame.add(inputPanel, BorderLayout.NORTH);
   
      // Buttons
      addButton = new JButton("Add Task");
      updateButton = new JButton("Update Task");
      deleteButton = new JButton("Delete Task");
      searchButton = new JButton("Search Task");
      loadButton = new JButton("Load Task");
      
      addButton.addActionListener(this::addTask);
      updateButton.addActionListener(this::updateTask);
      deleteButton.addActionListener(this::deleteTask);
      searchButton.addActionListener(this::searchTasks);
      loadButton.addActionListener(this::loadTaskData);
   
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.add(addButton);
      buttonPanel.add(updateButton);
      buttonPanel.add(deleteButton);
      buttonPanel.add(searchButton);
      buttonPanel.add(loadButton);
      frame.add(buttonPanel, BorderLayout.SOUTH);
   
      // Show frame
      frame.setVisible(true);
   
      // Load tasks initially
      loadAllTasks();
   }

   private void addTask(ActionEvent e) {
      try {
         Task task = new Task(
            0, // Task ID will be set by the database
            titleField.getText(),
            descriptionField.getText(),
            (String) priorityBox.getSelectedItem(),
            LocalDate.parse(deadlineField.getText()),
            (String) statusBox.getSelectedItem()
            );
         taskController.addTask(task);
         loadAllTasks(); // Refresh the task list
      } catch (Exception ex) {
         showError("Error adding task: " + ex.getMessage());
      }
   }
   
   // Load Task Data Method
   private void loadTaskData(ActionEvent e) {
      Task selectedTask = taskList.getSelectedValue();
      if (selectedTask != null) {
         titleField.setText(selectedTask.getTitle());
         descriptionField.setText(selectedTask.getDescription());
         deadlineField.setText(selectedTask.getDeadline().toString());
         priorityBox.setSelectedItem(selectedTask.getPriority());
         statusBox.setSelectedItem(selectedTask.getStatus());
      } else {
         showError("No task selected to load");
      }
   }

   private void updateTask(ActionEvent e) {
      Task selectedTask = taskList.getSelectedValue();
      if (selectedTask != null) {
         try {
         // Update the task details from the input fields
            selectedTask.setTitle(titleField.getText());
            selectedTask.setDescription(descriptionField.getText());
            selectedTask.setDeadline(LocalDate.parse(deadlineField.getText()));
            selectedTask.setPriority((String) priorityBox.getSelectedItem());
            selectedTask.setStatus((String) statusBox.getSelectedItem());
         
         // Call the TaskController to update the task
            taskController.updateTask(selectedTask);
         
         // Refresh the task list to reflect the update
            loadAllTasks();
         } catch (Exception ex) {
            showError("Error updating task: " + ex.getMessage());
         }
      } else {
         showError("No task selected for updating");
      }
   }

   private void deleteTask(ActionEvent e) {
      Task selectedTask = taskList.getSelectedValue();
      if (selectedTask != null) {
         taskController.deleteTask(selectedTask.getTaskId());
         loadAllTasks(); // Refresh the task list
      } else {
         showError("No task selected");
      }
   }

   private void searchTasks(ActionEvent e) {
      String searchText = titleField.getText();
      List<Task> tasks = taskController.searchTasksByTitle(searchText);
      taskListModel.clear();
      tasks.forEach(taskListModel::addElement);
   }

   private void loadAllTasks() {
      List<Task> tasks = taskController.getAllTasks();
      taskListModel.clear();
      tasks.forEach(taskListModel::addElement);
   }

   private void showError(String message) {
      JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(TaskManagerUI::new);
   }
}
