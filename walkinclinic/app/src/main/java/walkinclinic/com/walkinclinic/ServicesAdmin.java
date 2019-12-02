package walkinclinic.com.walkinclinic;


import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdmin extends AppCompatActivity {

    DatabaseReference mDatabase;
    Button addService;
    ListView listViewProducts;

    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_admin);

        addService = (Button) findViewById(R.id.addService);
        listViewProducts = (ListView) findViewById(R.id.ConstraintLayout);

        services = new ArrayList<>();

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddServiceDialog();
            }
        });

        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getName());
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

                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString(), ds.child("rate").getValue().toString());

                    services.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(ServicesAdmin.this, services);
                listViewProducts.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showAddServiceDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.editTextRole);
        final EditText editTextRate = (EditText) dialogView.findViewById(R.id.editTextRate);
        final Button buttonAddService = (Button) dialogView.findViewById(R.id.finalAddAvail);

        dialogBuilder.setTitle("Add Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString();
                String rate = editTextRate.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role) && !TextUtils.isEmpty(rate)) {
                    addService(name, role, rate);
                    editTextName.setText("");
                    editTextRole.setText("");
                    editTextRate.setText("");
                    b.dismiss();
                }else {
                    Toast.makeText(ServicesAdmin.this, "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showUpdateDeleteDialog(final String serviceId, String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.editTextRole);
        final EditText editTextRate  = (EditText) dialogView.findViewById(R.id.editTextRate);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString();
                String rate = editTextRate.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role) && !TextUtils.isEmpty(rate)) {
                    updateService(serviceId, name, role, rate);
                    b.dismiss();
                }else {
                    Toast.makeText(ServicesAdmin.this, "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);
                b.dismiss();
            }
        });
    }

    private void updateService(String id, String name, String role, String rate) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Service").child(id);

        Service service = new Service(id, name, role, rate);
        dR.setValue(service);

        Toast.makeText(getApplicationContext(), "Service updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteService(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Service").child(id);

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private void addService(String name, String role, String rate ) {

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role) && !TextUtils.isEmpty(rate))
        {
            String id = mDatabase.push().getKey();

            Service service = new Service (id, name, role, rate);

            mDatabase.child(id).setValue(service);

            Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Please enter a name and a role", Toast.LENGTH_LONG).show();
        }
    }
}
