package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesSP extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView listViewProducts;
    ListView listViewYourServices;

    List<Service> services;

    List<Service> yourServices;

    ServiceProvider user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_sp);

        Intent intent = getIntent();

        user = (ServiceProvider) intent.getSerializableExtra("Person");

        listViewProducts = (ListView) findViewById(R.id.servicesList);
        listViewYourServices = findViewById(R.id.yourServicesList);

        services = new ArrayList<>();

        yourServices = new ArrayList<>();


        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                addService(service);
                return true;
            }
        });

        listViewYourServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = yourServices.get(i);
                deleteService(service.getId());
                return true;
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Service");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString());
                    services.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(ServicesSP.this, services);
                listViewProducts.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Services");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yourServices.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString());
                    yourServices.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(ServicesSP.this, yourServices);
                listViewYourServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addService(Service service) {

        for (int i = 0; i < yourServices.size(); i++)
        {
            if (yourServices.get(i).getId().equals(service.getId()))
            {
                Toast.makeText(ServicesSP.this, "Service already added", Toast.LENGTH_LONG).show();
                return;
            }

        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Toast.makeText(ServicesSP.this, "Service added.", Toast.LENGTH_LONG).show();
        DatabaseReference dR = mDatabase.child("Person").child("ServiceProvider").child(user.getId()).child("Services");
        dR.child(service.getId()).setValue(service);

    }

    private boolean deleteService(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Services").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service deleted", Toast.LENGTH_LONG).show();
        return true;
    }


}
