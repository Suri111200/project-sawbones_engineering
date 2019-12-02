package walkinclinic.com.walkinclinic;

import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.annotation.UiThreadTest;

import android.content.Intent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ServicesSPTest {


    ListView allServices;
    ListView myServices;

    @Rule
    public ActivityTestRule<ServicesSP> myActivityTestRule = new ActivityTestRule(ServicesSP.class);
    private ServicesSP myActivity = null;

    @Before
    public void setUp() throws Exception {
       // Intent intent =
        myActivity = myActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @UiThreadTest
    public void testLaunch() {

        allServices = myActivity.findViewById(R.id.servicesList);
        myServices = myActivity.findViewById(R.id.yourServicesList);
        assertNotNull(myActivity);
        assertNotNull(allServices);




    }
}
