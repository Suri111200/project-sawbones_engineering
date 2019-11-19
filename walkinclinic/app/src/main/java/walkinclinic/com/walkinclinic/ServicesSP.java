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
    Button addService;
    ListView listViewProducts;

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

        services = new ArrayList<>();

        yourServices = new ArrayList<>();

        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showAddServiceSPDialog(service);
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
    }

    private void showAddServiceSPDialog(Service service) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.sp_services_dialog, null);
        dialogBuilder.setView(dialogView);

        final ListView listYourServices = (ListView) findViewById(R.id.yourServicesList);

        listYourServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourServices.remove(i);
                return true;
            }
        });

        dialogBuilder.setTitle("Your Services");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        yourServices.add(service);
        user.setServices(yourServices);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Person").child("ServiceProvider").child(user.getId()).setValue(user);

        ServiceList yourServicesAdapter = new ServiceList(ServicesSP.this, yourServices);
        listYourServices.setAdapter(yourServicesAdapter);

    }

}
