package walkinclinic.com.walkinclinic;

import android.provider.ContactsContract;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  ServiceProvider extends Person implements Serializable {

    String address; //mandatory
    String phoneNumber; //mandatory
    String company; //mandatory
    String description;
    boolean licensed;
    ArrayList<Service> services;
    ArrayList<String> availabilities;

    public ServiceProvider (String id, String email, String password, String name, String address, String phoneNumber, String company, String description, boolean licensed)
    {
        super(id, email, password, name);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.description = description;
        this.licensed = licensed;
        services = new ArrayList<>();
        availabilities = new ArrayList<>();
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

    public ArrayList<Service> getServices(){ return services; }

    public ArrayList<String> getAvailabilities(){ return availabilities; }

    public void addService(Service service){
        services.add(service);
    }

    public void addAvailability(String availability){
        availabilities.add(availability);
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

    public void setServices(ArrayList<Service> services){
        this.services = services;
    }

    public void setAvailabilities(ArrayList<String> availabilities){
        this.availabilities = availabilities;
    }
}
