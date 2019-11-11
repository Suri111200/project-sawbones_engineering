package walkinclinic.com.walkinclinic;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.annotation.UiThreadTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class SignInTest {

    @Rule
    public ActivityTestRule<SignIn> myActivityTestRule = new ActivityTestRule(SignIn.class);
    private SignIn myActivity = null;

    @Before
    public void setUp() throws Exception {
        myActivity = myActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @UiThreadTest
    public void testLaunch() {
        
        EditText email;
        email = myActivity.findViewById(R.id.emailSignIn);
        EditText password;
        password = myActivity.findViewById(R.id.passwordSignIn);
        Button signIn;
        signIn = myActivity.findViewById(R.id.signinb);
        TextView errorMessage;

        //Tests the admin login
        email.setText("admin@uottawa.ca");
        assertEquals("admin@uottawa.ca", email.getText().toString());

        //Tests a login with a missing field
        password.getText().clear();
        signIn.performClick();
        errorMessage = myActivity.findViewById(R.id.errorMessage);
        assertEquals("Make sure all fields are filled in.", errorMessage.getText().toString());

        password.setText("poop");
        signIn.performClick();
        errorMessage = myActivity.findViewById(R.id.errorMessage);
        assertEquals("The password you've entered is incorrect.", errorMessage.getText().toString());

        //Verifies password
        password.setText("5T5ptR");
        assertNotEquals("5T5ptQ", password.getText().toString());


    }


//    @Test
//    public void onCreate() {
//    }
//
//    @Test
//    public void showPassword() {
//    }
//
//    @Test
//    public void verify() {
//    }
//
//    @Test
//    public void signIn() {
//    }
}