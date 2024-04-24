package no.hvl.dat153.quizapp_oblig3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityScenarioRule = new ActivityScenarioRule<>(QuizActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testQuizActivity() {
        Intent Quizintent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), QuizActivity.class);
        ActivityScenario<QuizActivity> QuizActivityScenario = ActivityScenario.launch(Quizintent);

        onView(isRoot()).perform(waitFor(500)); // Custom method to wait for 500 milliseconds

        final int[] correctNameButton = new int[1];
        QuizActivityScenario.onActivity(activity -> {
            correctNameButton[0] = activity.getCorrectButton();
        });

        if (correctNameButton[0] == R.id.button1) {
            onView(withId(R.id.button1)).perform(click());
        } else if (correctNameButton[0] == R.id.button2) {
            onView(withId(R.id.button2)).perform(click());
        } else if (correctNameButton[0] == R.id.button3) {
            onView(withId(R.id.button3)).perform(click());
        } else {
            Log.i("Error", "No correct button ID found");
        }

        onView(withId(R.id.scoreText)).check(matches(withText("Score: 1 of 1")));

        QuizActivityScenario.onActivity(activity -> {
            correctNameButton[0] = activity.getCorrectButton();
        });

        if (correctNameButton[0] == R.id.button1) {
            onView(withId(R.id.button2)).perform(click());
        } else if (correctNameButton[0] == R.id.button2) {
            onView(withId(R.id.button3)).perform(click());
        } else {
            onView(withId(R.id.button1)).perform(click());
        }
        onView(withId(R.id.scoreText)).check(matches(withText("Score: 1 of 2")));

        QuizActivityScenario.close();
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }
            @Override
            public String getDescription() {
                return "wait for " + millis + " milliseconds";
            }
            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}
