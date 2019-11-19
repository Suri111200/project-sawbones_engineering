package walkinclinic.com.walkinclinic;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Register extends AppCompatActivity {

    Button patient;
    Button admin;
    Button employee;
    Button serviceProvider;

    String type = "not";

    TextView errorMessage;

    Button registerButton;
    private DatabaseReference mDatabase;
    EditText emailB;
    EditText password1B;
    EditText password2B;
    EditText nameB;


    MessageDigest digest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        errorMessage = (TextView) findViewById(R.id.errorMessage);

        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        }

        emailB = findViewById(R.id.emailRegister);
        password1B = findViewById(R.id.passwordRegister);
        password2B = findViewById(R.id.password2Register);
        nameB = findViewById(R.id.nameText);

        registerButton = (Button) findViewById(R.id.registerb);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                String email =  emailB.getText().toString();
                String password1 =  password1B.getText().toString();
                byte[] hash1 = digest.digest(password1.getBytes(StandardCharsets.UTF_8));
                String password = new String(hash1);
                String password2 =  password2B.getText().toString();
                String name = nameB.getText().toString();
                if (type.equals("not"))
                {
                    errorMessage.setText("Please select what type of user profile you wish to be created.");
                }
                else
                {
                    if (!email.equals("") && !name.equals("") && !password1.equals("") && !password2.equals("")) {
                        if (password1.equals(password2))
                            registerUser(name, email, password);
                        else {
                            errorMessage.setText("Passwords don't match.");
                            password1B.setText("");
                            password2B.setText("");
                        }
                    }
                    else
                        errorMessage.setText("Make sure all fields are filled in.");
                }

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

        serviceProvider = (Button) findViewById(R.id.serviceProviderB);
        serviceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeType(v, "ServiceProvider");
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

    public void  registerUser (String name, String email, String password) {

        String id = mDatabase.push().getKey();
        Person user = new Person("h", "h", "h", "h");
        if (type.equals("ServiceProvider")) {
            Intent nextStep = new Intent(this, ServiceProviderRegister.class);
            user = new ServiceProvider(id, email, password, name, "", "", "", "", false);
            nextStep.putExtra("Person", user);
            startActivity(nextStep);
        } else {
            Intent toWelcome = new Intent(this, Welcome.class);
            if (type.equals("Admin")) {
                //            user = new Admin(id, email, password, name);
                //            mDatabase.child("Person").child("Admin").child(id).setValue(user);
                Toast.makeText(Register.this, "Admin account already created.", Toast.LENGTH_LONG).show();
            } else if (type.equals("Patient")) {
                user = new Patient(id, email, password, name);
                mDatabase.child("Person").child("Patient").child(id).setValue(user);
                Toast.makeText(Register.this, "Patient account created", Toast.LENGTH_LONG).show();
            } else {
                user = new Employee(id, email, password, name);
                mDatabase.child("Person").child("Employee").child(id).setValue(user);
                Toast.makeText(Register.this, "Employee account created", Toast.LENGTH_LONG).show();
            }
            toWelcome.putExtra("Person", user);
            startActivity(toWelcome);
        }
    }


    public void changeType (View v, String type)
    {
        this.type = type;
        v.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);

        if (type.equals("Admin")) {
            patient.getBackground().clearColorFilter();
            employee.getBackground().clearColorFilter();
            serviceProvider.getBackground().clearColorFilter();
            Toast.makeText(Register.this, "Admin account already created.", Toast.LENGTH_LONG).show();
            v.getBackground().clearColorFilter();
            this.type = "not";
        }
        else if (type.equals("Patient"))
        {
            employee.getBackground().clearColorFilter();
            admin.getBackground().clearColorFilter();
            serviceProvider.getBackground().clearColorFilter();
        }
        else if (type.equals("Employee"))
        {
            patient.getBackground().clearColorFilter();
            admin.getBackground().clearColorFilter();
            serviceProvider.getBackground().clearColorFilter();
        }
        else if(type.equals("ServiceProvider"))
        {
            patient.getBackground().clearColorFilter();
            admin.getBackground().clearColorFilter();
            employee.getBackground().clearColorFilter();
            registerButton.setText("Next");
        }

    }




}
