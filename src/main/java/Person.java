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
     * @param firstName first name of the Person
     * @param lastName last name of the Person
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
    	return firstName.substring(0, 1).toUpperCase() + firstName.substring(1, firstName.length());
    }

    /**
     * gets last name of the Person
     * 
     * @return String last name of Person
     */
    public String getLastName() {
    	return lastName.substring(0, 1).toUpperCase() + lastName.substring(1, lastName.length());
    }
    
    /**
     * gets the name of the Person
     *
     * @return name of the Person
     */
    public String getName() {
        return firstName.substring(0, 1).toUpperCase() + firstName.substring(1, firstName.length()) + " " + lastName.substring(0, 1).toUpperCase() + lastName.substring(1, lastName.length());
    }
}
