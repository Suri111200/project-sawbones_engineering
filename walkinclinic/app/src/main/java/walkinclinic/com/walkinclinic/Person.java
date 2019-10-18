package walkinclinic.com.walkinclinic;

public class Person {
    private String email;
    private String password;
    private String name;

    public Person (String email, String password, String name)
    {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getEmail()
    {
        return this.email;
    }
}
