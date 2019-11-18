package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChooseClinic extends AppCompatActivity {

    Button joinClinicButton;
    Button registerClinicButton;

    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);

        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("Employee");


        //Employee opens list of clinics to join
        joinClinicButton = (Button) findViewById(R.id.joinClinicBtn);
        joinClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClinicList();
            }
        });

        //Employee creates a new clinic (and joins it)
        registerClinicButton = (Button) findViewById(R.id.registerClinicBtn);
        registerClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens activity to let employee create a clinic
                openRegisterClinic();
            }
        });
    }

    public void openRegisterClinic() {
        Intent toRegisterClinic = new Intent(this, RegisterClinic.class);
        startActivity(toRegisterClinic);
    }

    public void openClinicList() {
        // TODO: Opens list of Clinics and allow the employee to join a clinic
        Intent toClinicJoin = new Intent(this, ClinicJoin.class);
        toClinicJoin.putExtra("Employee", employee); //passing employee through so we can add them to the clinic in ClinicJoin
        startActivity(toClinicJoin);
    }
}