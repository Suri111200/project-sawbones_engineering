package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewList extends ArrayAdapter<Review> {

    private Activity context;
    List<Review> reviews;
    String filter = "";

    public ReviewList(Activity context, List<Review> reviews) {
        super(context, R.layout.layout_review_list, reviews);
        this.context = context;
        this.reviews = reviews;
    }

    public void update(List<Review> results, String query){
        this.filter = query;
        reviews = new ArrayList<>();
        reviews.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_review_list, null, true);

        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.RatingTV);
        TextView textViewReview = (TextView) listViewItem.findViewById(R.id.ReviewTV);


        Review review = reviews.get(position);
        textViewRating.setText(review.getReviewValue());
        textViewReview.setText(String.valueOf(review.getReviewContent()));
        return listViewItem;
    }

    @Override
    public int getCount(){
        return reviews.size();
    }
}
