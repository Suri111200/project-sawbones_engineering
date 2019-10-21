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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SignIn extends AppCompatActivity {

    Button patient;
    Button admin;
    Button employee;

    String name;
    String type;

    Button signinButton;
    DatabaseReference mDatabase;

    MessageDigest digest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signinButton = (Button) findViewById(R.id.signinb);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailT = findViewById(R.id.emailSignIn);
                String email =  emailT.getText().toString();
                EditText passwordT = findViewById(R.id.passwordSignIn);
                String prePassword = passwordT.getText().toString();
                byte[] hash1 = digest.digest(prePassword.getBytes(StandardCharsets.UTF_8));
                String password = new String(hash1);
                signIn(email, password);
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

    }

    public void showPassword (View view)
    {
        EditText password = (EditText) findViewById(R.id.passwordSignIn);
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkboxSignIn);

        if (checkbox.isChecked()) {
            // show password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // hide password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void signIn (String email, String password)
    {
        Intent toWelcome = new Intent(this, Welcome.class);

        if (type.equals("Admin"))
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        name = datas.child("name").getValue().toString();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
        else if (type.equals("Patient"))
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patient");

            reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        name = datas.child("name").getValue().toString();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        else if (type.equals("Employee"))
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Employee");

            reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        name = datas.child("name").getValue().toString();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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
