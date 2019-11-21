package walkinclinic.com.walkinclinic;

public class Availability {

    private String id;
    private String day;
    private String startTime;
    private String endTime;

    public Availability(String id, String day, String startTime, String endTime)
    {
        this.id = id;
        this.day= day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getId() {
        return id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}