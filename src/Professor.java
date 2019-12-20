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
     *
     * @throws IllegalArgumentException If courseTaught is empty
     * @throws NullPointerException If courseTaught is null
     */
    public Professor(int age, String name, int salary, int employeeID, String courseTaught)
    {
        super(age, name, salary, employeeID);

        if (courseTaught == null) {
            throw new NullPointerException("Null passed in as courseTaught");
        } else if (courseTaught == "") {
            throw new IllegalArgumentException("courseTaught is empty");
        }

        this.courseTaught = courseTaught;
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
