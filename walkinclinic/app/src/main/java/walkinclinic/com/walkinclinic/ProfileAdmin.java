package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileAdmin extends AppCompatActivity {

    TextView nameT;
    TextView typeT;
    TextView emailT;
    Button buttonToServices;
    Button buttonToUsers;

    Admin user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        nameT = findViewById(R.id.nameAText);
        typeT = findViewById(R.id.typeAText);
        emailT = findViewById(R.id.emailAText);
        buttonToServices = findViewById(R.id.serviceB);
        buttonToUsers = findViewById(R.id.usersB);

        Intent intent = getIntent();
        user = (Admin) intent.getSerializableExtra("Person");

        nameT.setText("Email: "+user.getName());
        typeT.setText("Type: "+ user.getClass().getSimpleName());
        emailT.setText("Email: "+ user.getEmail());

        buttonToServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toServices();
            }
        });

        /*
        buttonToUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUsers();
            }
        });
        */
    }

    public void toServices ()
    {
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        Intent toServices = new Intent(this, ServicesAdmin.class);
        toServices.putExtra("Person", user);
        startActivity(toServices);
    }
    /*
    public void toUsers ()
    {
        //Toast.makeText(Welcome.this, user.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        Intent toUsers = new Intent(this, EmployeesAdmin.class);
        toUsers.putExtra("Person", user);
        startActivity(toUsers);
    */
}
