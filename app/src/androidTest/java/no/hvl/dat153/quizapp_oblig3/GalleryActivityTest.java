package no.hvl.dat153.quizapp_oblig3;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    @Rule
    public ActivityScenarioRule<GalleryActivity> activityScenarioRule = new ActivityScenarioRule<>(GalleryActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testImageAdded() {
        // Hent konteksten fra InstrumentationRegistry
        Context context = ApplicationProvider.getApplicationContext();

        ActivityScenario<GalleryActivity> scenario = activityScenarioRule.getScenario();


        scenario.onActivity(activity -> {

            if (!activity.getImageList().isEmpty()) {

                int initialImageCount = activity.getImageList().size();

                // Hent URI til bildet fra drawable-mappen
                Uri imageUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.giraffe);

                // Simuler legge til et bilde
                String description = "Giraffe";
                activity.addImage(imageUri, description);

                int finalImageCount = activity.getImageList().size();

                // Verifiser at antall bilder har økt med 1
                assertEquals(initialImageCount + 1, finalImageCount);
            }
        });
    }


    @Test
    public void testImageDeleted() {
        ActivityScenario<GalleryActivity> scenario = activityScenarioRule.getScenario();

        scenario.onActivity(activity -> {
            // Hvis det er bilder i galleriet
            if (!activity.getImageList().isEmpty()) {
                // Lagre antall bilder før slettingen
                int initialImageCount = activity.getImageList().size();

                // Simuler at et bilde slettes
                ImageEntity imageToDelete = activity.getImageList().get(0); // Slett det første bildet for testformål
                activity.deleteImage(imageToDelete);

                // Lagre antall bilder etter slettingen
                int finalImageCount = activity.getImageList().size();

                // Verifiser at antall bilder har blitt redusert med 1
                assertEquals(initialImageCount - 1, finalImageCount);
            }
        });
    }
}
