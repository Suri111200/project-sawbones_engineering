package walkinclinic.com.walkinclinic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends Fragment {

    DatabaseReference mDatabase;
    ListView listViewProducts;
    List<Service> services;
    private ServiceList servicesAdapter;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_services, container, false);
        setHasOptionsMenu(true);

        listViewProducts = (ListView) view.findViewById(R.id.ConstraintLayout);

        services = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Service");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString(), ds.child("rate").getValue().toString());
                    services.add(service);
                }
                servicesAdapter = new ServiceList(getActivity(), services);
                listViewProducts.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProviderServicesFragment nextFrag= new ProviderServicesFragment();

                Bundle bundle = new Bundle();
                Service service = services.get(i);
                bundle.putString("Service",service.getName());
                nextFrag.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), nextFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
        getActivity().setTitle("Placeholder?");
        searchView.setFocusable(true);
        searchView.setIconified(true);
        searchView.setQueryHint("Search services");
        searchView.clearFocus();
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query){
                List<Service> results = new ArrayList<>();
                for(Service i: services){
                    if(i.getName().toLowerCase().contains(query.toLowerCase()))
                        results.add(i);
                }
                ((ServiceList)listViewProducts.getAdapter()).update(results, query);
                return false;
            }
        });
    }
}
