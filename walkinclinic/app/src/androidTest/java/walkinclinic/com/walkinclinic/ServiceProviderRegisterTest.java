package walkinclinic.com.walkinclinic;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.annotation.UiThreadTest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static org.junit.Assert.*;


public class ServiceProviderRegisterTest {

    @Rule
    public ActivityTestRule<ServiceProviderRegister> myActivityTestRule = new ActivityTestRule(ServiceProviderRegister.class);
    private ServiceProviderRegister myActivity = null;
    EditText addressB;
    EditText phoneNumberB;
    EditText descriptionB;
    EditText companyNameB;
    Button registerButton;
    TextView errorMessageB;

    @Before
    public void setUp() throws Exception {
        myActivity = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void testLaunch() {

        //THIS ONLY TESTS FIELDS
        //If all fields entered and register button hit, null error
        //Since ServiceProviderRegister relies on the User created in the Registration screen

        addressB = myActivity.findViewById(R.id.address);
        phoneNumberB = myActivity.findViewById(R.id.phoneNumber);
        descriptionB = myActivity.findViewById(R.id.description);
        companyNameB = myActivity.findViewById(R.id.companyName);
        registerButton = (Button) myActivity.findViewById(R.id.registerB);
        errorMessageB = myActivity.findViewById(R.id.errorMessage);

        registerButton.performClick();
        assertEquals("Please enter an address", errorMessageB.getText().toString());

        addressB.setText("123 Test Street");
        assertNotNull(addressB);
        assertEquals("123 Test Street", addressB.getText().toString());
        registerButton.performClick();
        assertEquals("Please enter a phone number", errorMessageB.getText().toString());

        phoneNumberB.setText("123");
        assertNotNull(phoneNumberB);
        assertEquals("123", phoneNumberB.getText().toString());
        registerButton.performClick();
        assertEquals("Please enter a name for the clinic", errorMessageB.getText().toString());

        descriptionB.setText("Testing Testing 123 !@#$%");
        assertNotNull(descriptionB);
        assertEquals("Testing Testing 123 !@#$%",descriptionB.getText().toString());
        registerButton.performClick();
        assertEquals("Please enter a name for the clinic", errorMessageB.getText().toString());

        companyNameB.setText("Test Clinic");
        assertNotNull(companyNameB);
        assertEquals("Test Clinic",companyNameB.getText().toString());

        //registerButton.performClick();

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