package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;


public class ServiceProviderRegister extends AppCompatActivity {

    EditText addressB;
    EditText phoneNumberB;
    EditText descriptionB;
    EditText companyNameB;
    Switch switchB;
    Button registerButton;
    TextView errorMessageB;
    private DatabaseReference mDatabase;

    ServiceProvider user;

    boolean licensed;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        user = (ServiceProvider) intent.getSerializableExtra("Person");

        addressB = findViewById(R.id.address);
        phoneNumberB = findViewById(R.id.phoneNumber);
        descriptionB = findViewById(R.id.description);
        companyNameB = findViewById(R.id.companyName);
        switchB = findViewById(R.id.switch1);
        registerButton = (Button) findViewById(R.id.registerB);
        errorMessageB = findViewById(R.id.errorMessage);

        switchB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    licensed = true;
                    //Toast.makeText(ServiceProviderRegister.this, "licensed", Toast.LENGTH_LONG).show();
                } else {
                    licensed = false;
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = addressB.getText().toString();
                String phoneNumber = phoneNumberB.getText().toString();
                String description = descriptionB.getText().toString();
                String companyName = companyNameB.getText().toString();
                if (!address.equals("") && !phoneNumber.equals("") && !companyName.equals(""))
                {
                    user.setAddress(address);
                    user.setCompany(companyName);
                    user.setLicensed(licensed);
                    user.setDescription(description);
                    user.setPhoneNumber(phoneNumber);

                    registerUser (user);
                }
                else if(address.equals("")){
                    errorMessageB.setText("Please enter an address");
                }
                else if(phoneNumber.equals("")){
                    errorMessageB.setText("Please enter a phone number");
                }
                else if(companyName.equals("")){
                    errorMessageB.setText("Please enter a name for the clinic");
                }else
                    errorMessageB.setText("Make sure all fields are filled in.");
            }
        });
    }

    public void  registerUser (Person user) {

        Intent toWelcome = new Intent(this, Welcome.class);
        mDatabase.child("Person").child("ServiceProvider").child(user.getId()).setValue(user);
        Toast.makeText(ServiceProviderRegister.this, "Service Provider account created", Toast.LENGTH_LONG).show();
        toWelcome.putExtra("Person", user);
        startActivity(toWelcome);
    }
}
