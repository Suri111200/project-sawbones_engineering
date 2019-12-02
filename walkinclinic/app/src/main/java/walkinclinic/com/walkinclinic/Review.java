package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Review implements Serializable {
    String id;
    Patient user;
    private Float reviewValue;
    private String reviewContent;

    public Review(String id, Patient user, Float reviewValue, String reviewContent) {
        this.id = id;
        this.user = user; //ensures only one review per user per clinic
        this.reviewValue = reviewValue;
        this.reviewContent = reviewContent;
    }

    public String getId(){ return this.id; }

    public Patient getUser(){ return this.user; }

    public Float getReviewValue() { return this.reviewValue; }

    public String getReviewContent() { return this.reviewContent; }
}
