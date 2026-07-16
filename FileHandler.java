import java.io.*;

// Handles saving and loading student data from files
public class FileHandler {

    // File name used for storing student data
    private String fileName = "studentData.txt";

    // Student object whose data will be saved
    private Student student;

    // Constructor to initialize student object
    FileHandler(Student student) {
        this.student = student;
    }

    // Saves student data to a text file
    public void saveToFile() {
        try {

            // Create writer object for file
            BufferedWriter bw = new BufferedWriter(new FileWriter("studentData.txt"));

            // Save student name
            bw.write(student.getName());
            bw.newLine();

            // Loop through all subjects
            for (int i = 0; i < student.getSubjects().size(); i++) {

                Subject subject = student.getSubjects().get(i);

                // Save subject details in comma-separated format
                bw.write(subject.getSubjectName() + "," +
                        String.valueOf(subject.getExamDate()) + "," +
                        String.valueOf(subject.getExamMonth()) + "," +
                        String.valueOf(subject.getExamYear()) + "," +
                        subject.getPriority() + "," +
                        subject.isCompleted());

                bw.newLine();

                // Save weak topics
                for (int j = 0; j < subject.getWeakTopics().size(); j++) {

                    if (j < subject.getWeakTopics().size() - 1) {
                        bw.write(subject.getWeakTopics().get(j) + ",");
                    } else {
                        bw.write(subject.getWeakTopics().get(j));
                    }
                }

                bw.newLine();
            }

            // Close writer
            bw.close();

        } catch (IOException e) {

            // Handles file writing errors
            System.out.println("Error" + e.getMessage());

        } catch (NumberFormatException e) {

            // Handles invalid number format
            System.out.println("Invalid number in feild");

        } catch (Exception e) {

            // Handles any other unexpected errors
            System.out.println("Invalid input type");
        }
    }

    // Loads student data from file
    public Student loadFromFile() {

        // Create File object
        File f = new File("studentData.txt");

        // Check if file exists
        if (f.exists()) {

            Student loadedStudent = null;

            try {

                // Reader object for reading file
                BufferedReader br = new BufferedReader(new FileReader("studentData.txt"));

                // Read student name
                String name = br.readLine();

                // Create Student object
                loadedStudent = new Student(name);

                String line;

                // Read subject details line by line
                while ((line = br.readLine()) != null) {

                    // Split subject data using comma
                    String[] parts = line.split(",");

                    // Create Subject object from file data
                    Subject s = new Subject(
                            parts[0],
                            Integer.parseInt(parts[1].trim()),
                            Integer.parseInt(parts[2].trim()),
                            Integer.parseInt(parts[3].trim()),
                            parts[4]
                    );

                    // Restore completion status
                    s.setCompleted(Boolean.parseBoolean(parts[5]));

                    // Read weak topics line
                    String topicLine = br.readLine();

                    // Split weak topics
                    String[] topics = topicLine.split(",");

                    // Add weak topics to subject
                    for (int i = 0; i < topics.length; i++) {
                        s.addWeakTopics(topics[i]);
                    }

                    // Add subject to student
                    loadedStudent.addSubject(s);
                }

                // Close reader
                br.close();

            } catch (NumberFormatException e) {

                // Handles invalid number format
                System.out.println("Invalid number in feild");

            } catch (FileNotFoundException e) {

                // Handles missing file
                System.out.println("File Not Found!!");

            } catch (IOException e) {

                // Handles file reading errors
                System.out.println("Error:" + e.getMessage());
            }

            // Return loaded student object
            return loadedStudent;

        } else {

            // Display message if no file exists
            System.out.println("No saved data found!! Please enter new data.");
        }

        return null;
    }

    // Saves generated study schedule into a separate file
    public void saveSchedule(String schedule) {

        try {

            // Create writer object for study plan file
            BufferedWriter bw = new BufferedWriter(new FileWriter("studyPlan.txt"));

            // Write schedule
            bw.write(schedule);

            // Close writer
            bw.close();

            System.out.println("Study plan saved to studyPlan.txt");

        } catch (IOException e) {

            // Handles schedule saving errors
            System.out.println("Error saving study plan: " + e.getMessage());
        }
    }
}