package walkinclinic.com.walkinclinic;


import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.view.MenuItem;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SP_Search extends AppCompatActivity {

    Patient user;
    private List<ServiceProvider> providers;
    private SP_List providerAdapter;
    private DatabaseReference mDatabase;
    private ListView listViewServiceProviders;
    private SearchView searchView;
    private ArrayList<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_search);

        services = new ArrayList<>();

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");
/*
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        AppBarLayout appbar = findViewById(R.id.appbar);

        TabItem tabClinic = findViewById(R.id.TabClinic);
        TabItem tabService = findViewById(R.id.TabService);

        final ViewPager viewPager = findViewById(R.id.ViewPager);
        SearchAdapter searchAdapter = new SearchAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), user);
        viewPager.setAdapter(searchAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        */


        providers = new ArrayList<>();
        services = new ArrayList();
        //setHasOptionsMenu(true);

        listViewServiceProviders = (ListView) findViewById(R.id.providerList);
        mDatabase = FirebaseDatabase.getInstance().getReference("Person");
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
                                Log.i("snaptest",serviceSnapshot.child("role").getValue().toString() + " " + serviceSnapshot.child("name").getValue().toString());
                                provider.addService(new Service(serviceSnapshot.child("id").getValue().toString(), serviceSnapshot.child("name").getValue().toString(), serviceSnapshot.child("role").getValue().toString()));
                            }
                            for(DataSnapshot availabilitySnapshot : ds.child("Availability").getChildren()){
                                //Log.i("snaptest",serviceSnapshot.child("role").getValue().toString() + " " + serviceSnapshot.child("name").getValue().toString());
                                provider.addAvailability(availabilitySnapshot.child("day").getValue().toString() + ": " + availabilitySnapshot.child("startTime").getValue().toString() + " - " + availabilitySnapshot.child("endTime").getValue().toString());
                            }
                            //provider.setServices(services);
                            providers.add(provider);
                        }
                    }
                }

                providerAdapter = new SP_List(SP_Search.this,providers);
                listViewServiceProviders.setAdapter(providerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        });

        listViewServiceProviders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Open clinic profile
                ServiceProvider provider = providers.get(i);
                Intent toProfileClass = new Intent(adapterView.getContext(), ServiceProviderInfo.class);
                toProfileClass.putExtra("Person", user);
                toProfileClass.putExtra("ServiceProvider", provider);
                startActivity(toProfileClass);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_provider, menu);
        MenuItem searchClinicMenu = menu.findItem(R.id.searchClinics);
        SearchView searchView = (SearchView) searchClinicMenu.getActionView();

        //searchView.setFocusable(false);
        setTitle("Clinics");
        searchView.setFocusable(true);
        searchView.setIconified(true);
        searchView.setQueryHint("Search clinics");
        searchView.clearFocus();
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                List<ServiceProvider> results = new ArrayList<>();
                Boolean hasService;
                Boolean hasAvailability;
                for(ServiceProvider i : providers){
                    hasService = false;
                    hasAvailability = false;
                    //Queries company name, address, and the services of the company
                    for(Service s : i.getServices()){
                        Log.i("snaptest", "Service " + s.getName() + " belongs to provider: " + i.getCompany());
                        if(s.getName().toLowerCase().contains(query))
                            hasService = true;
                    }
                    for(String a : i.getAvailabilities()){
                        if(a.toLowerCase().contains(query))
                            hasAvailability = true;
                    }
                    if(i.getCompany().toLowerCase().contains(query.toLowerCase()) || i.getAddress().toLowerCase().contains(query.toLowerCase()) || hasService || hasAvailability) {  // i.getDescription().toLowerCase().contains(query.toLowerCase()
                        results.add(i);
                    }
                }
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
