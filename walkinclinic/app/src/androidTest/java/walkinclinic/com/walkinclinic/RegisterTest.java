package walkinclinic.com.walkinclinic;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import androidx.test.annotation.UiThreadTest;

import static org.junit.Assert.*;


public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> myActivityTestRule = new ActivityTestRule(Register.class);
    private Register myActivity = null;
    EditText name;
    EditText email;
    EditText password;
    EditText password2;
    String type;

    @Before
    public void setUp() throws Exception {
        myActivity = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void testLaunch() {
        name = myActivity.findViewById(R.id.nameText);
        email = myActivity.findViewById(R.id.emailRegister);
        password = myActivity.findViewById(R.id.passwordRegister);
        password2 = myActivity.findViewById(R.id.password2Register);

        //might over-comment so i actually follow what im doing lol
        //checks fields are not null and the fields are actually correct
        name.setText("Joe");
        assertNotNull(name);
        assertEquals("Joe", name.getText().toString());

        //Selects register button with only name field entered
        Button register;
        register = myActivity.findViewById(R.id.registerb);
        register.performClick();
        TextView errorMessage;
        errorMessage = myActivity.findViewById(R.id.errorMessage);
        assertEquals("Please select what type of user profile you wish to be created.", errorMessage.getText().toString());

        //Selects type patient and clicks register button
        Button patient;
        patient = myActivity.findViewById(R.id.patientB);
        patient.performClick();
        //myActivity.type = "Patient";
        register.performClick();
        assertEquals("Make sure all fields are filled in.", errorMessage.getText().toString());

        //Remaining fields are entered
        email.setText("joe123@uottawa.ca");
        assertNotNull(email);
        assertEquals("joe123@uottawa.ca", email.getText().toString());
        password.setText("password123");
        assertNotNull(password);
        assertEquals("password123", password.getText().toString());
        password2.setText("password");
        assertNotNull(password2);
        assertEquals("password", password2.getText().toString());

        //Attempts to register with two different passwords
        register.performClick();
        assertEquals("Passwords don't match.", errorMessage.getText().toString());

        //Reenters passwords correctly
        password.setText("password123");
        password2.setText("password123");

        register.performClick();
        //maybe verify if user was created in database, not sure how to implement
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Person").child("Patient");




        /*
        Button signIn;
        signIn = myActivity.findViewById(R.id.signinb);
        signIn.performClick();
        TextView errorMessage;
        errorMessage = myActivity.findViewById(R.id.errorMessage);
        assertEquals("The password you've entered is incorrect.", errorMessage.getText().toString());
        */
    }

    @After
    public void tearDown() throws Exception {
        myActivity = null;
    }

}