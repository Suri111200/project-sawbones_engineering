package walkinclinic.com.walkinclinic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ClinicJoin extends AppCompatActivity {
    //TODO: Displays list of clinics and allows the employee to join one
    Employee employee;

    DatabaseReference mDatabase;
    ListView listViewClinics;

    List<Clinic> clinics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO pls verify this, borrowed code from ServliceList.
        // It opens the right activity but should display any created
        // Clinics in a list, employee can join ONE
        // But actually displays nothing lol. RIP.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);

        //this is for later, ideally when they join add the employee to the clinic's 'employee list' or some shit
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("Employee");


        // Toast.makeText(ServicesBasic.this, "Services Basic", Toast.LENGTH_LONG).show();
        listViewClinics = (ListView) findViewById(R.id.clinicsList);

        clinics = new ArrayList<>();
        //not sure what this does, copying it from ServicesBasic
        mDatabase = FirebaseDatabase.getInstance().getReference("Clinic");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("clinicTest", "Something happens");

                clinics.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.i("clinicTest", "Enters for loop");
                    Clinic clinic = new Clinic(ds.child("id").getValue().toString(),ds.child("name").getValue().toString(), ds.child("address").getValue().toString(), ds.child("phoneNumber").getValue().toString(), ds.child("description").getValue().toString(), (Boolean)ds.child("licensed").getValue());
                    clinics.add(clinic);

                    //testing
                    if(clinic.getName().length()<1)
                        Log.i("clinicTest", "Clinic does not exist or has no name");
                    else
                        Log.i("clinicTest", clinic.getName());
                }
                Log.i("clinicTest", "Exits for loop");//this doesnt print
                ClinicList clinicsAdapter = new ClinicList(ClinicJoin.this, clinics);
                listViewClinics.setAdapter(clinicsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
