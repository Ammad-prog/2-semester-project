import java.util.ArrayList;

// Subject class to store subject-related information
public class Subject {

    // Subject details
    private String subjectName;
    private int examDate;
    private int examMonth;
    private int examYear;
    private String priority;

    // Stores weak topics of the subject
    private ArrayList<String> weaktopics;

    // Tracks whether the subject preparation is completed or not
    private boolean completed;

    // Constructor to initialize subject information
    Subject(String subjectName, int examDate,int examMonth,int examYear, String priority) {
        this.subjectName = subjectName;
        this.examDate = examDate;
        this.examMonth = examMonth;
        this.examYear = examYear;
        this.priority = priority;

        // Create an empty ArrayList for weak topics
        weaktopics = new ArrayList<>();

        // Initially subject is not completed
        this.completed=false;
    }

    // Adds a weak topic to the ArrayList
    public void addWeakTopics(String topic) {
        weaktopics.add(topic);
    }

    // Returns subject name
    public String getSubjectName(){
        return subjectName;
    }

    // Returns exam date
    public int getExamDate(){
        return examDate;
    }

    // Returns exam month
    public int getExamMonth(){
        return examMonth;
    }

    // Returns exam year
    public int getExamYear(){
        return examYear;
    }

    // Returns priority level
    public String getPriority(){
        return priority;
    }

    // Returns completion status
    public Boolean isCompleted(){
        return completed;
    }

    // Returns list of weak topics
    public ArrayList<String> getWeakTopics(){
        return weaktopics;
    }

    // Updates completion status
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}