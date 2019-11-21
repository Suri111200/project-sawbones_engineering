package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Welcome extends AppCompatActivity {

    Person user;
    //Button buttonToService;
    Button buttonToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

       // buttonToService = (Button) findViewById(R.id.toServices);
        buttonToProfile = (Button) findViewById(R.id.toProfile);

        //ref.orderByChild("name").endAt(5).toString();

        Intent intent = getIntent();
        user = (Person) intent.getSerializableExtra("Person");
        TextView one = findViewById(R.id.textView);
        TextView two = findViewById(R.id.textView2);
        String display1 = "Welcome " + user.getName() + "!";
        String display2 = "You are logged-in as a " + user.getClass().getSimpleName() + ".";
        one.setText(display1);
        two.setText(display2);

//        buttonToService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toServices();
//            }
//        });
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                toProfile();
            }
        });

    }

//    public void toServices ()
//    {
//        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
//        Intent toServices = new Intent(this, ServicesBasic.class);
//        if (user.getClass().getSimpleName().equals("Admin")) {
//            //Toast.makeText(Welcome.this, "yay", Toast.LENGTH_LONG).show();
//            toServices = new Intent(this, ServicesAdmin.class);
//        }
//
//        toServices.putExtra("Person", user);
//        startActivity(toServices);
//    }

    private void toProfile ()
    {

        Intent toProfileClass = new Intent(this, ProfileBasic.class);
        if (user.getClass().getSimpleName().equals("Admin")) {
            //Toast.makeText(Welcome.this, "yay", Toast.LENGTH_LONG).show();
            toProfileClass = new Intent(this, ProfileAdmin.class);
        }
        else if (user.getClass().getSimpleName().equals("ServiceProvider"))
        {
            //Toast.makeText(Welcome.this, "yay", Toast.LENGTH_LONG).show();
            toProfileClass = new Intent(this, ProfileServiceProvider.class);
        }

        toProfileClass.putExtra("Person", user);
        startActivity(toProfileClass);
    }

}
