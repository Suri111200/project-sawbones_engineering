package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Service implements Serializable{

    private String id;
    private String name;
    private String role;
    private String rate;

    public Service (String id, String name, String role, String rate)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.rate = rate;
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

    public String getRate() {
        return rate;
    }

    public void editName(String newName)
    {
        this.name = newName;
    }

    public void editRole(String newRole)
    {
        this.role = newRole;
    }

    public void editRate(String newRate) {this.rate = newRate;}
}
