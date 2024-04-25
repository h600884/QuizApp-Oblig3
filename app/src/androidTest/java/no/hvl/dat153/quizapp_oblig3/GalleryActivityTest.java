package no.hvl.dat153.quizapp_oblig3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.atomic.AtomicInteger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    public ActivityScenario<GalleryActivity> activityScenario;

    @Test
    public void testImageAdded() {

        Intents.init();

        activityScenario = ActivityScenario.launch(GalleryActivity.class);

        AtomicInteger atomicBefore = new AtomicInteger(-1);

        activityScenario.onActivity(activity -> {
            // Observer LiveData for å få antall bilder før handlingen
            activity.getAllQuizImages().observe(activity, quizImages -> atomicBefore.set(quizImages.size()));
        });

        int before = atomicBefore.get();

                // Gjennomfører handlingen for å legge til et bilde
                    onView(withId(R.id.addbutton)).perform(click());

                    // Simulerer valg av bilde fra galleriet
                    // Setter opp en intent som returnerer et bilde
                    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Uri imageUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.giraffe);
                    Intent galleryIntent = new Intent();
                    galleryIntent.setData(imageUri);
                    Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, galleryIntent);
                    intending(hasAction(Intent.ACTION_PICK)).respondWith(result);

                    // Fyller ut skjemaet og bekrefter handlingen
                    onView(withId(R.id.textinput)).perform(replaceText("Giraffe"));
                    onView(withId(R.id.confirm_button)).perform(click());

                    AtomicInteger atomicAfter = new AtomicInteger(-1);


        activityScenario.onActivity(activity -> {
            // Observer LiveData for å få antall bilder etter handlingen
                activity.getAllQuizImages().observe(activity, quizImages -> atomicAfter.set(quizImages.size()));
        });
        int after = atomicAfter.get();

        Intents.release();

        assertEquals(before + 1, after);
    }

  @Test
  public void testImageDeleted() {

      Intents.init();

      activityScenario = ActivityScenario.launch(GalleryActivity.class);

      AtomicInteger atomicBefore = new AtomicInteger(-1);

      activityScenario.onActivity(activity -> {
          activity.getAllQuizImages().observe(activity, quizImages -> atomicBefore.set(quizImages.size()));
      });

      int before = atomicBefore.get();

      // Klikk på det første bildet i RecyclerView for å utløse bekreftelsesdialogen
              onView(withId(R.id.recycler_view_gallery))
                      .perform(actionOnItemAtPosition(0, click()));

              // Forventer at dialogen vises
              onView(withText("Are you sure you want to delete this image?"))
                      .inRoot(isDialog())
                      .check(matches(isDisplayed()));

              // Klikk på bekreftelsesknappen ("Delete") i dialogen
              onView(withText("Delete"))
                      .inRoot(isDialog())
                      .check(matches(isDisplayed()))
                      .perform(click());

                AtomicInteger atomicAfter = new AtomicInteger(-1);

      activityScenario.onActivity(activity -> {
          // Observer LiveData for å få antall bilder etter handlingen
          activity.getAllQuizImages().observe(activity, quizImages -> atomicAfter.set(quizImages.size()));
      });

      int after = atomicAfter.get();

      Intents.release();

      assertEquals(before - 1, after);
  }
}
