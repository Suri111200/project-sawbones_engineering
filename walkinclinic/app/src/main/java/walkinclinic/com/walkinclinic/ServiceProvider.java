package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class ServiceProvider extends Person implements Serializable {

    String address; //mandatory
    String phoneNumber; //mandatory
    String company; //mandatory
    String description;
    boolean licensed;

    public ServiceProvider (String id, String email, String password, String name, String address, String phoneNumber, String company, String description, boolean licensed)
    {
        super(id, email, password, name);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.description = description;
        this.licensed = licensed;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public boolean checkLicense () {
        return licensed;
    }
}
