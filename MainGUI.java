// Importing Swing library for GUI components like JFrame, JButton, JLabel etc.
import javax.swing.*;
// Importing AWT library for layouts and graphics
import java.awt.*;
// Importing event handling for button clicks and window actions
import java.awt.event.*;

// Main GUI class — contains all screens and logic in one place
public class MainGUI {

    // Main application window
    static JFrame frame;
    // CardLayout allows switching between different screens
    static CardLayout cardLayout;
    // Main panel that holds all screen panels
    static JPanel mainPanel;
    // Student object shared across all screens
    static Student student;

    // Subject input fields declared at class level so clearSubjectFields() can access them
    static JTextField subjectNameField;  // input field for subject name
    static JTextField dateField;         // input field for exam date
    static JTextField monthField;        // input field for exam month
    static JTextField yearField;         // input field for exam year
    static JComboBox<String> priorityBox; // dropdown for priority selection
    static JTextField weakTopicsField;   // input field for weak topics
    static JLabel counterLabel;          // label showing "Subject 1 of 3"
    static int totalSubjects = 0;        // total number of subjects to be entered
    static int currentSubject = 0;       // number of subjects entered so far

    // Entry point of the program
    public static void main(String[] args) {
        // Create the main window with title
        frame = new JFrame("Smart Exam Preparation Planner");
        // Create CardLayout for switching between screens
        cardLayout = new CardLayout();
        // Create main panel with CardLayout
        mainPanel = new JPanel(cardLayout);

        // Add all four screens to the main panel with their names
        mainPanel.add(welcomePanel(), "welcome");   // Screen 1
        mainPanel.add(studentPanel(), "student");   // Screen 2
        mainPanel.add(subjectPanel(), "subject");   // Screen 3
        mainPanel.add(progressPanel(), "progress"); // Screen 4

        // Add main panel to the frame
        frame.add(mainPanel);
        // Set window size
        frame.setSize(500, 450);
        // Prevent window from closing directly when X is pressed
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Show exit confirmation dialog when X button is pressed
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Show Yes/No confirmation dialog
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                // If user clicked Yes, close the program
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                // If user clicked No, do nothing and return to app
            }
        });

        // Center the window on screen
        frame.setLocationRelativeTo(null);
        // Make the window visible
        frame.setVisible(true);
        // Show the welcome screen first
        cardLayout.show(mainPanel, "welcome");
    }

    // -------------------------
    // Screen 1 — Welcome Panel
    // -------------------------
    static JPanel welcomePanel() {
        // Create panel with GridBagLayout for flexible positioning
        JPanel panel = new JPanel(new GridBagLayout());
        // Constraints object to control position and spacing
        GridBagConstraints g = new GridBagConstraints();
        // Add 10px padding around each component
        g.insets = new Insets(10, 10, 10, 10);

        // Create app title label
        JLabel title = new JLabel("SMART EXAM PREPARATION PLANNER");
        // Set font to bold and large
        title.setFont(new Font("Arial", Font.BOLD, 16));
        // Place in first row
        g.gridx = 0; g.gridy = 0;
        panel.add(title, g);

        // Create subtitle label
        JLabel subtitle = new JLabel("Please choose an option:");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        // Place in second row
        g.gridy = 1;
        panel.add(subtitle, g);

        // Button to enter new student data
        JButton newDataBtn = new JButton("Enter New Data");
        // Set button size
        newDataBtn.setPreferredSize(new Dimension(200, 40));
        // Place in third row
        g.gridy = 2;
        panel.add(newDataBtn, g);

        // Button to load existing data from file
        JButton loadBtn = new JButton("Load From File");
        loadBtn.setPreferredSize(new Dimension(200, 40));
        // Place in fourth row
        g.gridy = 3;
        panel.add(loadBtn, g);

        // When New Data button is clicked, go to student input screen
        newDataBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to Screen 2
                cardLayout.show(mainPanel, "student");
            }
        });

        // When Load button is clicked, load data from file
        loadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create FileHandler to load data
                FileHandler fh = new FileHandler(null);
                Student loaded = fh.loadFromFile();
                // If no file found or file is empty
                if (loaded == null) {
                    // Show warning message
                    JOptionPane.showMessageDialog(frame,
                            "No saved data found! Please enter new data.",
                            "File Not Found",
                            JOptionPane.WARNING_MESSAGE);
                    // Go to student panel to enter new data
                    cardLayout.show(mainPanel, "student");
                } else {
                    // Save loaded student to global variable
                    student = loaded;
                    // Go to progress tracking screen
                    cardLayout.show(mainPanel, "progress");
                }
            }
        });

        // Return the completed panel
        return panel;
    }

    // --------------------------
    // Screen 2 — Student Panel
    // --------------------------
    static JPanel studentPanel() {
        // Create new panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        // Stretch fields horizontally
        g.fill = GridBagConstraints.HORIZONTAL;

        // Screen title
        JLabel title = new JLabel("Student Information");
        title.setFont(new Font("Arial", Font.BOLD, 15));
        // Span across 2 columns in first row
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(title, g);

        // Reset grid width back to 1
        g.gridwidth = 1;
        // Label for name field
        JLabel nameLabel = new JLabel("Enter Your Name:");
        g.gridx = 0; g.gridy = 1;
        panel.add(nameLabel, g);

        // Text field for student name
        JTextField nameField = new JTextField(20);
        g.gridx = 1; g.gridy = 1;
        panel.add(nameField, g);

        // Label for number of subjects field
        JLabel numLabel = new JLabel("Number of Subjects:");
        g.gridx = 0; g.gridy = 2;
        panel.add(numLabel, g);

        // Text field for number of subjects
        JTextField numSubjectsField = new JTextField(20);
        g.gridx = 1; g.gridy = 2;
        panel.add(numSubjectsField, g);

        // Next button to proceed to subject input screen
        JButton nextBtn = new JButton("Next");
        nextBtn.setPreferredSize(new Dimension(150, 35));
        // Span across 2 columns in third row
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        panel.add(nextBtn, g);

        // Validate input and switch screen when Next is clicked
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get values from fields and remove whitespace
                String name = nameField.getText().trim();
                String numText = numSubjectsField.getText().trim();

                // Check if name field is empty
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your name!", "Empty Field", JOptionPane.WARNING_MESSAGE);
                    return; // Stop here, do not proceed
                }
                // Check if number of subjects field is empty
                if (numText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter number of subjects!", "Empty Field", JOptionPane.WARNING_MESSAGE);
                    return; // Stop here, do not proceed
                }

                try {
                    // Convert string to integer
                    int numSubjects = Integer.parseInt(numText);
                    // Check if number is zero or negative
                    if (numSubjects <= 0) {
                        JOptionPane.showMessageDialog(frame, "Number of subjects must be greater than 0!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    // Create new Student object with entered name
                    student = new Student(name);
                    // Save total subjects count
                    totalSubjects = numSubjects;
                    // Reset subject counter to zero
                    currentSubject = 0;
                    // Clear all subject input fields
                    clearSubjectFields();
                    // Update counter label
                    counterLabel.setText("Subject 1 of " + totalSubjects);
                    // Switch to subject input screen
                    cardLayout.show(mainPanel, "subject");

                } catch (NumberFormatException ex) {
                    // Input was not a valid number
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        return panel;
    }

    // --------------------------
    // Screen 3 — Subject Panel
    // --------------------------
    static JPanel subjectPanel() {
        // Create new panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Screen title
        JLabel title = new JLabel("Subject Information");
        title.setFont(new Font("Arial", Font.BOLD, 15));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(title, g);

        // Counter label showing current subject progress eg "Subject 2 of 4"
        counterLabel = new JLabel("Subject 1 of ?");
        counterLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        g.gridy = 1;
        panel.add(counterLabel, g);

        // Label and field for subject name
        g.gridwidth = 1;
        JLabel nameLabel = new JLabel("Subject Name:");
        g.gridx = 0; g.gridy = 2;
        panel.add(nameLabel, g);

        // Subject name field declared at class level for reset access
        subjectNameField = new JTextField(20);
        g.gridx = 1; g.gridy = 2;
        panel.add(subjectNameField, g);

        // Label and field for exam date
        JLabel dateLabel = new JLabel("Exam Date:");
        g.gridx = 0; g.gridy = 3;
        panel.add(dateLabel, g);

        // Date input field
        dateField = new JTextField(20);
        g.gridx = 1; g.gridy = 3;
        panel.add(dateField, g);

        // Label and field for exam month
        JLabel monthLabel = new JLabel("Exam Month:");
        g.gridx = 0; g.gridy = 4;
        panel.add(monthLabel, g);

        // Month input field
        monthField = new JTextField(20);
        g.gridx = 1; g.gridy = 4;
        panel.add(monthField, g);

        // Label and field for exam year
        JLabel yearLabel = new JLabel("Exam Year:");
        g.gridx = 0; g.gridy = 5;
        panel.add(yearLabel, g);

        // Year input field
        yearField = new JTextField(20);
        g.gridx = 1; g.gridy = 5;
        panel.add(yearField, g);

        // Label and dropdown for priority
        JLabel priorityLabel = new JLabel("Priority:");
        g.gridx = 0; g.gridy = 6;
        panel.add(priorityLabel, g);

        // Priority dropdown with three options
        priorityBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        g.gridx = 1; g.gridy = 6;
        panel.add(priorityBox, g);

        // Label and field for weak topics
        JLabel topicsLabel = new JLabel("Weak Topics (comma separated):");
        g.gridx = 0; g.gridy = 7;
        panel.add(topicsLabel, g);

        // Weak topics field — user types topics separated by commas
        weakTopicsField = new JTextField(20);
        g.gridx = 1; g.gridy = 7;
        panel.add(weakTopicsField, g);

        // Button to add the subject and move to next
        JButton addBtn = new JButton("Add Subject");
        addBtn.setPreferredSize(new Dimension(150, 35));
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        panel.add(addBtn, g);

        // Handle Add Subject button click
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Read all field values
                String subjectName = subjectNameField.getText().trim();
                String dateText = dateField.getText().trim();
                String monthText = monthField.getText().trim();
                String yearText = yearField.getText().trim();
                // Get selected value from priority dropdown
                String priority = (String) priorityBox.getSelectedItem();
                String topicsText = weakTopicsField.getText().trim();

                // Check if any required field is empty
                if (subjectName.isEmpty() || dateText.isEmpty() || monthText.isEmpty() || yearText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Empty Field", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // Convert string values to integers
                    int date = Integer.parseInt(dateText);
                    int month = Integer.parseInt(monthText);
                    int year = Integer.parseInt(yearText);

                    // Create new Subject object with all entered values
                    Subject s = new Subject(subjectName, date, month, year, priority);

                    // Add weak topics only if field is not empty
                    if (!topicsText.isEmpty()) {
                        // Split by comma to get individual topics
                        String[] topics = topicsText.split(",");
                        for (int i = 0; i < topics.length; i++) {
                            // trim() removes extra spaces around each topic
                            s.addWeakTopics(topics[i].trim());
                        }
                    }

                    // Add subject to the student object
                    student.addSubject(s);
                    // Increment subject counter
                    currentSubject++;

                    // Check if all subjects have been entered
                    if (currentSubject >= totalSubjects) {
                        // Save all data to file
                        FileHandler fh = new FileHandler(student);
                        fh.saveToFile();
                        // Show success message
                        JOptionPane.showMessageDialog(frame, "All subjects added! Data saved.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Go to progress tracking screen
                        cardLayout.show(mainPanel, "progress");
                    } else {
                        // More subjects remaining — clear fields for next subject
                        clearSubjectFields();
                        // Update counter label for next subject
                        counterLabel.setText("Subject " + (currentSubject + 1) + " of " + totalSubjects);
                        // Inform user to enter next subject
                        JOptionPane.showMessageDialog(frame, "Subject added! Enter next subject.", "Added", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    // Date, month or year was not a valid number
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for date, month, year!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        return panel;
    }

    // ----------------------------
    // Screen 4 — Progress Panel
    // ----------------------------
    static JPanel progressPanel() {
        // Create new panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Screen title
        JLabel title = new JLabel("Progress Tracking");
        title.setFont(new Font("Arial", Font.BOLD, 15));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(title, g);

        // Instruction label for user
        JLabel instruction = new JLabel("Enter subject name to mark as completed:");
        g.gridy = 1;
        panel.add(instruction, g);

        // Label for subject name field
        g.gridwidth = 1;
        JLabel subjectLabel = new JLabel("Subject Name:");
        g.gridx = 0; g.gridy = 2;
        panel.add(subjectLabel, g);

        // Input field for subject name to mark complete
        JTextField subjectField = new JTextField(20);
        g.gridx = 1; g.gridy = 2;
        panel.add(subjectField, g);

        // Button to mark subject as completed
        JButton markBtn = new JButton("Mark as Completed");
        markBtn.setPreferredSize(new Dimension(180, 35));
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        panel.add(markBtn, g);

        // Button to generate, save and view the schedule
        JButton scheduleBtn = new JButton("View & Save Schedule");
        scheduleBtn.setPreferredSize(new Dimension(180, 35));
        g.gridy = 4;
        panel.add(scheduleBtn, g);

        // Handle Mark as Completed button click
        markBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get subject name from field
                String subjectName = subjectField.getText().trim();
                // Check if field is empty
                if (subjectName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a subject name!", "Empty Field", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Track whether subject was found
                boolean found = false;
                // Loop through all subjects to find matching name
                for (int i = 0; i < student.getSubjects().size(); i++) {
                    // Compare subject name
                    if (student.getSubjects().get(i).getSubjectName().equals(subjectName)) {
                        // Mark subject as completed
                        student.getSubjects().get(i).setCompleted(true);
                        found = true;
                        // Save updated data to file
                        FileHandler fh = new FileHandler(student);
                        fh.saveToFile();
                        // Show success message
                        JOptionPane.showMessageDialog(frame, subjectName + " marked as completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Clear the input field
                        subjectField.setText("");
                        // Stop searching
                        break;
                    }
                }

                // If subject name was not found in the list
                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Subject not found! Check the name and try again.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Handle View and Save Schedule button click
        scheduleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create StudySchedule object for the current student
                StudySchedule schedule = new StudySchedule(student);
                // Generate the schedule and store in string
                String generatedSchedule = schedule.generateSchedule();
                // Save schedule to studyPlan.txt file
                FileHandler fh = new FileHandler(student);
                fh.saveSchedule(generatedSchedule);
                // Open schedule in a separate window
                showScheduleWindow(generatedSchedule);
            }
        });

        return panel;
    }

    // ------------------------------
    // Schedule Window — Separate Window
    // ------------------------------
    static void showScheduleWindow(String schedule) {
        // Create a new separate frame for schedule display
        JFrame scheduleFrame = new JFrame("Study Schedule");
        // Set window size
        scheduleFrame.setSize(500, 450);
        // Use BorderLayout — North for title, Center for text, South for button
        scheduleFrame.setLayout(new BorderLayout());

        // Title label centered at the top
        JLabel title = new JLabel("Your Study Schedule", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        // Add padding around title
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        // Add to top of window
        scheduleFrame.add(title, BorderLayout.NORTH);

        // Text area to display the schedule
        JTextArea textArea = new JTextArea(schedule);
        // Make it read only — user cannot edit
        textArea.setEditable(false);
        // Monospaced font keeps text aligned properly
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        // Add inner padding to text area
        textArea.setMargin(new Insets(10, 10, 10, 10));
        // Wrap text area in scroll pane for scrolling if content is long
        JScrollPane scrollPane = new JScrollPane(textArea);
        // Add to center of window
        scheduleFrame.add(scrollPane, BorderLayout.CENTER);

        // Close button at the bottom
        JButton closeBtn = new JButton("Close");
        closeBtn.setPreferredSize(new Dimension(120, 35));
        // Panel to hold the close button
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeBtn);
        // Add to bottom of window
        scheduleFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Close button only closes this window, not the entire application
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Dispose only this schedule window
                scheduleFrame.dispose();
            }
        });

        // Center this window relative to main frame
        scheduleFrame.setLocationRelativeTo(frame);
        // Make schedule window visible
        scheduleFrame.setVisible(true);
    }

    // Method to clear all subject input fields for next subject entry
    static void clearSubjectFields() {
        subjectNameField.setText("");    // clear subject name
        dateField.setText("");           // clear exam date
        monthField.setText("");          // clear exam month
        yearField.setText("");           // clear exam year
        priorityBox.setSelectedIndex(0); // reset priority to High
        weakTopicsField.setText("");     // clear weak topics
    }
}