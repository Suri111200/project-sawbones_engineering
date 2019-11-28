package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewReviews extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView listViewReview;
    TextView overallRating;

    List<Review> reviews;
    ServiceProvider sp;
    float sumRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        listViewReview = (ListView) findViewById(R.id.reviewList);
//        overallRating = findViewById(R.id.overallRating);

        reviews = new ArrayList<>();

        Intent intent = getIntent();
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Review");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviews.clear();
                sumRating = 0;
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
//                    overallRating.setText(ds.child("reviewValue").getValue().toString());
//                    sumRating += Integer.parseInt(overallRating.getText().toString());
//                    overallRating.setText("");
                    Review review = new Review(ds.child("id").getValue().toString(), ds.child("reviewValue").getValue().toString(), ds.child("reviewContent").getValue().toString());
                    reviews.add(review);
                }
                ReviewList reviewAdapter = new ReviewList(ViewReviews.this, reviews);
                listViewReview.setAdapter(reviewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        String avgRating = "";
//        if (reviews.size() == 0)
//            avgRating = "No reviews yet";
//        else
//            String.valueOf(sumRating / reviews.size());
//
        overallRating.setText("Reviews");
    }
}
