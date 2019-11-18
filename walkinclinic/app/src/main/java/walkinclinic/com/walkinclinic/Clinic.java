package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Clinic implements Serializable {

    String name;        //mandatory
    String address;     //mandatory
    String phoneNumber; //mandatory
    String description;
    boolean licensed;

    public Clinic (String id, String name, String address, String phoneNumber, String description, boolean licensed)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.licensed = licensed;
    }

    public String getName(){return name;}
    public String getAddress() {return address;}
    public String getCompany() {return phoneNumber;}
    public String getDescription() {return description;}
    public boolean checkLicense () {return licensed;}
}