/**
 * Creates a Dean
 *
 * @author Jason Gao
 * @version v1.1 Aug 16, 2020
 */
public class Dean extends Staff
{
    /**
     * Creates a new Dean
     *
     * @param firstName String first name of the dean
     * @param lastName String last name of the dean
     * @param salary int salary of the dean
     * @param employeeID String dean employee id and username
     */
    public Dean(String firstName, String lastName, int salary, String employeeID)
    { 
        super(firstName, lastName, salary, employeeID);
    } 

    /**
     * Sets Professor salary
     */
    public void setSalary(Professor professor, int salary) {
        professor.setSalary(salary);
    }
    
    /**
     * Approves grades for students after professor grades them
     */
    public void approveGrade() {
    	
    }
}