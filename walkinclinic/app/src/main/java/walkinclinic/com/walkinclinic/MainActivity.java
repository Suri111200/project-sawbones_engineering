package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button registerButton;
    Button signinButton;
    Button registerClinicButton;

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
        registerClinicButton = (Button) findViewById(R.id.registerClinic);
        registerClinicButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRegisterClinic();
            }
        });

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

    public void openRegisterClinic()
    {
        Intent toRegisterClinic = new Intent(this, RegisterClinic.class);
        startActivity(toRegisterClinic);
    }
}
