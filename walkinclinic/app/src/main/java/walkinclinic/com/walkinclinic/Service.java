package walkinclinic.com.walkinclinic;

public class Service {

    private String id;
    private String name;
    private String role;

    public Service (String id, String name, String role)
    {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getName()
    {
        return name;
    }

    public String getRole()
    {
        return role;
    }

    public String getId() {
        return id;
    }

    public void editName(String newName)
    {
        this.name = newName;
    }

    public void editRole(String newRole)
    {
        this.role = newRole;
    }
}
