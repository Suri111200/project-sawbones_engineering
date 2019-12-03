package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.format.DateFormat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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

public class BookAppointment extends AppCompatActivity {

    CalendarView calendar;
    Button selectDateB;
    TextView dateSelected;

    Patient user;
    ServiceProvider sp;

    String date;
    String selDOW;

    int count;
    ArrayList<String> availDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        calendar = findViewById(R.id.calendarView);
        selectDateB = findViewById(R.id.selectDateB);
        dateSelected = findViewById(R.id.selectedDate);

        count = 0;

        availDays = new ArrayList<>();

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String currentDate = sdf.format(new Date(calendar.getDate()));

        date = currentDate;
        dateSelected.setText(date);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (dayOfMonth < 10) {
                    date = (month + 1) + "-" + "0" + dayOfMonth + "-" + year;
                }
                else {
                    date = (month + 1) + "-" + dayOfMonth + "-" + year;
                }

                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date dateD = new Date(year, month, dayOfMonth-1);
                selDOW = simpledateformat.format(dateD);
                //Toast.makeText(BookAppointment.this, selDOW, Toast.LENGTH_LONG).show();

                dateSelected.setText(date);
            }
        });

        DatabaseReference availabilitiesAtClinic = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Availability");
        availabilitiesAtClinic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availDays.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    availDays.add(ds.child("day").getValue().toString());
                }

                //Toast.makeText(BookAppointment.this, availDays.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference appointmentsAtClinic = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Appointments").child(date);
        appointmentsAtClinic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        selectDateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean notAvailable = true;
                for (int i = 0; i < availDays.size(); i++)
                {
                    Toast.makeText(BookAppointment.this, availDays.get(i), Toast.LENGTH_LONG).show();
                    if (availDays.get(i).equals(selDOW))
                        notAvailable = false;
                }

                if (notAvailable)
                    Toast.makeText(BookAppointment.this, "The clinic is closed on this day.", Toast.LENGTH_LONG).show();
                else if (count == 16)
                    Toast.makeText(BookAppointment.this, "Day is Full. Please select another day.", Toast.LENGTH_LONG).show();
                else {
                    Intent toBookAppointment = new Intent(getApplicationContext(), AppointmentTimeSelect.class);
                    toBookAppointment.putExtra("Person", user);
                    toBookAppointment.putExtra("ServiceProvider", sp);
                    toBookAppointment.putExtra("Date", date);
                    startActivity(toBookAppointment);
                }
            }
        });
    }
}
