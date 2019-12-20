import java.util.ArrayList;
import java.util.HashMap;
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
    HashMap<String, String> courses;

    /**
     * String student major
     */
    private String major;

    /**
     * int Year standing
     */
    private int yearStanding;

    /**
     * double school fees
     */
    private int fees;

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
     * @param major String major of Student
     * @param yearStanding int yearStanding of Student
     *
     * @throws IllegalArgumentException If student ID below 0
     */
    public Student(int age, String name, int studentID, String major, int yearStanding)
    {
        super(age, name);
        if (studentID < 0) {
            throw new IllegalArgumentException("Student ID must be a positive integer");
        }
        this.major = major;
        this.yearStanding = yearStanding;
        fees = 0;
        this.studentID = studentID;
        courses = new HashMap<>();
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
     */
    public void addCourse(String courseCode) {
        courses.put(courseCode, "EMPTY");
        fees += 500;
    }

    /**
     * Gets an ArrayList of the course codes
     * @return ArrayList<String> of all course codes </String>
     */
    public ArrayList<String> getCourse() {
        ArrayList<String> course = new ArrayList<>();
        for (String code : courses.keySet()) {
            course.add(code);
        }
        return course;
    }

    /**
     * Removes a course for student
     */
    public void removeCourse(String courseCode) {
        courses.remove(courseCode);
        fees -= 500;
    }

    /**
     * String of student major
     * @return String of student mojor
     */
    public String getMajor(){
        return major;
    }

    /**
     * int of yearStanding
     * @return int of yearStanding
     */
    public int getYearStanding(){
        return yearStanding;
    }

    /**
     * Get student fees
     * @return int representing student fee
     */
    public int getFees(){
        return fees;
    }

    /**
     * Gets student ID
     * @return int Student ID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Updates student grade
     *
     */
    public void setGrade(String courseCode, String grade) {
        courses.put(courseCode, grade);
    }

    /**
     * Prints String representing Student information
     *
     * @return String of Student information
     */
    public String toString() {
        return (getName() + " is " + getAge() + " years old, is in " + getMajor() + " year standing: " + getYearStanding());
    }
}
