package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        emailT = findViewById(R.id.emailAText);
        companyT = findViewById(R.id.companySPText);
        addressT = findViewById(R.id.addressSPText);
        phoneT = findViewById(R.id.phoneSPText);
        descT = findViewById(R.id.descSPText);
        licensedT = findViewById(R.id.licensedSPText);
        buttonToServices = findViewById(R.id.servicesSPB);
        buttonToAvail = findViewById(R.id.availSPB);

        Intent intent = getIntent();
        user = (ServiceProvider) intent.getSerializableExtra("Person");

        nameT.setText("Email: "+user.getName());
        typeT.setText("Type: "+ user.getClass().getSimpleName());
        emailT.setText("Email: "+ user.getEmail());
        companyT.setText("Company: "+user.getCompany());
        addressT.setText("Address: "+user.getAddress());
        phoneT.setText("Phone Number: "+user.getPhoneNumber());
        descT.setText("Description: "+user.getDescription());
        if (user.getLicensed() == true)
            licensedT.setText("Licensed");
        else
            licensedT.setText("Not Licensed");

        buttonToServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toServices();
            }
        });

        buttonToAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAvailibilities();
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

    public void toAvailibilities ()
    {
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        Intent toServices = new Intent(this, Availibilities.class);
        toServices.putExtra("Person", user);
        startActivity(toServices);
    }
}
