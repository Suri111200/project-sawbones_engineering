package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    Button patient;
    Button admin;
    Button employee;

    String type = "Patient";

    Button registerButton;
    private DatabaseReference mDatabase;

    MessageDigest digest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        }

        registerButton = (Button) findViewById(R.id.registerb);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                EditText emailB = findViewById(R.id.emailRegister);
                String email =  emailB.getText().toString();
                EditText password1B = findViewById(R.id.passwordRegister);
                String password1 =  password1B.getText().toString();
                byte[] hash1 = digest.digest(password1.getBytes(StandardCharsets.UTF_8));
                String password = new String(hash1);
                //Toast.makeText(Register.this, password, Toast.LENGTH_LONG).show();
                EditText password2B = findViewById(R.id.password2Register);
                String password2 =  password2B.getText().toString();
                EditText nameB = findViewById(R.id.name);
                String name = nameB.getText().toString();
                if (!email.equals("") && !name.equals("") && !password1.equals("") && !password2.equals("") && password1.equals(password2))
                    registerUser(name, email, password);
            }
        });

        admin = (Button) findViewById(R.id.adminB);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeType(v, "Admin");
            }
        });

        patient = (Button) findViewById(R.id.patientB);
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeType(v, "Patient");
            }
        });

        employee = (Button) findViewById(R.id.employeeB);
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeType(v, "Employee");
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void showPassword (View view)
    {
        EditText password = (EditText) findViewById(R.id.passwordRegister);
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkboxRegister);

        if (checkbox.isChecked()) {
            // show password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // hide password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void  registerUser (String name, String email, String password)
    {
        Intent toWelcome = new Intent(this, Welcome.class);
        String id = mDatabase.push().getKey();
        if (type.equals("Admin")) {
            Person user = new Admin(id, email, password, name);
            mDatabase.child("Person").child("Admin").child(id).setValue(user);
            Toast.makeText(Register.this, "Admin account already created.", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Patient"))
        {
            Person user = new Patient(id, email, password, name);
            mDatabase.child("Person").child("Patient").child(id).setValue(user);
            Toast.makeText(Register.this, "Patient account created", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Employee"))
        {
            Person user = new Employee(id, email, password, name);
            mDatabase.child("Person").child("Employee").child(id).setValue(user);
            //Toast.makeText(Register.this, "Employee account created", Toast.LENGTH_LONG).show();
        }

        toWelcome.putExtra("name", name);
        toWelcome.putExtra("role", type);
        startActivity(toWelcome);
    }

    public void changeType (View v, String type)
    {
        this.type = type;
        v.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);

        if (type.equals("Admin")) {
            patient.getBackground().clearColorFilter();
            employee.getBackground().clearColorFilter();
        }
        else if (type.equals("Patient"))
        {
            employee.getBackground().clearColorFilter();
            admin.getBackground().clearColorFilter();
        }
        else if (type.equals("Employee"))
        {
            patient.getBackground().clearColorFilter();
            admin.getBackground().clearColorFilter();
        }


    }




}
