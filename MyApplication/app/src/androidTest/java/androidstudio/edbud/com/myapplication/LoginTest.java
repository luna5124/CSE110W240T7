package androidstudio.edbud.com.myapplication;

/**
 * Created by paulszh on 3/5/16.
 */
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import login.Login;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<Login> activityRule = new ActivityTestRule(Login.class);


    @Test
    //Given the user has logged in and they are at the appointment page
    //attention: because we are given user has login in, you need to login inorder to test
    //username: tester1  password: 123456
    public void initialState(){
        Log.v("ENTER", "TEST");
        onView(withId(R.id.EmailOption)).check(matches(withText("Email")));
        onView(withId(R.id.PasswordOption)).check(matches(withText("Password")));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.tvRegisterLink)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
    }
    @Test
    public void testRegisterAndLogin(){

        onView(withId(R.id.tvRegisterLink))
                .perform(click());
        onView(withId(R.id.etEmail)).perform(typeText("test2@ucsd.edu"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"));
        onView(withId(R.id.etName)).perform(typeText("newUser"));
        onView(withId(R.id.etCollege)).perform(typeText("sixth"));
        onView(withId(R.id.etMajor)).perform(typeText("Computer science"));
        onView(withId(R.id.etGPA)).perform(typeText("3.67"));
        onView(withId(R.id.etUnit)).perform(typeText("156"));
        onView(withId(R.id.etCurrentTerm)).perform(typeText("Winter 2016"));

        //check if the button is clickable
        onView(withId(R.id.bRegister)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.bRegister)).perform(click());

        /*onView(withId(R.id.etEmail)).perform(typeText("test@ucsd.edu"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.bLogin)).perform(click());
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.GPAtext))
                .check(matches(withText("GPA")));
        onView(withId(R.id.GPAnumber))
                .check(matches(withText("3.67")));*/
        //onView(withId(R.id.nav_view)).perform(click());
        /*onView(withId(R.id.nav_logout)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.nav_logout)).perform(click());*/




    }

}
