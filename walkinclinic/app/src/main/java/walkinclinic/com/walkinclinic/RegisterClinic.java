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
    //something for spinner here, eventually

    MessageDigest digest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clinic);

        Spinner licensedClinic = (Spinner) findViewById(R.id.licensedClinic);
        ArrayAdapter<String> licensedAdapter = new ArrayAdapter<String>(RegisterClinic.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.clinicOption));

        nameB = findViewById(R.id.nameClinic);
        addressB = findViewById(R.id.addressClinic);
        phoneNumberB = findViewById(R.id.phoneClinic);
        descriptionB= findViewById(R.id.descriptionClinic);

        registerClinic = (Button) findViewById(R.id.registerb);
        registerClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameB.getText().toString();
                String address = addressB.getText().toString();
                String phoneNumber = phoneNumberB.getText().toString();
                String description = descriptionB.getText().toString();
            }
        });
    }
}
