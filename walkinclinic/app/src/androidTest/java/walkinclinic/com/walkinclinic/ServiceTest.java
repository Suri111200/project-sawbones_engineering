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
public class ServiceTest {

    @Rule
    public ActivityTestRule<ServicesAdmin> myActivityTestRule = new ActivityTestRule(ServicesAdmin.class);
    private ServicesAdmin myActivity = null;

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


        //Test adding of a service

        //Test updating a service
        
        //Test deleting a service

    }
}