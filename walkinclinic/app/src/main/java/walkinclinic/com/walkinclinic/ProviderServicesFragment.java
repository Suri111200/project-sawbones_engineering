package walkinclinic.com.walkinclinic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProviderServicesFragment extends Fragment {

    DatabaseReference mDatabase;
    private List<ServiceProvider> providers;
    private SP_List providerAdapter2;
    private ListView listViewServiceProviders;
    String service;

    public ProviderServicesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_serviceclinics, container, false);
        service = getArguments().getString("Service");
        listViewServiceProviders = (ListView) view.findViewById(R.id.providerListAgain);
        providers = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("Person/ServiceProvider");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providers.clear();
                for (DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    //TODO: doesnt work.
                    // it runs all the code but i get a blank screen.
                    ServiceProvider provider = new ServiceProvider(
                            dSnapshot.child("id").getValue().toString(),
                            dSnapshot.child("email").getValue().toString(),
                            dSnapshot.child("password").getValue().toString(),
                            dSnapshot.child("name").getValue().toString(),
                            dSnapshot.child("address").getValue().toString(),
                            dSnapshot.child("phoneNumber").getValue().toString(),
                            dSnapshot.child("company").getValue().toString(),
                            dSnapshot.child("description").getValue().toString(),
                            Boolean.parseBoolean(dSnapshot.child("licensed").getValue().toString()));
                    for(DataSnapshot ds : dSnapshot.getChildren()){
                        for(DataSnapshot ds2 : ds.getChildren())
                            if(ds2.child("name").getValue()!=null) {
                                Log.i("poop", ds2.child("name").getValue().toString());
                                if(ds2.child("name").getValue().toString().equals(service)) {
                                    Log.i("poop", ds2.child("name").getValue().toString());
                                    providers.add(provider);
                                }
                            }
                    }
                    if(providers.size()>0)
                        Log.i("poop2", providers.get(0).getCompany().toString());
                    providerAdapter2 = new SP_List(getActivity(), providers);
                    listViewServiceProviders.setAdapter(providerAdapter2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        listViewServiceProviders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Do a thing
            }
        });
        return view;
    }
}
