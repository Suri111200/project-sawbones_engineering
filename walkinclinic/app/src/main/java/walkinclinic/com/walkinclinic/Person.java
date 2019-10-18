package walkinclinic.com.walkinclinic;

public class Person {
    private String role;
    private String name;

    public Person (String role, String name)
    {
        this.role = role;
        this.name = name;
    }

    public String getRole()
    {
        return this.role;
    }

    public String getName()
    {
        return this.name;
    }
}
