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

import java.util.ArrayList;

public class AppointmentTimeSelect extends AppCompatActivity {

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b10;
    Button b11;
    Button b12;
    Button b13;
    Button b14;
    Button b15;
    Button b16;

    Button setAppointment;
    TextView timeT;

    String time;
    String date;
    Patient user;
    ServiceProvider sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time_select);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b10 = findViewById(R.id.b10);
        b11 = findViewById(R.id.b11);
        b12 = findViewById(R.id.b12);
        b13 = findViewById(R.id.b13);
        b14 = findViewById(R.id.b14);
        b15 = findViewById(R.id.b15);
        b16 = findViewById(R.id.b16);

        timeT = findViewById(R.id.chosenTime);
        setAppointment = findViewById(R.id.setB);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b1.getText().toString();
                time = b1.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b2.getText().toString();
                time = b2.getText().toString();
                timeT.setText(timeShow);time = "Time Selected: " +b2.getText().toString();
                timeT.setText(time);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b3.getText().toString();
                time = b3.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b4.getText().toString();
                time = b4.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b5.getText().toString();
                time = b5.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b6.getText().toString();
                time = b6.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b7.getText().toString();
                time = b7.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b8.getText().toString();
                time = b8.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b9.getText().toString();
                time = b9.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b10.getText().toString();
                time = b10.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b11.getText().toString();
                time = b11.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b12.getText().toString();
                time = b12.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b13.getText().toString();
                time = b13.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b14.getText().toString();
                time = b14.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b15.getText().toString();
                time = b15.getText().toString();
                timeT.setText(timeShow);
            }
        });

        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeShow = "Time Selected: " + b16.getText().toString();
                time = b16.getText().toString();
                timeT.setText(timeShow);
            }
        });

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");
        date = intent.getStringExtra("Date");

        DatabaseReference appointmentsAtClinic = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Appointments").child(date);
        appointmentsAtClinic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("time").getValue().toString().equals("9:00"))
                        b1.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("9:30"))
                        b2.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("10:00"))
                        b3.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("10:30"))
                        b4.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("11:00"))
                        b5.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("11:30"))
                        b6.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("12:00"))
                        b7.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("12:30"))
                        b8.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("13:00"))
                        b9.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("13:30"))
                        b10.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("14:00"))
                        b11.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("14:30"))
                        b12.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("15:00"))
                        b13.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("15:30"))
                        b14.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("16:00"))
                        b15.setEnabled(false);
                    else if (ds.child("time").getValue().toString().equals("16:30"))
                        b16.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment(date, time, user, sp);
            }
        });
    }

    private void bookAppointment (String date, String time, Patient patient, ServiceProvider sp)
    {
        DatabaseReference patientDR = FirebaseDatabase.getInstance().getReference("Person").child("Patient").child(patient.getId()).child("Appointments");
        DatabaseReference spDR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Appointments");

        String id = patientDR.push().getKey();

        Appointment appointment = new Appointment(id, date, time, patient, sp);
        patientDR.child(date).child(id).setValue(appointment);
        spDR.child(date).child(id).setValue(appointment);

        Toast.makeText(AppointmentTimeSelect.this, "Appointment booked", Toast.LENGTH_LONG).show();

        Intent toProfile = new Intent(getApplicationContext(), ProfileBasic.class);
        toProfile.putExtra("Person", user);
        startActivity(toProfile);
    }
}
