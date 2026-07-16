import java.util.ArrayList;

// Student class to store student information
public class Student {

    // Stores the student's name
    private String studentName;

    // Stores all subjects of the student
    private ArrayList<Subject> subjects;

    // Constructor to initialize student name and subject list
    Student(String studentName) {

        // Assign the provided name to the student
        this.studentName = studentName;

        // Create an empty ArrayList to store subjects
        subjects = new ArrayList<>();
    }

    // Returns the student's name
    public String getName() {
        return studentName;
    }

    // Adds a Subject object to the subject list
    public void addSubject(Subject s) {
        subjects.add(s);
    }

    // Returns the complete list of subjects
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
}