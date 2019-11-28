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

import java.util.ArrayList;
import java.util.List;

public class SP_Search extends AppCompatActivity {

    Patient user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_search);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");

        AppBarLayout appbar = findViewById(R.id.appbar);
        TabItem tabClinic = findViewById(R.id.TabClinic);
        TabItem tabService = findViewById(R.id.TabService);

        final ViewPager viewPager = findViewById(R.id.ViewPager);
        SearchAdapter searchAdapter = new SearchAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), user);
        viewPager.setAdapter(searchAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
