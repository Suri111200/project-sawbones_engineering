package walkinclinic.com.walkinclinic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ServicesBasic extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView listViewProducts;

    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_basic);

       // Toast.makeText(ServicesBasic.this, "Services Basic", Toast.LENGTH_LONG).show();
        listViewProducts = (ListView) findViewById(R.id.ConstraintLayout);

        services = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Service");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString(), ds.child("rate").getValue().toString());
                    services.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(ServicesBasic.this, services);
                listViewProducts.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
