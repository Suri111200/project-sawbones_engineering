package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        TextView one = findViewById(R.id.textView);
        TextView two = findViewById(R.id.textView2);
        String display1 = "Welcome " + intent.getStringExtra("name") + "!";
        String display2 = "You are logged-in as " + intent.getStringExtra("role") + ".";
        one.setText(display1);
        two.setText(display2);

    }
}
