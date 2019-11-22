package walkinclinic.com.walkinclinic;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class WelcomeTest {
    @Rule
    public ActivityTestRule<Welcome> myActivityTestRule = new ActivityTestRule(Welcome.class);
    private Welcome myActivity = null;
    Person user;
    Button buttonToService;
    TextView one;
    TextView two;

    @Before
    public void setUp() throws Exception {
        myActivity = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void testLaunch() {
        /** This doesn't actually do anything
         * I was going to verify that the welcome messages were correct
         * However, Welcome uses a user from another intent, or something
         * Test case doesn't work because Welcome is using a null user
         * Not sure how to pass a user to Welcome, seems a little complicated lol
         */



        Intent intent = myActivity.getIntent();
        user =(Person) intent.getSerializableExtra("Person");
        buttonToService = myActivity.findViewById(R.id.toProfile);
        one = myActivity.findViewById(R.id.textView);
        two = myActivity.findViewById(R.id.textView2);

        //verify display messages
        assertEquals("Welcome " + user.getName() + "!",one.getText().toString());
        assertEquals("You are logged-in as " + user.getClass().getSimpleName() + ".",two.getText().toString());

    }

    @After
    public void tearDown() throws Exception {
        myActivity = null;
    }
}