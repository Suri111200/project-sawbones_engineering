package walkinclinic.com.walkinclinic;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
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

    List<ServiceProvider> providers;
    SP_List providerAdapter;

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
        providers= new ArrayList<>();
        // Tests that the PersonList adapter retrieves the users correctly
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Person");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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

                //Runs a query "Test", filters the service providers and verifies that the ListView is updated correctly
                //This query checks both address and name
                String query = "Test";

                List<ServiceProvider> results = new ArrayList<>();
                Boolean hasService;
                Boolean hasAvailability;

                for(ServiceProvider sp : providers){
                    hasService = false;
                    hasAvailability = false;
                    //Queries company name, address, and the services of the company
                    for(Service s : sp.getServices()){
                        Log.i("snaptest", "Service " + s.getName() + " belongs to provider: " + sp.getCompany());
                        if(s.getName().toLowerCase().contains(query.toLowerCase()))
                            hasService = true;
                    }
                    for(Availability a : sp.getAvailabilities()){
                        if(a.toString().toLowerCase().contains(query.toLowerCase()))
                            hasAvailability = true;
                    }
                    if(sp.getCompany().toLowerCase().contains(query.toLowerCase()) || sp.getAddress().toLowerCase().contains(query.toLowerCase()) || hasService || hasAvailability) {  // i.getDescription().toLowerCase().contains(query.toLowerCase()
                        results.add(sp);
                    }
                }

                listViewServiceProviders = myActivity.findViewById(R.id.providerList);
                providerAdapter = (SP_List)listViewServiceProviders.getAdapter();
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);

                i = 0;
                for(ServiceProvider p : providerAdapter.getProviders()){
                    assertEquals(results.get(i).getName(),p.getName());
                    assertEquals(results.get(i).getEmail(), p.getEmail());
                    assertEquals(results.get(i).getId(),p.getId());
                    assertEquals(results.get(i).getCompany(),p.getCompany());
                    assertEquals(results.get(i).getDescription(),p.getDescription());
                    assertEquals(results.get(i).getPhoneNumber(),p.getPhoneNumber());
                    i++;
                }

                //Queries services
                query = "Blood";
                results = new ArrayList<>();

                for(ServiceProvider sp : providers){
                    hasService = false;
                    hasAvailability = false;
                    //Queries company name, address, and the services of the company
                    for(Service s : sp.getServices()){
                        if(s.getName().toLowerCase().contains(query.toLowerCase()))
                            hasService = true;
                    }
                    for(Availability a : sp.getAvailabilities()){
                        if(a.toString().toLowerCase().contains(query.toLowerCase()))
                            hasAvailability = true;
                    }
                    if(sp.getCompany().toLowerCase().contains(query.toLowerCase()) || sp.getAddress().toLowerCase().contains(query.toLowerCase()) || hasService || hasAvailability) {  // i.getDescription().toLowerCase().contains(query.toLowerCase()
                        results.add(sp);
                    }
                }
                //Service provider sp, our test service provider (?), has BloodWork and therefore should be 1
                assertTrue(results.size()>=1);

                listViewServiceProviders = myActivity.findViewById(R.id.providerList);
                providerAdapter = (SP_List)listViewServiceProviders.getAdapter();
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);

                //verifies updated adapter
                i = 0;
                for(ServiceProvider p : providerAdapter.getProviders()){
                    assertEquals(results.get(i).getName(),p.getName());
                    assertEquals(results.get(i).getEmail(), p.getEmail());
                    assertEquals(results.get(i).getId(),p.getId());
                    assertEquals(results.get(i).getCompany(),p.getCompany());
                    assertEquals(results.get(i).getDescription(),p.getDescription());
                    assertEquals(results.get(i).getPhoneNumber(),p.getPhoneNumber());
                    i++;
                }

                //Queries availability
                query = "monday";
                results = new ArrayList<>();

                for(ServiceProvider sp : providers){
                    hasService = false;
                    hasAvailability = false;
                    //Queries company name, address, and the services of the company
                    for(Service s : sp.getServices()){
                        if(s.getName().toLowerCase().contains(query.toLowerCase()))
                            hasService = true;
                    }
                    for(Availability a : sp.getAvailabilities()){
                        if(a.toString().toLowerCase().contains(query.toLowerCase()))
                            hasAvailability = true;
                    }
                    if(sp.getCompany().toLowerCase().contains(query.toLowerCase()) || sp.getAddress().toLowerCase().contains(query.toLowerCase()) || hasService || hasAvailability) {  // i.getDescription().toLowerCase().contains(query.toLowerCase()
                        results.add(sp);
                    }
                }
                //Service provider sp, our test service provider (?), has an available time monday and therefore should be at least 1
                assertTrue(results.size()>=1);

                listViewServiceProviders = myActivity.findViewById(R.id.providerList);
                providerAdapter = (SP_List)listViewServiceProviders.getAdapter();
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);

                //verifies updated adapter
                i = 0;
                for(ServiceProvider p : providerAdapter.getProviders()){
                    assertEquals(results.get(i).getName(),p.getName());
                    assertEquals(results.get(i).getEmail(), p.getEmail());
                    assertEquals(results.get(i).getId(),p.getId());
                    assertEquals(results.get(i).getCompany(),p.getCompany());
                    assertEquals(results.get(i).getDescription(),p.getDescription());
                    assertEquals(results.get(i).getPhoneNumber(),p.getPhoneNumber());
                    i++;
                }

                //And finally, enters gibberish and should display nothing
                query = "asdfasdf234234234asdfasdf";
                results = new ArrayList<>();

                for(ServiceProvider sp : providers){
                    hasService = false;
                    hasAvailability = false;
                    //Queries company name, address, and the services of the company
                    for(Service s : sp.getServices()){
                        if(s.getName().toLowerCase().contains(query.toLowerCase()))
                            hasService = true;
                    }
                    for(Availability a : sp.getAvailabilities()){
                        if(a.toString().toLowerCase().contains(query.toLowerCase()))
                            hasAvailability = true;
                    }
                    if(sp.getCompany().toLowerCase().contains(query.toLowerCase()) || sp.getAddress().toLowerCase().contains(query.toLowerCase()) || hasService || hasAvailability) {  // i.getDescription().toLowerCase().contains(query.toLowerCase()
                        results.add(sp);
                    }
                }
                //Service provider sp, our test service provider (?), has an available time monday and therefore should be at least 1
                assertTrue(results.size() == 0);

                listViewServiceProviders = myActivity.findViewById(R.id.providerList);
                providerAdapter = (SP_List)listViewServiceProviders.getAdapter();
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);

                //verifies updated adapter
                i = 0;
                for(ServiceProvider p : providerAdapter.getProviders()){
                    assertEquals(results.get(i).getName(),p.getName());
                    assertEquals(results.get(i).getEmail(), p.getEmail());
                    assertEquals(results.get(i).getId(),p.getId());
                    assertEquals(results.get(i).getCompany(),p.getCompany());
                    assertEquals(results.get(i).getDescription(),p.getDescription());
                    assertEquals(results.get(i).getPhoneNumber(),p.getPhoneNumber());
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        });
/*
        int i = 0;
        for(ServiceProvider p : providerAdapter.getProviders()){
            assertEquals(results.get(i).getName(),p.getName());
            assertEquals(results.get(i).getEmail(), p.getEmail());
            assertEquals(results.get(i).getId(),p.getId());
            assertEquals(results.get(i).getCompany(),p.getCompany());
            assertEquals(results.get(i).getDescription(),p.getDescription());
            assertEquals(results.get(i).getPhoneNumber(),p.getPhoneNumber());
            i++;
        }*/

    }
}