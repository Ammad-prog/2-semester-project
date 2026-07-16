import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Class responsible for generating study schedule for a student
public class StudySchedule {

    // Student whose schedule is being generated
    private Student student;

    // Temporary subject reference used during looping
    private Subject subject;

    // Constructor to initialize student
    StudySchedule(Student student) {
        this.student = student;
    }

    // Builds schedule details for a single subject
    public String buildSchedule(Subject subject) {

        // String to store formatted schedule output
        String result = "";

        // Add subject name
        result = result + "Subject Name:" + subject.getSubjectName() + "\n";

        // Convert priority into numeric score
        int priorityScore = 0;

        if (subject.getPriority().equals("High")) {
            priorityScore = 3;
        }

        if (subject.getPriority().equals("Medium")) {
            priorityScore = 2;
        }

        if (subject.getPriority().equals("Low")) {
            priorityScore = 1;
        }

        // Count weak topics
        int countWeakTopics = subject.getWeakTopics().size();

        // Base study hours calculation
        int hoursForStudy = countWeakTopics + priorityScore;

        // Add priority info
        result = result + "Priority:" + subject.getPriority() + "\n";

        // Get today's date
        LocalDate todayDate = LocalDate.now();

        // Get exam date details from subject
        int examDate = subject.getExamDate();
        int examMonth = subject.getExamMonth();
        int examYear = subject.getExamYear();

        long daysLeft = 0;

        try {
            // Create exam date
            LocalDate dateOfExam = LocalDate.of(examYear, examMonth, examDate);

            // Calculate days left until exam
            daysLeft = dateOfExam.toEpochDay() - todayDate.toEpochDay();

        } catch (DateTimeParseException e) {

            // Handles invalid date input
            System.out.println("Invalid Date!");
        }

        // Add days left info
        result = result + "Days Left:" + daysLeft + "\n";

        // Adjust study hours based on remaining days
        if (daysLeft < 5) {
            hoursForStudy = hoursForStudy + 2;
        } else if (daysLeft >= 5 && daysLeft <= 10) {
            hoursForStudy = hoursForStudy + 1;
        } else {
            hoursForStudy = hoursForStudy;
        }

        // Limit maximum study hours
        if (hoursForStudy > 6) {
            result = result + "Hours for Study:6";
        } else {
            result = result + "Hours for Study:" + hoursForStudy + "\n";
        }

        // Add weak topics section
        result = result + "Revise Weak Topics of " + subject.getSubjectName() + ":";

        for (int j = 0; j < subject.getWeakTopics().size(); j++) {

            if (j < subject.getWeakTopics().size() - 1) {
                result = result + subject.getWeakTopics().get(j) + ",";
            } else {
                result = result + subject.getWeakTopics().get(j);
            }
        }

        // Divider line between subjects
        result = result + "\n" + "--------------------------------------------" + "\n";

        return result;
    }

    // Generates full schedule for all subjects of a student
    public String generateSchedule() {

        // String for completed subjects
        String completed = "COMPLETED SUBJECTS:\n";

        // String for pending subjects
        String pending = "PENDING SUBJECTS:\n";

        // Loop through all subjects
        for (int i = 0; i < student.getSubjects().size(); i++) {

            subject = student.getSubjects().get(i);

            // Check if subject is completed
            if (subject.isCompleted()) {

                completed += "✓ " + subject.getSubjectName() + "\n";

            } else {

                // Add pending subject schedule
                pending += buildSchedule(subject);
            }
        }

        // Return full schedule
        return pending + "\n" + completed;
    }
}