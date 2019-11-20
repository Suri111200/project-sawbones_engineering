package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Availabilities extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    DatabaseReference mDatabase;
    Button addAvail;
    ListView listViewAvail;

    List<Availability> availabilities;
    ServiceProvider user;


    TimePickerDialog timePickerDialog;
    
    String day = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availabilities);


        addAvail = (Button) findViewById(R.id.addAvailabilityB);
        listViewAvail = (ListView) findViewById(R.id.availabilityList);

        availabilities = new ArrayList<>();

        addAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddAvailDialog();
            }
        });

        listViewAvail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Availability avail = availabilities.get(i);
                showUpdateDeleteDialog(avail);
                return true;
            }
        });

        Intent intent = getIntent();
        user = (ServiceProvider) intent.getSerializableExtra("Person");

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Availability");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Availability Availability = new Availability(ds.child("id").getValue().toString(), ds.child("day").getValue().toString(), ds.child("startTime").getValue().toString(), ds.child("endTime").getValue().toString());
                    availabilities.add(Availability);
                }
                AvailabilityList AvailabilityAdapter = new AvailabilityList(Availabilities.this, availabilities);
                listViewAvail.setAdapter(AvailabilityAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }

    private void showAddAvailDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_avail_dialog, null);
        dialogBuilder.setView(dialogView);

        final RadioGroup rg2;
        final RadioGroup rg1;
        final RadioGroup rg3;

        rg1 = findViewById(R.id.radioGroup1);
        rg2 = findViewById(R.id.radioGroup2);
        rg3 = findViewById(R.id.radioGroup3);

        final TextView startTimeT = findViewById(R.id.sTshow);
        final TextView endTimeT = findViewById(R.id.eTshow);
        final Button startTimeB = findViewById(R.id.startTimeB);
        final Button endTimeB = findViewById(R.id.endTimeB);
        Button addAvail = findViewById(R.id.finalAddAvail);

        dialogBuilder.setTitle("Add Availability");
        final AlertDialog b = dialogBuilder.create();
        b.show();

//        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                rg2.clearCheck();
//                rg3.clearCheck();
//            }
//        });
//
//        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                rg1.clearCheck();
//                rg3.clearCheck();
//            }
//        });
//
//        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                rg1.clearCheck();
//                rg2.clearCheck();
//            }
//        });
//
        startTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Availabilities.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        startTimeT.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });

        endTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Availabilities.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        endTimeT.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });
//
//        if (rg1.getCheckedRadioButtonId() != -1) {
//            int selectedId = rg1.getCheckedRadioButtonId();
//            RadioButton radioButton = (RadioButton) findViewById(selectedId);
//            day = radioButton.getText().toString();
//        }
//        else if (rg2.getCheckedRadioButtonId() != -1)
//        {
//            int selectedId = rg2.getCheckedRadioButtonId();
//            RadioButton radioButton = (RadioButton) findViewById(selectedId);
//            day = radioButton.getText().toString();
//        }
//        else
//        {
//            int selectedId = rg3.getCheckedRadioButtonId();
//            RadioButton radioButton = (RadioButton) findViewById(selectedId);
//            day = radioButton.getText().toString();
//        }
//
//
//
//        addAvail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String startTime = startTimeT.getText().toString().trim();
//                String endTime = endTimeT.getText().toString();
//                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !day.equals("")) {
//                    addAvailability(day, startTime, endTime);
//                    b.dismiss();
//                }
//                Toast.makeText(Availabilities.this, "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void showUpdateDeleteDialog(final Availability availability) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView startTimeT = findViewById(R.id.sTshow);
        final TextView endTimeT = findViewById(R.id.eTshow);
        final Button startTimeB = findViewById(R.id.startTimeB);
        final Button endTimeB = findViewById(R.id.endTimeB);
        final Button updateB = findViewById(R.id.updateAvailB);
        final Button deleteB = findViewById(R.id.deleteAvailB);
        Button addAvail = findViewById(R.id.finalAddAvail);

        final RadioGroup rg2;
        final RadioGroup rg1;
        final RadioGroup rg3;

        rg1 = findViewById(R.id.radioGroup1);
        rg2 = findViewById(R.id.radioGroup2);
        rg3 = findViewById(R.id.radioGroup3);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rg2.clearCheck();
                rg3.clearCheck();
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rg1.clearCheck();
                rg3.clearCheck();
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rg1.clearCheck();
                rg2.clearCheck();
            }
        });

        startTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Availabilities.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        startTimeT.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });

        endTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Availabilities.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        endTimeT.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });

        if (rg1.getCheckedRadioButtonId() != -1) {
            int selectedId = rg1.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            day = radioButton.getText().toString();
        }
        else if (rg2.getCheckedRadioButtonId() != -1)
        {
            int selectedId = rg2.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            day = radioButton.getText().toString();
        }
        else
        {
            int selectedId = rg3.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            day = radioButton.getText().toString();
        }

        dialogBuilder.setTitle("Update/Delete Availability");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startTime = startTimeT.getText().toString().trim();
                String endTime = endTimeT.getText().toString();
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !day.equals("")) {
                    updateAvailability(availability.getId(), day, startTime, endTime);
                    b.dismiss();
                }
                Toast.makeText(Availabilities.this, "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
            }
        });

        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAvailability(availability.getId());
                b.dismiss();
            }
        });
    }

    private void updateAvailability(String id, String day, String startTime, String endTime) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child("Availability").child(id);

        Availability availability = new Availability(id, day, startTime, endTime);
        dR.setValue(availability);

        Toast.makeText(getApplicationContext(), "Availability updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteAvailability(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child("Availability").child(id);

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Availability deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private void addAvailability(String day, String startTime, String endTime) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child("Availability");

        if (!TextUtils.isEmpty(day) && !TextUtils.isEmpty(startTime) && !day.equals(""))
        {
            String id = dR.push().getKey();

            Availability avail = new Availability (id, day, startTime, endTime);

            dR.child(id).setValue(avail);

            Toast.makeText(this, "Availability added", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Please enter a day, start time and end time", Toast.LENGTH_LONG).show();
        }
    }
}
