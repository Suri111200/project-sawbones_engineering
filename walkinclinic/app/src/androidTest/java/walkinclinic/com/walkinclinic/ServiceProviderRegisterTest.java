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
public class ServiceProviderRegisterTest {

    @Rule
    public ActivityTestRule<ServiceProviderRegister> myActivityTestRule = new ActivityTestRule(ServiceProviderRegister.class);
    private ServiceProviderRegister myActivity = null;

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
        EditText address;
        address = myActivity.findViewById(R.id.address);
        EditText companyName;
        companyName = myActivity.findViewById(R.id.companyName);
        EditText phoneNumber;
        phoneNumber = myActivity.findViewById(R.id.phoneNumber);
        EditText description;
        description = myActivity.findViewById(R.id.description);
        TextView errorMessage;
        Button register = myActivity.findViewById(R.id.registerB);

        //Tests incomplete form
        address.setText("123 Jane Street");
        assertEquals("123 Jane Street", address.getText().toString());
        companyName.setText("A big company");
        assertEquals("A big company", companyName.getText().toString());
        assertEquals("", phoneNumber.getText().toString());
        assertEquals("", description.getText().toString());

        register.performClick();
        errorMessage = myActivity.findViewById(R.id.errorMessage);
        assertEquals("Make sure all fields are filled in.", errorMessage.getText().toString());


    }


}