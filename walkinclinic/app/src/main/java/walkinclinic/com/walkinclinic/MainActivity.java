package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    Button registerButton;
    Button signinButton;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = (Button) findViewById(R.id.toregister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        signinButton = (Button) findViewById(R.id.signin);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignin();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        LatLng uOttawa = new LatLng(45.4231, -75.6831);
        map.addMarker(new MarkerOptions().position(uOttawa).title("uOttawa"));
        map.moveCamera(CameraUpdateFactory.newLatLng(uOttawa));
    }

    public void openRegister ()
    {
        Intent toRegister = new Intent(this, Register.class);
        startActivity(toRegister);
    }

    public void openSignin ()
    {
        Intent toSignIn = new Intent (this, SignIn.class);
        startActivity(toSignIn);
    }
}
