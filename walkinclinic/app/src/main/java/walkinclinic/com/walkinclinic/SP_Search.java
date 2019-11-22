package walkinclinic.com.walkinclinic;


import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SP_Search extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView listViewServiceProviders;

    List<ServiceProvider> providers;

    SP_List providerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_search);

        listViewServiceProviders = (ListView) findViewById(R.id.providerList);
        providers = new ArrayList<>();

        listViewServiceProviders.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Prompt to review? Book an appointment?
                return true;
            }
        });

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
                        }
                    }
                }

                providerAdapter = new SP_List(SP_Search.this, providers);
                listViewServiceProviders.setAdapter(providerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_provider,menu);
        MenuItem menuItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query){
                List<ServiceProvider> results = new ArrayList<>();
                for(ServiceProvider i: providers){
                    if(i.getCompany().toLowerCase().contains(query.toLowerCase()))
                        results.add(i);
                }
                ((SP_List)listViewServiceProviders.getAdapter()).update(results);
                return false;
            }


        });

        return super.onCreateOptionsMenu(menu);
    }
}
