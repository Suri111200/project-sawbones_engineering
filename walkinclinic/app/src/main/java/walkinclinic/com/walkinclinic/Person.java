package walkinclinic.com.walkinclinic;

public class Person {
    private String id;
    private String email;
    private String password;
    private String name;

    public Person(String id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getId() {
        return this.id;
    }

    public String getPassword()
    {
        return this.password;
    }
}
