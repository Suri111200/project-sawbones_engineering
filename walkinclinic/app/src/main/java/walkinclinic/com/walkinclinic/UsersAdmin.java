package walkinclinic.com.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class UsersAdmin extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView listViewUsers;

    List<Person> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_admin);

        listViewUsers = (ListView) findViewById(R.id.usersList);

        users = new ArrayList<>();


        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person user = users.get(i);
                deleteUser(user);
                return true;
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Person");

        // THE FOLLOWING GETS IT BUGGING OUT
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot ds : dataSnapshot.child("Patient").getChildren()) {
                    String id = ds.child("id").getValue().toString();
                    String email = ds.child("email").getValue().toString();
                    String password = ds.child("password").getValue().toString();
                    String name = ds.child("name").getValue().toString();
                    users.add(new Patient(id, email, password, name));
                }
                for (DataSnapshot ds : dataSnapshot.child("Admin").getChildren()) {
                    String id = ds.child("id").getValue().toString();
                    String email = ds.child("email").getValue().toString();
                    String password = ds.child("password").getValue().toString();
                    String name = ds.child("name").getValue().toString();
                    users.add(new Admin(id, email, password, name));
                }
                for (DataSnapshot ds : dataSnapshot.child("ServiceProvider").getChildren()) {
                    String id = ds.child("id").getValue().toString();
                    String email = ds.child("email").getValue().toString();
                    String password = ds.child("password").getValue().toString();
                    String name = ds.child("name").getValue().toString();
                    String address = ds.child("address").getValue().toString();
                    String company = ds.child("company").getValue().toString();
                    String desc = ds.child("description").getValue().toString();
                    String phone = ds.child("phoneNumber").getValue().toString();
                    boolean licensed = (boolean) ds.child("licensed").getValue();
                    users.add(new ServiceProvider(id, email, password, name, address, phone, company, desc, licensed));
                }
                PersonList personAdapter = new PersonList(UsersAdmin.this, users);
                listViewUsers.setAdapter(personAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private boolean deleteUser(Person user) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child(user.getClass().getSimpleName()).child(user.getId());

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
