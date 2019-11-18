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

public class ChooseClinic extends AppCompatActivity {

    Button joinClinicButton;
    Button registerClinicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);

        joinClinicButton = (Button) findViewById(R.id.joinClinicBtn);
        joinClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open list of clinics that the employee can join
            }
        });

        registerClinicButton = (Button) findViewById(R.id.registerClinicBtn);
        registerClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterClinic();
            }
        });
    }


    public void openRegisterClinic() {
        Intent toRegisterClinic = new Intent(this, RegisterClinic.class);
        startActivity(toRegisterClinic);
    }
}