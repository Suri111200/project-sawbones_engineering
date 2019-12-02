package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewServiceProvider extends AppCompatActivity {

    Button submit;
    EditText ratingText;
    EditText reviewText;
    ServiceProvider sp;
    Patient user;
    String id;
    DatabaseReference dR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_service_provider);

        Intent intent = getIntent();
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");
        user = (Patient) intent.getSerializableExtra("Person");

        submit = findViewById(R.id.submitB);
        ratingText = findViewById(R.id.ratingT);
        reviewText = findViewById(R.id.reviewT);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview(ratingText.getText().toString(), reviewText.getText().toString());
            }
        });
    }

    private void submitReview (String rating, String review)
    {
        if (!TextUtils.isEmpty(rating) && !TextUtils.isEmpty(review) && Integer.parseInt(rating) <= 10 && Integer.parseInt(rating) > 0 )
        {

            dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Review");
            id = dR.push().getKey();

            Review reviewObject = new Review(id, rating, review);

            dR.child(id).setValue(reviewObject);

            Toast.makeText(this, "Review added", Toast.LENGTH_LONG).show();

            Intent toProfileClass = new Intent(getApplicationContext(), ProfileBasic.class);
            toProfileClass.putExtra("Person", user);
            startActivity(toProfileClass);
        }
        else
        {
            Toast.makeText(this, "Please enter a rating and a review", Toast.LENGTH_LONG).show();
        }
    }


}
