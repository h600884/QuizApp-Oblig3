package no.hvl.dat153.quizapp_oblig3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.hamcrest.CoreMatchers.allOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    // Sjekker at når du trykker på "Gallery"-knappen blir du sendt til galleriet og at recyclerView viser frem bildene
    @Test
    public void galleryButtonOpensGalleryActivity() {
        onView(withId(R.id.gallery_button)).perform(click());
        onView(withId(R.id.recycler_view_gallery)).check(matches(isDisplayed()));
    }

    // Sjekker at når du trykker på første svar i quizen så vil det vise om det var feil og at scoren forblir uendret mens runden går opp 1
    // Vil ikke alltid gå igjennom siden først knapp vil av og til være riktig svar for quizen sin del
    @Test
    public void scoreIsUpdatedCorrectly() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), QuizActivity.class);

        ActivityScenario.launch(intent);

        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.scoreText)).check(matches(isDisplayed()));
        onView(withId(R.id.scoreText)).check(matches(withText("Score: 0 of 1")));
    }

    // Sjekker at når du trykker på et bilde så blir bildet slettet
    @Test
    public void DeleteingImageIsRemovedFromDb() {
       /* Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GalleryActivity.class);

        ActivityScenario.launch(intent);*/
        onView(withId(R.id.gallery_button)).perform(click());
        onView(withId(R.id.recycler_view_gallery)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewName), withText("Tree"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewName), withText("Tree"))).perform(click());
        onView(allOf(withId(R.id.textViewName), withText("Tree"))).check(doesNotExist());
    }

    // Tester om at når det blir lagt til et bilde gjennom drawable mappen så vil den dukke opp i galleriet
    @Test
    public void AddingImageToDb() {
        Intent resultData = new Intent();
        Uri imageUri = Uri.parse("android.resource://no.hvl.dat153.quizapp_oblig3/" + R.drawable.giraffe);
        resultData.setData(imageUri);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GalleryActivity.class);
        ActivityScenario.launch(intent);

        onView(withId(R.id.textinput)).perform(typeText("Giraffe"), closeSoftKeyboard());
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(activityResult);

        onView(withId(R.id.addbutton)).perform(click());

        onView(withId(R.id.recycler_view_gallery)).check(matches(isDisplayed()));

    }
}
