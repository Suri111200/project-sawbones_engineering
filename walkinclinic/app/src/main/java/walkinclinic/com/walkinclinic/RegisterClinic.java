package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RegisterClinic extends AppCompatActivity {

    Button registerClinic;

    TextView errorMessage;

    EditText nameB;
    EditText addressB;
    EditText phoneNumberB;
    EditText descriptionB;
    Spinner licensedB;
    Boolean checkLicense;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clinic);

        errorMessage = (TextView) findViewById(R.id.errorMessage);

        final Spinner licensedClinic = (Spinner) findViewById(R.id.licensedClinic);
        /*ArrayAdapter<String> licensedAdapter = new ArrayAdapter<String>(RegisterClinic.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.clinicOption));*/

        nameB = findViewById(R.id.nameClinic);
        addressB = findViewById(R.id.addressClinic);
        phoneNumberB = findViewById(R.id.phoneClinic);
        descriptionB = findViewById(R.id.descriptionClinic);
        //licensedB = findViewById(R.id.licensedClinic);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        registerClinic = (Button) findViewById(R.id.registerClinicButton);
        registerClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Variables for clinic to be created
                String name = nameB.getText().toString();
                String address = addressB.getText().toString();
                String phoneNumber = phoneNumberB.getText().toString();
                String description = descriptionB.getText().toString();
                String licensed = licensedClinic.getSelectedItem().toString();
                if (licensed == "This is a licensed clinic"){
                    checkLicense = true;
                }
                else
                    checkLicense = false;

                //Verifies mandatory fields are entered, if so registers the clinic
                if (!name.equals("") && !address.equals("") && !phoneNumber.equals("")) {
                    registerClinic(name, address, phoneNumber, description, checkLicense);
                }
                else
                    if(name.equals(""))
                        errorMessage.setText("Please enter a name for the clinic");
                    else if(address.equals(""))
                        errorMessage.setText("Please enter an address for the clinic");
                    else
                        errorMessage.setText("Please enter a phone number for the clinic");
            }
        });
    }

    public void registerClinic (String name, String address, String phoneNumber, String description, boolean licensed){
        //TODO: Verify my code pls. Not actually sure what I'm doing here
        // We want to add clinics to some sort of list/database so users can view a list of Clinics later
        // Or an employee can join an existing clinic when registering
        String id = mDatabase.push().getKey();
        Clinic clinic = new Clinic(id, name, address, phoneNumber, description, checkLicense);
        mDatabase.child(id).setValue(clinic);
        Log.i("clinicTest", clinic.getName() + " was created");
        Toast.makeText(RegisterClinic.this, name + " was registered successfully", Toast.LENGTH_LONG).show();

        /*Intent toWelcome = new Intent(this, Welcome.class);
        toWelcome.putExtra("Clinic", clinic;
        startActivity(toWelcome);*/

        //TODO: Should bring you to page to manage Clinic profile that you just made, ideally
        // Employee should be able to view a list of services and add or delete to their profile
    }
}
