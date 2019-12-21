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
     * @param age int age of the Professor
     * @param name String name of the Professor
     * @param salary int salary of the Professor
     * @param employeeID int EmployeeID
     *
     * @throws IllegalArgumentException If courseTaught is empty
     * @throws NullPointerException If courseTaught is null
     */
    public Professor(int age, String name, int salary, int employeeID)
    {
        super(age, name, salary, employeeID);

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

    /**
     * Prints String representing Professor information
     *
     * @return String of Professor information
     */
    public String toString() {
        return (getName() + " is " + getAge() + " years old and teaches " + getCourse() + " while being paid $" + getSalary() + "/month");
    }
}
