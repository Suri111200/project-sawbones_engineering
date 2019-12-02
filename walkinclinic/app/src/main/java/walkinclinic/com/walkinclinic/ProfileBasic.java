package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileBasic extends AppCompatActivity {

    TextView nameT;
    TextView typeT;
    TextView emailT;

    Patient user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_basic);

        nameT = findViewById(R.id.nameText);
        typeT = findViewById(R.id.typeText);
        emailT = findViewById(R.id.emailText);

        Intent intent = getIntent();
        user = (Patient) intent.getSerializableExtra("Person");

        nameT.setText("Email: "+user.getName());
        typeT.setText("Type: "+ user.getClass().getSimpleName());
        emailT.setText("Email: "+ user.getEmail());
    }
}
