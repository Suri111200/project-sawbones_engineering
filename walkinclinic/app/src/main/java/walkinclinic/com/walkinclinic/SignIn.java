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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    TextView errorMessage;
    Button signinButton;
    DatabaseReference mDatabase;

    MessageDigest digest;
    ArrayList<Person> users = new ArrayList<>();

    EditText emailT;
    EditText passwordT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        }

        errorMessage = (TextView) findViewById(R.id.errorMessage);

        emailT = findViewById(R.id.emailSignIn);
        passwordT = findViewById(R.id.passwordSignIn);

        signinButton = (Button) findViewById(R.id.signinb);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailT.getText().toString();
                String prePassword = passwordT.getText().toString();
                byte[] hash1 = digest.digest(prePassword.getBytes(StandardCharsets.UTF_8));
                String password = new String(hash1);
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(prePassword)) {
                    verify(email, password);
                }
                else
                    errorMessage.setText("Make sure all fields are filled in.");
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person");

        mDatabase.addValueEventListener(
                new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        //Toast.makeText(SignIn.this, "Snapshot", Toast.LENGTH_LONG).show();
                        users.clear();
                        for (DataSnapshot ds : dataSnapshot.child("Patient").getChildren()) {
                           String id = ds.child("id").getValue().toString();
                           String email = ds.child("email").getValue().toString();
                           String password = ds.child("password").getValue().toString();
                           String name = ds.child("name").getValue().toString();
                           users.add(new Patient(id, email, password, name));
                        }
                        for (DataSnapshot ds : dataSnapshot.child("Employee").getChildren()) {
                            String id = ds.child("id").getValue().toString();
                            String email = ds.child("email").getValue().toString();
                            String password = ds.child("password").getValue().toString();
                            String name = ds.child("name").getValue().toString();
                            users.add(new Employee(id, email, password, name));
                        }
                        for (DataSnapshot ds : dataSnapshot.child("Admin").getChildren()) {
                            String id = ds.child("id").getValue().toString();
                            String email = ds.child("email").getValue().toString();
                            String password = ds.child("password").getValue().toString();
                            String name = ds.child("name").getValue().toString();
                            users.add(new Admin(id, email, password, name));
                        }
                        //Toast.makeText(SignIn.this, users.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
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

    public void verify(String email, String password)
    {
        String email2;
        String password2;
        Person user;
        boolean signIn = false;
        boolean emailExists = false;
        for (int i = 0; i < users.size(); i++)
        {
            user = users.get(i);
            email2 =  user.getEmail();
            password2 = user.getPassword();
            if (email.equals(email2) && password.equals(password2))
            {
                emailT.setText("");
                passwordT.setText("");
                errorMessage.setText("");
                signIn = true;
                signIn(user);
            }
            else if (email.equals(email2)) {
                emailExists = true;
            }

        }

        if (emailExists && !signIn)
            errorMessage.setText("The password you've entered is incorrect.");
        else if (!signIn)
            errorMessage.setText("No user with the entered email exists.");
        passwordT.setText("");
    }

    public void signIn (Person user)
    {
        Intent toWelcome;
        if (user.getClass().getSimpleName().equals("Admin"))
            toWelcome = new Intent(this, WelcomeAdmin.class);
        else
            toWelcome = new Intent(this, Welcome.class);
        toWelcome.putExtra("Person", user);
        startActivity(toWelcome);
    }
}
