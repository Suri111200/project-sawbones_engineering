package walkinclinic.com.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class Register extends AppCompatActivity {

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerb);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcome();
            }
        });
    }

    public void showPassword (View view)
    {
        EditText password = (EditText) findViewById(R.id.passwordRegister);
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkboxRegister);

        if (checkbox.isChecked()) {
            // show password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // hide password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void  openWelcome ()
    {
        Intent toWelcome = new Intent(this, Welcome.class);
        startActivity(toWelcome);
    }





}
