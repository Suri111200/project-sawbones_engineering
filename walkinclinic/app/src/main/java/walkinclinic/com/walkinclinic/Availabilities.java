package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Availabilities extends AppCompatActivity {

    DatabaseReference mDatabase;
    Button addAvail;
    ListView listViewAvail;

    List<Availability> availabilities;
    ServiceProvider user;

    String day = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availabilities);


        addAvail = (Button) findViewById(R.id.addAvailabilityB);
        listViewAvail = (ListView) findViewById(R.id.availabilityList);

        availabilities = new ArrayList<>();

        addAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddAvailDialog("");
            }
        });

        listViewAvail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Availability avail = availabilities.get(i);
                showUpdateDeleteDialog(avail.getId());
                return true;
            }
        });

        Intent intent = getIntent();
        user = (ServiceProvider) intent.getSerializableExtra("Person");

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Availability");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availabilities.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Availability Availability = new Availability(ds.child("id").getValue().toString(), ds.child("day").getValue().toString(), ds.child("startTime").getValue().toString(), ds.child("endTime").getValue().toString());
                    availabilities.add(Availability);
                }
                AvailabilityList AvailabilityAdapter = new AvailabilityList(Availabilities.this, availabilities);
                listViewAvail.setAdapter(AvailabilityAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void showAddAvailDialog(String id) {
        Intent toAddAvailabilities = new Intent(this, AddAvailabilities.class);
        toAddAvailabilities.putExtra("id", id);
        toAddAvailabilities.putExtra("Person", user);
        startActivity(toAddAvailabilities);
    }

    private void showUpdateDeleteDialog(final String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete_availability, null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle("Availability");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddAvailDialog(id);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAvailability(id);
                b.dismiss();
            }
        });
    }


    private boolean deleteAvailability(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(user.getId()).child("Availability").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Availability deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
