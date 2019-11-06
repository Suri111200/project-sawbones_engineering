package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Person").child("Patient");

        //ref.orderByChild("name").endAt(5).toString();

        Intent intent = getIntent();
        TextView one = findViewById(R.id.textView);
        TextView two = findViewById(R.id.textView2);
        String display1 = "Welcome " + intent.getStringExtra("name") + "!";
        String display2 = "You are logged-in as " + intent.getStringExtra("role") + ".";
        one.setText(display1);
        two.setText(display2);

        //one.setText((ref.orderByChild("name").endAt(5).toString()));


    }


}
