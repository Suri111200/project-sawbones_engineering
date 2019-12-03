package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    List<Appointment> appointments;

    DatabaseReference mDatabase;

    Date date;

    int checkins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        appointments = new ArrayList<>();

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");

        review = findViewById(R.id.finishB);

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

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Patient patient = (Patient) ds.child("patient").getValue();
                    ServiceProvider sp = (ServiceProvider) ds.child("serviceProvider").getValue();
                    Appointment appointment = new Appointment(ds.child("id").getValue().toString(), ds.child("date").getValue().toString(), ds.child("time").getValue().toString(), patient, sp);
                    appointments.add(appointment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("CheckedIn");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkins = 0;
                //TODO: note: check-ins (probably) wont change because its in a ValueEventListener for Firebase, which is asynchronous. Works on a different thread. '
                // Delete this if I'm wrong. Just an observation.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    checkins++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        title = findViewById(R.id.title);
        String welcome = user.getName()+ ", you are Checked In!";
        title.setText(welcome);

        waitTimeT = findViewById(R.id.waitTimeT);
        waitTimeT.setText("XX:XX");
    }

//    private String calculateWaitTime ()
//    {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat simpledateformat = new SimpleDateFormat("hh:mm");
//        Date dateD = new Date(year, month, dayOfMonth-1);
//        Date waitTime = Calendar.getInstance().getTime();
//        boolean appointmentPresent;
//        for (int i = 0; i < checkins; i++)
//        {
//            appointmentPresent = false;
//            for (Appointment a : appointments)
//            {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//                Date appointmentTime = dateFormat.parse(a.getTime());
//                if ()
//            }
//        }
//
//    }
}
