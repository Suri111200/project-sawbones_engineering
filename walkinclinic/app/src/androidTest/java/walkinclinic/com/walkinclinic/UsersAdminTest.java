package walkinclinic.com.walkinclinic;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UsersAdminTest {

    @Rule
    public ActivityTestRule<UsersAdmin> myActivityTestRule = new ActivityTestRule(UsersAdmin.class);
    private UsersAdmin myActivity = null;

    @Before
    public void setUp() throws Exception {
        myActivity = myActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @UiThreadTest
    public void testLaunch() {

        // Tests that the PersonList adapter retrieves the users correctly
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Person");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Person> users = new ArrayList<>();
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
                ListView listViewUsers = myActivity.findViewById(R.id.usersList);
                PersonList personAdapter = (PersonList)listViewUsers.getAdapter();
                int i = 0;
                for(Person p : personAdapter.getUsers()){
                    assertEquals(users.get(i).getName(),p.getName());
                    assertEquals(users.get(i).getEmail(), p.getEmail());
                    assertEquals(users.get(i).getId(),p.getId());
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}