import java.util.Dictionary;
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
    Dictionary<String,String> courses;

    /**
     * An int of the student's ID
     */
    private int studentID;
    /**
     * Creates a new student
     *
     * @param age int age of the Student
     * @param name String name of the Student
     * @param studentID An int representing student ID
     *
     * @throws IllegalArguementException If student ID below 0
     */
    public Student(int age, String name, int studentID)
    {
        super(age, name);
        if (studentID < 0) {
            throw new IllegalArgumentException("Student ID must be a positive integer");
        }

        this.studentID = studentID;
    }

    /**
     * Returns the student's ID number
     *
     * @return An int representing student ID number
     */
    public int getID() {
        return studentID;
    }

    /**
     * Adds a course for the student
     *
     * @param courseCode A String of the course code
     * @param grade a String of the grade
     */
    public void addCourse(String courseCode, String grade) {
        courses.put(courseCode, grade);
    }

    /**
     * Updates course grade
     *
     * @param courseCode A String of the course code
     * @param grade a String of the grade
     */
    public void updateGrade(String courseCode, String grade) {
        courses.put(courseCode, grade);
    }

    /**
     * Prints String representing Student information
     *
     * @return String of Student information
     */
    public String toString() {
        return (getName() + " is " + getAge() + " years old ");
    }
}
