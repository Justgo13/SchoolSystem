/**
 * Creates a Person with a name and age
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class Person
{
    /**
     * int age of the Person
     */
    private int age;

    /**
     * String name of the Person
     */
    private String name;
    /**
     * Creates a new Person with name and age
     *
     * @param age int age of the Person
     * @param name String name of the Person
     *
     * @throws IllegalArgumentException If age is less than 0 or greater than 100
     * @throws IllegalArgumentException If name is empty
     * @throws NullPointerException If name is null
     */
    public Person(int age, String name)
    {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("Invalid age");
        }

        if (name == null) {
            throw new NullPointerException("Null passed in as name");
        } else if (name == "") {
            throw new IllegalArgumentException("Name is empty");
        }

        this.age = age;
        this.name = name;
    }

    /**
     * gets the age of the Person
     *
     * @return age of the Person
     */
    public int getAge() {
        return age;
    }

    /**
     * gets the name of the Person
     *
     * @return name of the Person
     */
    public String getName() {
        return name;
    }
}
