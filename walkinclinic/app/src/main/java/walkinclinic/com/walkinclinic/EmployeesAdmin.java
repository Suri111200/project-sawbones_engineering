package walkinclinic.com.walkinclinic;


import android.app.AlertDialog;
import android.util.Log;
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
    ListView listViewEmployees;

    List<Person> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_admin);

        listViewEmployees = (ListView) findViewById(R.id.employeeList);
        employees = new ArrayList<>();

        listViewEmployees.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person employee = employees.get(i);
                showDeleteDialog(employee.getId(), employee.getName());
                return true;
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employees.clear();

                for (DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds : dSnapshot.getChildren()) {
                        if(dSnapshot.getKey().toString().equals("Patient")){
                            Person user = new Patient(ds.child("id").getValue().toString(), ds.child("email").getValue().toString(), ds.child("password").getValue().toString(), ds.child("name").getValue().toString());
                            employees.add(user);
                        }
                        else if(dSnapshot.getKey().toString().equals("Employee")){
                            Person user = new Employee(ds.child("id").getValue().toString(), ds.child("email").getValue().toString(), ds.child("password").getValue().toString(), ds.child("name").getValue().toString());
                            employees.add(user);
                        }
                    }

                }

                PersonList employeesAdapter = new PersonList(EmployeesAdmin.this, employees);
                listViewEmployees.setAdapter(employeesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    private void showDeleteDialog(final String employeeId, String employeeName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonCancelProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle("Are you sure you want to delete " + employeeName + "?");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employeeId);
                b.dismiss();
            }
        });
    }

    private boolean deleteEmployee(String id) {

        //lazy but it works
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person/Patient").child(id);
        dR.removeValue();
        dR = FirebaseDatabase.getInstance().getReference("Person/Employee").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
