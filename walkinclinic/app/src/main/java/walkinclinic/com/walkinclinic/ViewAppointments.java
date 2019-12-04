package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewAppointments extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView listViewAppointments;

    List<Appointment> appointments;
    Patient patient;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        listViewAppointments = (ListView) findViewById(R.id.appointmentList);

        final TextView title = findViewById(R.id.title);

        appointments = new ArrayList<>();

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Person");

        date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String currentDate = sdf.format(date);

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(patient.getId()).child("Appointments").child(currentDate);

        Toast.makeText(ViewAppointments.this, patient.getId(), Toast.LENGTH_LONG).show();


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
                AppointmentList reviewAdapter = new AppointmentList(ViewAppointments.this, appointments);
                listViewAppointments.setAdapter(reviewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        listViewAppointments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Appointment appointment = appointments.get(i);
//                Patient user = appointment.getPatient();
//                ServiceProvider sp = appointment.getServiceProvider();
//                mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("CheckedIn");
//                mDatabase.child(user.getId()).setValue(user);
//
//                Intent toCheckIn = new Intent(getApplicationContext(), CheckIn.class);
//                toCheckIn.putExtra("Person", user);
//                toCheckIn.putExtra("ServiceProvider", sp);
//                startActivity(toCheckIn);
//                return true;
//            }
//        });
    }
}
