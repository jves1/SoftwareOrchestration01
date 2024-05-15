import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class TaskManagerUI {
   private JFrame frame;
   private TaskDatabaseManager dbManager;
   private JTextField titleField, descriptionField, priorityField, deadlineField, statusField, taskIdField;
   private JTextArea taskListArea;

   // Constructor
   public TaskManagerUI(TaskDatabaseManager dbManager) {
      this.dbManager = dbManager;
      initializeUI();
   }

   // Initialize the user interface
   private void initializeUI() {
      frame = new JFrame("Personal Task Manager");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 400);
      frame.setLayout(new BorderLayout());
   
      createMenu();
      createMainPanel();
      refreshTaskList();
   
      frame.setVisible(true);
   }

   // Create menu
   private void createMenu() {
      JMenuBar menuBar = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(e -> System.exit(0));
      fileMenu.add(exitItem);
      menuBar.add(fileMenu);
      frame.setJMenuBar(menuBar);
   }

   // Create main panel
   private void createMainPanel() {
      JPanel mainPanel = new JPanel(new BorderLayout());
   
      // Task input panel
      JPanel inputPanel = new JPanel(new GridLayout(6, 2));
      inputPanel.add(new JLabel("Task ID:"));
      taskIdField = new JTextField();
      inputPanel.add(taskIdField);
      inputPanel.add(new JLabel("Title:"));
      titleField = new JTextField();
      inputPanel.add(titleField);
      inputPanel.add(new JLabel("Description:"));
      descriptionField = new JTextField();
      inputPanel.add(descriptionField);
      inputPanel.add(new JLabel("Priority:"));
      priorityField = new JTextField();
      inputPanel.add(priorityField);
      inputPanel.add(new JLabel("Deadline:"));
      deadlineField = new JTextField();
      inputPanel.add(deadlineField);
      inputPanel.add(new JLabel("Status:"));
      statusField = new JTextField();
      inputPanel.add(statusField);
   
      // Buttons panel
      JPanel buttonsPanel = new JPanel(new FlowLayout());
      JButton addButton = new JButton("Add Task");
      addButton.addActionListener(new AddTaskActionListener());
      buttonsPanel.add(addButton);
      JButton updateButton = new JButton("Update Task");
      updateButton.addActionListener(new UpdateTaskActionListener());
      buttonsPanel.add(updateButton);
      JButton deleteButton = new JButton("Delete Task");
      deleteButton.addActionListener(new DeleteTaskActionListener());
      buttonsPanel.add(deleteButton);
      JButton refreshButton = new JButton("Refresh List");
      refreshButton.addActionListener(e -> refreshTaskList());
      buttonsPanel.add(refreshButton);
      // New button for displaying a task
      JButton displayButton = new JButton("Display Task");
      displayButton.addActionListener(new DisplayTaskActionListener());
      buttonsPanel.add(displayButton);
   
   
      // Task list area
      taskListArea = new JTextArea();
      taskListArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(taskListArea);
   
      mainPanel.add(inputPanel, BorderLayout.NORTH);
      mainPanel.add(scrollPane, BorderLayout.CENTER);
      mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
   
      frame.add(mainPanel);
   }

   // Action listener for adding a task
   private class AddTaskActionListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         Task task = new Task(0, titleField.getText(), descriptionField.getText(),
                priorityField.getText(), Date.valueOf(deadlineField.getText()),
                statusField.getText());
         if (dbManager.addTask(task)) {
            JOptionPane.showMessageDialog(frame, "Task added successfully.");
            clearInputFields();
            refreshTaskList();
         } else {
            JOptionPane.showMessageDialog(frame, "Failed to add task.");
         }
      }
   }

   // Action listener for updating a task
   private class UpdateTaskActionListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         Task task = new Task(Integer.parseInt(taskIdField.getText()), titleField.getText(),
                descriptionField.getText(), priorityField.getText(),
                Date.valueOf(deadlineField.getText()), statusField.getText());
         if (dbManager.updateTask(task)) {
            JOptionPane.showMessageDialog(frame, "Task updated successfully.");
            clearInputFields();
            refreshTaskList();
         } else {
            JOptionPane.showMessageDialog(frame, "Failed to update task.");
         }
      }
   }

   // Action listener for deleting a task
   private class DeleteTaskActionListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         int taskId = Integer.parseInt(taskIdField.getText());
         if (dbManager.deleteTask(taskId)) {
            JOptionPane.showMessageDialog(frame, "Task deleted successfully.");
            clearInputFields();
            refreshTaskList();
         } else {
            JOptionPane.showMessageDialog(frame, "Failed to delete task.");
         }
      }
   }
   
   // Action listener for displaying a task
   private class DisplayTaskActionListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         try {
            int taskId = Integer.parseInt(taskIdField.getText());
            Task task = dbManager.getTaskById(taskId);
            if (task != null) {
               titleField.setText(task.getTitle());
               descriptionField.setText(task.getDescription());
               priorityField.setText(task.getPriority());
               deadlineField.setText(task.getDeadline().toString());
               statusField.setText(task.getStatus());
            } else {
               JOptionPane.showMessageDialog(frame, "Task not found.");
            }
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid Task ID.");
         }
      }
   }

   // Refresh the task list in the text area
   private void refreshTaskList() {
      List<Task> tasks = dbManager.getAllTasks();
      StringBuilder sb = new StringBuilder();
      for (Task task : tasks) {
         sb.append(task.toString()).append("\n");
      }
      taskListArea.setText(sb.toString());
   }

   // Clear input fields
   private void clearInputFields() {
      titleField.setText("");
      descriptionField.setText("");
      priorityField.setText("");
      deadlineField.setText("");
      statusField.setText("");
      taskIdField.setText("");
   }
}
