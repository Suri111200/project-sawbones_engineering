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

public class EmployeesAdmin extends AppCompatActivity {

    DatabaseReference mDatabase;
    //Button addService;
    ListView listViewEmployees;

    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_admin);

        //addService = (Button) findViewById(R.id.addService);
        listViewEmployees = (ListView) findViewById(R.id.employeeList);

        employees = new ArrayList<>();

        /*
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddServiceDialog();
            }
        });*/

        listViewEmployees.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = employees.get(i);
                showDeleteDialog(employee.getId(), employee.getName());
                return true;
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person/Employee");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employees.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Employee employee = new Employee(ds.child("id").getValue().toString(), ds.child("email").getValue().toString(), ds.child("password").getValue().toString(),ds.child("name").getValue().toString());
                    employees.add(employee);
                }
                EmployeeList employeesAdapter = new EmployeeList(EmployeesAdmin.this, employees);
                listViewEmployees.setAdapter(employeesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
/*
    private void showAddServiceDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.editTextRole);
        final Button buttonAddService = (Button) dialogView.findViewById(R.id.finalAddService);

        dialogBuilder.setTitle("Add Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role)) {
                    addService(name, role);
                    editTextName.setText("");
                    editTextRole.setText("");
                    b.dismiss();
                }
                Toast.makeText(ServicesAdmin.this, "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    private void showDeleteDialog(final String employeeId, String employeeName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        //final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.editTextRole);
        //final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(employeeName);
        final AlertDialog b = dialogBuilder.create();
        b.show();
/*
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role)) {
                    updateService(serviceId, name, role);
                    b.dismiss();
                }
                Toast.makeText(ServicesAdmin.this, "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
            }
        });
*/
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employeeId);
                b.dismiss();
            }
        });
    }
/*
    private void updateService(String id, String name, String role) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Service").child(id);

        Service service = new Service(id, name, role);
        dR.setValue(service);

        Toast.makeText(getApplicationContext(), "Service updated", Toast.LENGTH_LONG).show();
    }
*/
    private boolean deleteEmployee(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person/Employee").child(id);

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Employee deleted", Toast.LENGTH_LONG).show();
        return true;
    }
/*
    private void addService(String name, String role) {

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role))
        {
            String id = mDatabase.push().getKey();

            Service service = new Service (id, name, role);

            mDatabase.child(id).setValue(service);

            Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Please enter a name and a role", Toast.LENGTH_LONG).show();
        }
    }*/
}
