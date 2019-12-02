package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileBasic extends AppCompatActivity {

    TextView nameT;
    TextView typeT;
    TextView emailT;

    Patient user;

    Button buttonToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_basic);

        nameT = findViewById(R.id.nameText);
        typeT = findViewById(R.id.typeText);
        emailT = findViewById(R.id.emailText);
        buttonToSearch = findViewById(R.id.searchB);

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");

        nameT.setText("Name: "+user.getName());
        typeT.setText("Type: "+ user.getClass().getSimpleName());
        emailT.setText("Email: "+ user.getEmail());

        buttonToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                toSearch();
            }
        });

        buttonToAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                toAppointments();
            }
        });

    }

    public void toSearch(){
        Intent toSearch = new Intent(this, SP_Search.class);
        toSearch.putExtra("Person", user);
        startActivity(toSearch);
    }

    public void toAppointments() {
        Intent toAppointments = new Intent(this, ViewAppointments.class);
        toAppointments.putExtra("Person", user);
        startActivity(toAppointments);
    }
}
