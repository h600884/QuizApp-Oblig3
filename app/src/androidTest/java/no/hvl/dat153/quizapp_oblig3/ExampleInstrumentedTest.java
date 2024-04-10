package no.hvl.dat153.quizapp_oblig3;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void galleryButtonOpensGalleryActivity() {
        onView(withId(R.id.gallery_button)).perform(click());
        onView(withId(R.id.recycler_view_gallery)).check(matches(isDisplayed()));
    }

    @Test
    public void scoreIsUpdatedCorrectly() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), QuizActivity.class);

        ActivityScenario.launch(intent);

        // Click the first option (which is always the correct answer)
        onView(withId(R.id.button1)).perform(click());

        // Check if the score text view is displayed
        onView(withId(R.id.scoreText)).check(matches(isDisplayed()));

        // Check if the score text view contains the correct score
        onView(withId(R.id.scoreText)).check(matches(ViewMatchers.withText("Score: 1 of 1")));
    }

}