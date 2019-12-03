package walkinclinic.com.walkinclinic;

import android.util.Log;
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
public class SP_SearchTest {

    @Rule
    public ActivityTestRule<SP_Search> myActivityTestRule = new ActivityTestRule(SP_Search.class);
    private SP_Search myActivity = null;

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
                List<ServiceProvider> providers= new ArrayList<>();
                providers.clear();
                for (DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds : dSnapshot.getChildren()) {
                        if (dSnapshot.getKey().toString().equals("ServiceProvider")) {
                            ServiceProvider provider = new ServiceProvider(
                                    ds.child("id").getValue().toString(),
                                    ds.child("email").getValue().toString(),
                                    ds.child("password").getValue().toString(),
                                    ds.child("name").getValue().toString(),
                                    ds.child("address").getValue().toString(),
                                    ds.child("phoneNumber").getValue().toString(),
                                    ds.child("company").getValue().toString(),
                                    ds.child("description").getValue().toString(),
                                    Boolean.parseBoolean(ds.child("licensed").getValue().toString()));
                            for(DataSnapshot serviceSnapshot : ds.child("Services").getChildren()){
                                provider.addService(new Service(serviceSnapshot.child("id").getValue().toString(), serviceSnapshot.child("name").getValue().toString(), serviceSnapshot.child("role").getValue().toString(), serviceSnapshot.child("rate").getValue().toString()));
                            }
                            for(DataSnapshot availabilitySnapshot : ds.child("Availability").getChildren()){
                                provider.addAvailability(new Availability(availabilitySnapshot.child("day").getValue().toString(), availabilitySnapshot.child("startTime").getValue().toString(),availabilitySnapshot.child("endTime").getValue().toString()));
                            }
                            //provider.setServices(services);
                            providers.add(provider);
                        }
                    }
                }
                ListView listViewServiceProviders = myActivity.findViewById(R.id.providerList);
                SP_List providerAdapter = (SP_List)listViewServiceProviders.getAdapter();

                int i = 0;
                for(ServiceProvider p : providerAdapter.getProviders()){
                    assertEquals(providers.get(i).getName(),p.getName());
                    assertEquals(providers.get(i).getEmail(), p.getEmail());
                    assertEquals(providers.get(i).getId(),p.getId());
                    assertEquals(providers.get(i).getCompany(),p.getCompany());
                    assertEquals(providers.get(i).getDescription(),p.getDescription());
                    assertEquals(providers.get(i).getPhoneNumber(),p.getPhoneNumber());
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        });


    }
}