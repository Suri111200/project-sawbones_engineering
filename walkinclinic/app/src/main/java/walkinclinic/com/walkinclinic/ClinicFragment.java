package walkinclinic.com.walkinclinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClinicFragment extends Fragment {

    private List<ServiceProvider> providers;
    private SP_List providerAdapter;
    private DatabaseReference mDatabase;
    private ListView listViewServiceProviders;
    private SearchView searchView;

    public ClinicFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        providers = new ArrayList<>();
        final ViewGroup container2 = container;
        View view = inflater.inflate(R.layout.frag_list_clinics, container, false);
        setHasOptionsMenu(true);

        listViewServiceProviders = (ListView) view.findViewById(R.id.providerList);

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

                providerAdapter = new SP_List(getActivity(),providers);
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

                toProfileClass.putExtra("Person", provider);
                startActivity(toProfileClass);
            }
        });

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_provider,menu);
        MenuItem searchClinicMenu = menu.findItem(R.id.searchClinics);
        SearchView searchView = (SearchView) searchClinicMenu.getActionView();

        //searchView.setFocusable(false);
        getActivity().setTitle("Placeholder");
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
                    if(i.getCompany().toLowerCase().contains(query.toLowerCase()) || i.getDescription().toLowerCase().contains(query.toLowerCase()))
                        results.add(i);
                }
                ((SP_List)listViewServiceProviders.getAdapter()).update(results, query);
                return false;
            }
        });
    }

}
