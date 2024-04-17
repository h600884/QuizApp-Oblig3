package no.hvl.dat153.quizapp_oblig3;

import static org.junit.Assert.assertEquals;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityScenarioRule = new ActivityScenarioRule<>(QuizActivity.class);

    @Before
    public void setUp() {
        Intents.init();
        // Starter opp og trykker på første knapp i quizen før testen begynner
        activityScenarioRule.getScenario().onActivity(QuizActivity::clickFirstAnswer);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void scoreIsUpdatedCorrectly() {
        // Hent poengsummen etter at knappen er trykket
        activityScenarioRule.getScenario().onActivity(activity -> {
            String scoreText = activity.getScoreText();
            assertEquals("Score: 0 of 3", scoreText);
        });
    }
}
