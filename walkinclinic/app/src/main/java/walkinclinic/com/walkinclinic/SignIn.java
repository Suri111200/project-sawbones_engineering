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

public class SignIn extends AppCompatActivity {

    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signinButton = (Button) findViewById(R.id.signinb);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcome();
            }
        });
    }

    public void showPassword (View view)
    {
        EditText password = (EditText) findViewById(R.id.passwordSignIn);
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkboxSignIn);

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
