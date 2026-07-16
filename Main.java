import java.util.InputMismatchException;
import java.util.Scanner;

// Main class of Smart Learning Planner
public class Main {

    // Program execution starts from main method
    public static void main(String[] args) {

        // Scanner object for user input
        Scanner sc = new Scanner(System.in);

        System.out.println("WELCOME TO SMART LEARNING PLANNER:");

        // Student object reference
        Student student = null;

        // Variable to store user's menu choice
        int choice = 0;

        // Display menu options
        System.out.println("1.Enter new Data " + "\n" + "2.Load from file");

        try {

            // Read user choice
            choice = sc.nextInt();
            sc.nextLine();

            // Validate choice
            if (choice != 1 && choice != 2) {
                System.out.println("INVALID!! Enter 1 or 2");
            }
        }

        // Handles non-numeric menu input
        catch (InputMismatchException e) {
            System.out.println("Enter valid number");
        }

        // Option 1: Enter new data
        if (choice == 1) {

            try {

                // Get student name
                System.out.println("Enter a name:");
                String name = sc.nextLine();

                // Check if name field is empty
                if (name.isEmpty()) {
                    System.out.println("Field is empty");
                    name = sc.nextLine();
                }

                // Create Student object
                student = new Student(name);

                System.out.println("Welcome " + student.getName() + "!!");

                // Get number of subjects
                System.out.println("Enter number of subject");
                String nS = sc.nextLine();
                int numofsubject;

                // Validate subject count
                if (nS.isEmpty()) {
                    System.out.println("Please Enter a number");
                    nS = sc.nextLine();
                    numofsubject = Integer.parseInt(nS);
                } else {
                    numofsubject = Integer.parseInt(nS);
                }

                // Loop for entering subjects
                for (int i = 0; i < numofsubject; i++) {

                    // Subject name input
                    System.out.println("Enter Subject name:");
                    String subjectName = sc.nextLine();

                    if (subjectName.isEmpty()) {
                        System.out.println("Please enter a number");
                        subjectName = sc.nextLine();
                    }

                    // Exam date input
                    System.out.println("Enter Exam Date:");
                    String d = sc.nextLine();
                    int date;

                    if (d.isEmpty()) {
                        System.out.println("Please enter a number");
                        d = sc.nextLine();
                        date = Integer.parseInt(d);
                    } else {
                        date = Integer.parseInt(d);
                    }

                    // Exam month input
                    System.out.println("Enter Exam Month:");
                    String m = sc.nextLine();
                    int month;

                    if (m.isEmpty()) {
                        System.out.println("Please enter a number");
                        m = sc.nextLine();
                        month = Integer.parseInt(m);
                    } else {
                        month = Integer.parseInt(m);
                    }

                    // Exam year input
                    System.out.println("Enter Exam year:");
                    String y = sc.nextLine();
                    int year;

                    if (y.isEmpty()) {
                        System.out.println("Please enter a number");
                        y = sc.nextLine();
                        year = Integer.parseInt(y);
                    } else {
                        year = Integer.parseInt(y);
                    }

                    // Priority input
                    System.out.println("Enter priority of the subject(High,Medium,Low):");
                    String priority = sc.nextLine();

                    if (priority.isEmpty()) {
                        System.out.println("Please enter a data(Field is empty)");
                        priority = sc.nextLine();
                    }

                    // Create Subject object
                    Subject s = new Subject(subjectName, date, month, year, priority);

                    // Get number of weak topics
                    System.out.println("How many weak topics??");
                    String ttopics = sc.nextLine();
                    int totaltopics;

                    if (ttopics.isEmpty()) {
                        System.out.println("Please enter a number");
                        ttopics = sc.nextLine();
                        totaltopics = Integer.parseInt(ttopics);
                    } else {
                        totaltopics = Integer.parseInt(ttopics);
                    }

                    // Loop for weak topics
                    for (int j = 0; j < totaltopics; j++) {

                        System.out.println("Enter weak topic:" + (j + 1));

                        String weaktopic = sc.nextLine();

                        if (weaktopic.isEmpty()) {
                            System.out.println("Field is empty");
                            weaktopic = sc.nextLine();
                        }

                        // Add weak topic to subject
                        s.addWeakTopics(weaktopic);
                    }

                    // Add subject to student
                    student.addSubject(s);
                }
            }

            // Handles wrong numeric input
            catch (InputMismatchException e) {
                System.out.println("Enter valid number");
            }

            // Handles invalid number conversion
            catch (NumberFormatException e) {
                System.out.println("Invalid number entered: " + e.getMessage());
            }

            // Create FileHandler object
            FileHandler fh = new FileHandler(student);

            // Save student data to file
            fh.saveToFile();

            // Generate study schedule
            StudySchedule schedule = new StudySchedule(student);
            String generatedSchedule = schedule.generateSchedule();

            // Display generated schedule
            System.out.println(generatedSchedule);

            // Ask user if they want to mark a subject completed
            System.out.println("Mark a subject as completed? (yes/no)");
            String answer = sc.nextLine();

            if (answer.equals("yes")) {

                System.out.println("Enter subject name to mark complete:");
                String subjectName = sc.nextLine();

                // Search subject and mark completed
                for (int i = 0; i < student.getSubjects().size(); i++) {

                    if (student.getSubjects().get(i).getSubjectName().equals(subjectName)) {

                        student.getSubjects().get(i).setCompleted(true);

                        System.out.println(subjectName + " marked as completed!");

                        // Save updated data
                        FileHandler f1 = new FileHandler(student);
                        f1.saveToFile();
                    }
                }
            }

            // Save generated schedule to file
            fh.saveSchedule(generatedSchedule);
        }

        // Option 2: Load data from file
        else if (choice == 2) {

            FileHandler fh = new FileHandler(null);

            // Load student data
            student = fh.loadFromFile();

            if (student == null) {

                System.out.println("Switching to new data Entry....");

            } else {

                // Generate study schedule
                StudySchedule schedule = new StudySchedule(student);
                String generatedSchedule = schedule.generateSchedule();

                System.out.println(generatedSchedule);

                // Ask if subject should be marked completed
                System.out.println("Mark a subject as completed? (yes/no)");
                String answer = sc.nextLine();

                if (answer.equals("yes")) {

                    System.out.println("Enter subject name to mark complete:");
                    String subjectName = sc.nextLine();

                    // Find and mark subject completed
                    for (int i = 0; i < student.getSubjects().size(); i++) {

                        if (student.getSubjects().get(i).getSubjectName().equals(subjectName)) {

                            student.getSubjects().get(i).setCompleted(true);

                            System.out.println(subjectName + " marked as completed!");
                        }
                    }
                }

                // Save generated schedule
                fh.saveSchedule(generatedSchedule);
            }
        }
    }
}