package walkinclinic.com.walkinclinic;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ReviewServiceProviderTest {

    @Rule
    public ActivityTestRule<ReviewServiceProvider> myActivityTestRule = new ActivityTestRule(ReviewServiceProvider.class);
    private ReviewServiceProvider myActivity = null;

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
        //Verifies user cannot enter invalid review and that the correct error message appears to aid user experience
        Button submit;
        TextView ratingText;
        TextView reviewText;
        TextView errorMessage;

        submit = myActivity.findViewById(R.id.submitB);
        ratingText = myActivity.findViewById(R.id.ratingT);
        reviewText = myActivity.findViewById(R.id.reviewT);
        errorMessage = myActivity.findViewById(R.id.errorMessage);

        //Tests incomplete form
        submit.performClick();
        assertEquals("Please make sure both fields are filled", errorMessage.getText().toString());

        ratingText.setText("5");
        assertEquals("5",ratingText.getText().toString());
        submit.performClick();
        assertEquals("Please enter a review", errorMessage.getText().toString());

        ratingText.setText("");
        reviewText.setText("Test");
        assertEquals("Test",reviewText.getText().toString());
        submit.performClick();
        assertEquals("Please enter a rating", errorMessage.getText().toString());
    }
}