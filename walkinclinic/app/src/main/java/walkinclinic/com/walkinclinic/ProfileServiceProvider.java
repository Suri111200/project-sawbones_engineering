package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileServiceProvider extends AppCompatActivity {

    TextView nameT;
    TextView typeT;
    TextView emailT;
    TextView companyT;
    TextView addressT;
    TextView phoneT;
    TextView descT;
    TextView licensedT;
    Button buttonToAvail;
    Button buttonToServices;

    ServiceProvider user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_service_provider);

        nameT = findViewById(R.id.nameSPText);
        typeT = findViewById(R.id.typeSPText);
        emailT = findViewById(R.id.emailSPText);
        companyT = findViewById(R.id.companySPText);
        addressT = findViewById(R.id.addressSPText);
        phoneT = findViewById(R.id.phoneSPText);
        descT = findViewById(R.id.descSPText);
        licensedT = findViewById(R.id.licensedSPText);
        buttonToServices = findViewById(R.id.servicesSPB);
        buttonToAvail = findViewById(R.id.availSPB);

        Intent intent = getIntent();
        user = (ServiceProvider) intent.getSerializableExtra("Person");

        String name = "Name: "+user.getName();
        String type = "Type: "+ user.getClass().getSimpleName();
        String email = "Email: "+ user.getEmail();
        String company = "Company: "+user.getCompany();
        String address = "Address: "+user.getAddress();
        String phone = "Phone Number: "+user.getPhoneNumber();
        String desc = "Description: "+user.getDescription();
        String licensed;
        if (user.getLicensed())
            licensed = "Licensed";
        else
            licensed = "Not Licensed";

        //Toast.makeText(ProfileServiceProvider.this, email, Toast.LENGTH_LONG).show();
        nameT.setText(name);
        typeT.setText(type);
        emailT.setText(email);
        companyT.setText(company);
        addressT.setText(address);
        phoneT.setText(phone);
        descT.setText(desc);
        licensedT.setText(licensed);

        buttonToServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toServices();
            }
        });

        buttonToAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAvailabilities();
            }
        });

    }

    public void toServices ()
    {
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        Intent toServices = new Intent(this, ServicesSP.class);
        toServices.putExtra("Person", user);
        startActivity(toServices);
    }

    public void toAvailabilities ()
    {
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        Intent toServices = new Intent(this, Availabilities.class);
        toServices.putExtra("Person", user);
        startActivity(toServices);
    }
}
