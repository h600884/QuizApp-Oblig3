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
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void launchQuizActivity() {
        onView(withId(R.id.quiz_button)).perform(click());
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testCorrectAnswerAndScore() {
        final int[] correctIdButton = new int[1];
        ActivityScenario<QuizActivity> quizActivityScenario = ActivityScenario.launch(QuizActivity.class);
        quizActivityScenario.onActivity(activity -> {
            correctIdButton[0] = activity.getCorrectButton();
        });

        // Click the correct button
        onView(withId(correctIdButton[0])).perform(click());
        onView(withId(R.id.scoreText)).check(matches(withText("Score: 1 of 1")));

        // Assuming the activity stays the same and doesn't reset or recreate
        quizActivityScenario.onActivity(activity -> {
            correctIdButton[0] = activity.getCorrectButton();  // Get new correct button
        });

        // Click an incorrect button
        int incorrectButtonId = correctIdButton[0] == R.id.button1 ? R.id.button2 : R.id.button1;
        onView(withId(incorrectButtonId)).perform(click());
        onView(withId(R.id.scoreText)).check(matches(withText("Score: 1 of 2")));

        quizActivityScenario.close();
    }

}
