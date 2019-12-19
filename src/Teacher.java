/**
 * Creates a Teacher
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Teacher extends Staff implements IncreaseSalary
{
    /**
     * A String of the courseTaught by the Teacher
     */
    private String courseTaught;
    /**
     * Creates a new Teacher
     *
     * @param age int age of the Teacher
     * @param name String name of the Teacher
     * @param salary int salary of the Teacher
     *
     * @throws IllegalArgumentException If courseTaught is empty
     * @throws NullPointerException If courseTaught is null
     */
    public Teacher(int age, String name, int salary, int employeeID, String courseTaught)
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
     * Increase the salary of the Teacher
     */
    public void increaseSalary(int salaryIncrease) {
        setSalary(getSalary() + salaryIncrease);
    }

    /**
     * Gets the course taught by the teacher
     *
     * @return A String representing the course taught by Teacher
     */
    public String getCourse() {
        return courseTaught;
    }

    /**
     * Prints String representing Teacher information
     *
     * @return String of Teacher information
     */
    public String toString() {
        return (getName() + " is " + getAge() + " years old and teaches " + getCourse() + " while being paid $" + getSalary() + "/month");
    }
}
