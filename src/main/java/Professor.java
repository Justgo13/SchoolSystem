/**
 * Creates a Professor
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Professor extends Staff
{
    /**
     * A String of the courseTaught by the Professor
     */ 
    private String courseTaught;
    /**
     * Creates a new Professor
     *
     * @param firstName String first name of the Professor
     * @param lastName String last name of the professor
     * @param salary int salary of professor
     * @param employeeID String professor employee ID and username
     *
     * @throws IllegalArgumentException If courseTaught is empty
     * @throws NullPointerException If courseTaught is null
     */
    public Professor(String firstName, String lastName, int salary, String employeeID)
    {
        super(firstName, lastName, salary, employeeID);
        this.courseTaught = "";
    } 

    /**
     * Gets the course taught by the teacher
     *
     * @return A String representing the course taught by Teacher
     */
    public String getCourse() {
        return courseTaught;
    }

    public void updateGrade(Student student, String courseCode, String grade) {
        student.setGrade(courseCode, grade);
    }
}
