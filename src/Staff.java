/**
 * Creates a staff members with employeeID and salary
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Staff extends Person
{
    /**
     * int of the staff salary
     */
    private int salary;

    /**
     * int for the staff ID number
     */
    private int employeeID;
    /**
     * Creates a staff member
     *
     * @param age int age of the staff member
     * @param name String name of the staff member
     * @param salary int salary of the staff member
     *
     * @throws IllegalArgumentException If salary is below 0
     * @throws IllegalArgumentException If employee ID is below 0
     */
    public Staff(int age, String name, int salary, int employeeID)
    {
        super(age, name);
        if (salary < 0) {
            throw new IllegalArgumentException("Salary can not be negative");
        }
        if (employeeID < 0) {
            throw new IllegalArgumentException("Employee ID can not be negative");
        }
        this.salary = salary;
        this.employeeID = employeeID;
    }

    /**
     * gets the salary of the Staff member
     *
     * @return salary of the Staff member
     */
    public int getSalary() {
        return salary;
    }

    /**
     * gets the salary of the Staff member
     *
     * @return salary of the Staff member
     */
    public int getID() {
        return employeeID;
    }

    /**
     * sets the salary of the Staff member
     *
     * @param salary An int representing the staff member salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }
}
