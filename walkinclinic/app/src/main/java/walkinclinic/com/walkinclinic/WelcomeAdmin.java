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

public class WelcomeAdmin extends AppCompatActivity {

    Person user;
    Button buttonToService;
    Button buttonToEmployeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Person").child("Patient");

        buttonToService = (Button) findViewById(R.id.toServices);
        buttonToEmployeeList = (Button) findViewById(R.id.toEmployeeList);

        //ref.orderByChild("name").endAt(5).toString();

        Intent intent = getIntent();
        user = (Person) intent.getSerializableExtra("Person");
        TextView one = findViewById(R.id.textView);
        TextView two = findViewById(R.id.textView2);
        String display1 = "Welcome " + user.getName() + "!";
        String display2 = "You are logged-in as " + user.getClass().getSimpleName() + ".";
        one.setText(display1);
        two.setText(display2);

        //one.setText((ref.orderByChild("name").endAt(5).toString()));

        buttonToService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toServices();
            }
        });

        buttonToEmployeeList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toEmployees();
            }
        });


    }

    public void toServices ()
    {
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        Intent toServices = new Intent(this, ServicesAdmin.class);

        //toWelcome.putExtra("Person", user);
        startActivity(toServices);
    }

    public void toEmployees(){
        Intent toEmployees = new Intent(this, EmployeesAdmin.class);
        startActivity(toEmployees);
    }

}
