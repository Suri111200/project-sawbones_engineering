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
                            providers.add(provider);
                            Log.i("testing",provider.getCompany());
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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_provider,menu);
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
            public boolean onQueryTextChange(String query){
                List<ServiceProvider> results = new ArrayList<>();
                for(ServiceProvider i: providers){
                    //Queries company name, address, and the services of the company
                    if(i.getCompany().toLowerCase().contains(query.toLowerCase()) || i.getAddress().toLowerCase().contains(query.toLowerCase()) || hasService(i, query)) {  // i.getDescription().toLowerCase().contains(query.toLowerCase()
                        results.add(i);
                    }
                }
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getServices(ServiceProvider provider){
        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(provider.getId()).child("Services");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Service> results = new ArrayList<>();
                results.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString());
                    Log.i("getServices",service.getName() +" was retrieved and added to the list");
                    results.add(service);
                    if(results.size()>0)
                        //this also works
                        Log.i("getServices",results.get(0).getName() + " is the element at results(0)");
                    if(results.size()>1)
                        //works
                        Log.i("getServices",results.get(1).getName() + " is the element at results(1)");
                }
                updateServices(results);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateServices(ArrayList<Service> results){
        this.services = results;
        if(services.size()>1) {
            //this works!
            Log.i("updateServices", services.get(0).getName() + " is in services");
            Log.i("updateServices", services.get(1).getName() + " is in services");
            }
        }

    private Boolean hasService(ServiceProvider provider, String query){
        getServices(provider);
        Log.i("hasService","hasService runs");
        if(services != null) {
            // TODO this prints 0 as the size which should not be the case
            Log.i("hasService","First if statement, size of services is: " + services.size());
            //if (services.size() > 0) {
            //Log.i("hasService", "Second if statement");
                for (Service i : services) {
                    Log.i("hasService", "Service name " + i.getName() + ", Query:" + query);
                    if (i.getName().toLowerCase().contains(query)) {
                        Log.i("hasService", "Returns true.");
                        return true;
                    }
                }
            //}
        }
        return false;
    }
}
