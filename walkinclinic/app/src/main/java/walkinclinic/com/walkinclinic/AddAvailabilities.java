package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAvailabilities extends AppCompatActivity {
    RadioGroup rg2;
    RadioGroup rg1;
    RadioGroup rg3;

    TimePickerDialog timePickerDialog;

    ServiceProvider user;

    String day = "";
    String id;

    boolean rg1Clicked = false;
    boolean rg2Clicked = false;
    boolean rg3Clicked = false;

    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    RadioButton rb5;
    RadioButton rb6;
    RadioButton rb7;

    int hodstart = 0;
    int minstart = 0;
    int hodend = 0;
    int minend = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_availabilities);

        rg1 = findViewById(R.id.radioGroup1);
        rg2 = findViewById(R.id.radioGroup2);
        rg3 = findViewById(R.id.radioGroup3);

        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        rb5 = findViewById(R.id.radioButton5);
        rb6 = findViewById(R.id.radioButton6);
        rb7 = findViewById(R.id.radioButton7);


        final TextView startTimeT = findViewById(R.id.sTshow);
        final TextView endTimeT = findViewById(R.id.eTshow);
        Button startTimeB = findViewById(R.id.startTimeB);
        Button endTimeB = findViewById(R.id.endTimeB);
        Button addBtn = findViewById(R.id.addB);
        Button updateBtn = findViewById(R.id.updateAvailB);

        Intent intent = getIntent();
        user = (ServiceProvider) intent.getSerializableExtra("Person");
        id = intent.getStringExtra("id");
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAvailability(id, startTimeT.getText().toString(), endTimeT.getText().toString());
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.equals("")) {
                    updateAvailability(id, startTimeT.getText().toString(), endTimeT.getText().toString());
                } else
                    Toast.makeText(getApplicationContext(), "No availability specified", Toast.LENGTH_LONG).show();
            }
        });


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rg2Clicked) {
                    rg2.clearCheck();
                    rg2Clicked = false;
                }
                if (rg3Clicked) {
                    rg3.clearCheck();
                    rg3Clicked = false;
                }
                rg1Clicked = true;
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rg1Clicked) {
                    rg1.clearCheck();
                    rg1Clicked = false;
                }
                if (rg3Clicked) {
                    rg3.clearCheck();
                    rg3Clicked = false;
                }
                rg2Clicked = true;
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rg1Clicked) {
                    rg1.clearCheck();
                    rg1Clicked = false;
                }
                if (rg2Clicked) {
                    rg2.clearCheck();
                    rg2Clicked = false;
                }
                rg3Clicked = true;
            }
        });

        startTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(AddAvailabilities.this, new TimePickerDialog.OnTimeSetListener() {

                    String amPm;

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hodstart = hourOfDay;
                        minstart = minutes;
                        String set = String.format("%02d:%02d", hourOfDay, minutes) + amPm;
                        startTimeT.setText(set);
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });

        endTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(AddAvailabilities.this, new TimePickerDialog.OnTimeSetListener() {

                    String amPm;

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hodend = hourOfDay;
                        minend = minutes;
                        String set = String.format("%02d:%02d", hourOfDay, minutes) + amPm;
                        endTimeT.setText(set);
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });


    }


    private void updateAvailability(String id, String startTime, String endTime) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Availability").child(day);
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();


        if (rb1.isChecked())
            day = rb1.getText().toString();
        else if (rb2.isChecked())
            day = rb2.getText().toString();
        else if (rb3.isChecked())
            day = rb3.getText().toString();
        else if (rb4.isChecked())
            day = rb4.getText().toString();
        else if (rb5.isChecked())
            day = rb5.getText().toString();
        else if (rb6.isChecked())
            day = rb6.getText().toString();
        else
            day = rb7.getText().toString();
        if (hodstart <= hodend && minstart < minend) {
            if (!TextUtils.isEmpty(day) && !TextUtils.isEmpty(startTime) && !day.equals("") && !startTime.equals(endTime)) {
                Availability availability = new Availability(day, startTime, endTime);
                dR.setValue(availability);
                Toast.makeText(getApplicationContext(), "Availability updated", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), "Make sure to pick all fields, including day, start time and end time", Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(getApplicationContext(), "End Time has to be after Start Time", Toast.LENGTH_LONG).show();
    }

    private void addAvailability(String id, String startTime, String endTime){

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Availability");

        if (rb1.isChecked())
            day = rb1.getText().toString();
        else if (rb2.isChecked())
            day = rb2.getText().toString();
        else if (rb3.isChecked())
            day = rb3.getText().toString();
        else if (rb4.isChecked())
            day = rb4.getText().toString();
        else if (rb5.isChecked())
            day = rb5.getText().toString();
        else if (rb6.isChecked())
            day = rb6.getText().toString();
        else
            day = rb7.getText().toString();

        if (id.equals("")) {
            if (!TextUtils.isEmpty(day) && !TextUtils.isEmpty(startTime) && !day.equals("") && !startTime.equals(endTime)) {

                Availability avail = new Availability(day, startTime, endTime);

                dR.child(day).setValue(avail);

                Toast.makeText(this, "Availability added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please enter a day, start time and end time", Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(this, "Click the update button to update availability.", Toast.LENGTH_LONG).show();
    }
}
