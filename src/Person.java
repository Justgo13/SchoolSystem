/**
 * Creates a Person with a name and age
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Person
{
    /**
     * String first name of the Person
     */
    private String firstName;
    
    /**
     * String last name of the Person
     */
    private String lastName;
     
    /**
     * Creates a new Person with name and age
     *
     * @param age int age of the Person
     * @param name String name of the Person
     */
    public Person(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * gets first name of the Person
     * 
     * @return String first name of Person
     */
    public String getFirstName() {
    	return firstName;
    }

    /**
     * gets last name of the Person
     * 
     * @return String last name of Person
     */
    public String getLastName() {
    	return lastName;
    }
    
    /**
     * gets the name of the Person
     *
     * @return name of the Person
     */
    public String getName() {
        return firstName + " " + lastName;
    }
}
