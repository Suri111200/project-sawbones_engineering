package walkinclinic.com.walkinclinic;

public class Appointment {

    String id;
    String date;
    String time;
    Patient patient;
    ServiceProvider sp;

    public Appointment (String id, String date, String time, Patient patient, ServiceProvider sp)
    {
        this.id = id;
        this.date = date;
        this.time = time;
        this.patient = patient;
        this.sp = sp;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public ServiceProvider getServiceProvider() {
        return sp;
    }


}
