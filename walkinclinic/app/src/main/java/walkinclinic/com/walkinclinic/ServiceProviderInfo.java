package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderInfo extends AppCompatActivity {

    TextView nameT;
    TextView typeT;
    TextView emailT;
    TextView companyT;
    TextView addressT;
    TextView phoneT;
    TextView descT;
    TextView licensedT;

    Button bookB;
    Button checkInB;
    Button reviewB;

    Patient user;
    ServiceProvider sp;

    DatabaseReference mDatabase;
    ListView listViewYourServices;
    List<Service> yourServices;
    ListView listViewAvail;
    List<Availability> availabilities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_info);

        nameT = findViewById(R.id.nameSPText);
        typeT = findViewById(R.id.typeSPText);
        emailT = findViewById(R.id.emailSPText);
        companyT = findViewById(R.id.companySPText);
        addressT = findViewById(R.id.addressSPText);
        phoneT = findViewById(R.id.phoneSPText);
        descT = findViewById(R.id.descSPText);
        licensedT = findViewById(R.id.licensedSPText);
        listViewAvail = findViewById(R.id.availabilitiesList);
        listViewYourServices = findViewById(R.id.serviceList);

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");
        sp = (ServiceProvider) intent.getSerializableExtra("ServiceProvider");

        bookB = findViewById(R.id.appointmentB);
        checkInB = findViewById(R.id.checkInB);
        reviewB = findViewById(R.id.reviewB);

        bookB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBookAppointment = new Intent(listViewAvail.getContext(), BookAppointment.class);
                toBookAppointment.putExtra("Person", user);
                toBookAppointment.putExtra("ServiceProvider", sp);
                startActivity(toBookAppointment);
            }
        });

//        checkInB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        String name = "Name: " + sp.getName();
        String type = "Type: " + sp.getClass().getSimpleName();
        String email = "Email: " + sp.getEmail();
        String company = "Company: " + sp.getCompany();
        String address = "Address: " + sp.getAddress();
        String phone = "Phone Number: " + sp.getPhoneNumber();
        String desc = "Description: " + sp.getDescription();
        String licensed;
        if (sp.getLicensed())
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

        yourServices = new ArrayList<>();

        availabilities = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Services");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yourServices.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString(), ds.child("rate").getValue().toString());
                    yourServices.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(ServiceProviderInfo.this, yourServices);
                listViewYourServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(sp.getId()).child("Availability");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availabilities.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Availability Availability = new Availability(ds.child("id").getValue().toString(), ds.child("day").getValue().toString(), ds.child("startTime").getValue().toString(), ds.child("endTime").getValue().toString());
                    availabilities.add(Availability);
                }
                AvailabilityList AvailabilityAdapter = new AvailabilityList(ServiceProviderInfo.this, availabilities);
                listViewAvail.setAdapter(AvailabilityAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
