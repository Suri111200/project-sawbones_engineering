package walkinclinic.com.walkinclinic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class  ServiceProvider extends Person implements Serializable {

    String address; //mandatory
    String phoneNumber; //mandatory
    String company; //mandatory
    String description;
    boolean licensed;
    List<Service> services;
    List<Availibility> availibilities;

    public ServiceProvider (String id, String email, String password, String name, String address, String phoneNumber, String company, String description, boolean licensed)
    {
        super(id, email, password, name);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.description = description;
        this.licensed = licensed;
        services = new ArrayList<>();
        availibilities = new ArrayList<>();;
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

    public boolean getLicensed() {
        return licensed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
