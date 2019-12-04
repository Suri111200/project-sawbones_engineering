package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckIn extends AppCompatActivity {

    TextView title;
    TextView waitTimeT;

    Patient user;
    ServiceProvider sp;
    Button review;

    //List<Appointment> appointments;

    DatabaseReference mDatabase;

    Date date;

    int waitTime;
    int checkins;
    int appointments;

    //List<Patient> checkIns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        //appointments = new ArrayList<>();
        //checkIns = new ArrayList<>();

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");

        review = findViewById(R.id.finishB);
        waitTimeT = findViewById(R.id.waitTimeT);


        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toReview = new Intent(getApplicationContext(), ReviewServiceProvider.class);
                toReview.putExtra("Person", user);
                toReview.putExtra("ServiceProvider", sp);
                startActivity(toReview);
            }
        });

        date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String currentDate = sdf.format(date);

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Appointments").child(currentDate);

//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                appointments.clear();
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Patient patient = (Patient) ds.child("patient").getValue();
//                    ServiceProvider sp = (ServiceProvider) ds.child("serviceProvider").getValue();
//                    Appointment appointment = new Appointment(ds.child("id").getValue().toString(), ds.child("date").getValue().toString(), ds.child("time").getValue().toString(), patient, sp);
//                    appointments.add(appointment);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    appointments += 1;
                    waitTime = calculateWaitTime(checkins, appointments);
                    String waitTimeStr = waitTime + " minutes";
                    waitTimeT.setText(waitTimeStr);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("CheckedIn");
//        Toast.makeText(CheckIn.this, sp.getId(), Toast.LENGTH_LONG).show();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkins = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    checkins+=1;
                    String waitTimeStr = waitTime + " minutes";
                    waitTimeT.setText(waitTimeStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        title = findViewById(R.id.title);
        String welcome = user.getName()+ ", you are Checked In!";
        title.setText(welcome);
    }

    private int calculateWaitTime (int checkins, int appointments)
    {
        waitTime = checkins * 15 + appointments * 30;
        return waitTime;
    }
}
