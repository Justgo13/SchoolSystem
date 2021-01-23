import java.util.HashMap;
import java.util.Map;

/**
 * Creates a Student
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Student extends Person
{
    /**
     * A Dictionary storing all of the Student courses code and grade
     */
    private HashMap<String, String> courses;

    /**
     * int school fees
     */
    private int fees;

    /**
     * An int of the student's ID
     */
    private String studentID;
    /**
     * Creates a new student
     *
     * @param firstName String first name of the student
     * @param lastName String last name of the student
     * @param studentID An int representing student ID
     */
    public Student(String firstName, String lastName, String studentID)
    {
        super(firstName, lastName);
        fees = 0;
        this.studentID = studentID;
        courses = new HashMap<>();
    }

    /**
     * Adds a course for the student
     *
     * @param courseCode A String of the course code
     */
    public void addCourse(String courseCode) {
        courses.put(courseCode, "NO GRADE");
        setFees(getFees()+500);
    }

	/**
     * Removes a course for student
     */
    public void removeCourse(String courseCode) {
        courses.remove(courseCode);
        setFees(getFees()-500);
    }

    /**
     * Gets an HashMap of the course codes
     * @return HashMap<String, String> Return the HashMap of all courses</String,>
     */
    public HashMap<String,String> getCourse() {
        return courses;
    }

    /**
     * Get student fees
     * @return int representing student fee
     */
    public int getFees(){
        return fees;
    }
    
    /**
     * Adds fees for the student
     * @param fees
     */
    private void setFees(int fees) {
		this.fees = fees;
		
	}

    /**
     * get all grades for the student
     * @return
     */
    public String getGrade() {
        String grade = "";
        for (Map.Entry<String, String> entry : courses.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            grade += k + ": " + v.toString() + "\n";
        }
        return grade;
    }

    /**
     * Updates student grade
     *
     */ 
    public void setGrade(String courseCode, String grade) {
        courses.put(courseCode, grade);
    }

    /**
     * get student ID
     */
    public String getStudentID() {
        return studentID;
    }
}
