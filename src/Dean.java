/**
 * Creates a Dean
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Dean extends Staff
{
    /**
     * A String of the school name;
     */
    private String schoolManagedName;
    /**
     * Creates a new Dean
     *
     * @param age int age of the Principal
     * @param name String name of the Principal
     * @param salary int salary of the Principal
     * @param schoolManagedName String school name the Principal manages
     *
     * @throws IllegalArgumentException If schoolManagedName is empty
     * @throws NullPointerException If schoolManagedName is null
     */
    public Dean(int age, String name, int salary, int employeeID, String schoolManagedName)
    {
        super(age, name, salary, employeeID);

        if (schoolManagedName == null) {
            throw new NullPointerException("Null passed in as schoolManagedName");
        } else if (schoolManagedName == "") {
            throw new IllegalArgumentException("schoolManagedName is empty");
        }

        this.schoolManagedName = schoolManagedName;
    }

    /**
     * Gets the name of the school
     *
     * @return A String of the school name the dean is in charge of
     */
    public String getSchoolName() {
        return schoolManagedName;
    }

    /**
     * Prints String representing Dean information
     *
     * @return String of Dean information
     */
    public String toString() {
        return (getName() + " is " + getAge() + " years old and runs " + getSchoolName() + " while being paid $" + getSalary() + "/month");
    }

    /**
     * Sets Professor salary
     */
    public void setSalary(Professor professor, int salary) {
        professor.setSalary(salary);
    }
}