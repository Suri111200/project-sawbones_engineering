package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Review implements Serializable {
    String id;
    private String reviewValue;
    private String reviewContent;

    public Review(String id, String reviewValue, String reviewContent) {
        this.id = id;
        this.reviewValue = reviewValue;
        this.reviewContent = reviewContent;
    }

    public String getId(){ return this.id; }

    public String getReviewValue() { return this.reviewValue; }

    public String getReviewContent() { return this.reviewContent; }
}
