package no.hvl.dat153.quizapp_oblig3;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public void deletingImageIsRemovedFromDb() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.clickGalleryButton();
            assertTrue(activity.isGalleryActivityStarted());
            activity.clickOnImage("Tree");
            assertTrue(activity.isImageDeleted("Tree"));
        });
    }

    @Test
    public void testAddingImageToDb() {
        // Kjører GalleryActivity
        ActivityScenario<GalleryActivity> scenario = activityScenarioRule.getScenario();

        // Lager en AtomicBoolean for å sjekke om bildet er lagt til
        AtomicBoolean isImageAdded = new AtomicBoolean(false);

        // Henter aktivitetsobjektet og utfører handlinger
        scenario.onActivity(activity -> {
            // Klikker på legg til bilde-knappen
            activity.clickAddButton();

            // Klikker på bekreft-knappen
            activity.clickConfirmButton();

            // Sjekker om bildet er lagt til
            isImageAdded.set(activity.isImageAdded());
        });

        // Sjekker om bildet ble lagt til i databasen
        assertTrue("Image should be added to database", isImageAdded.get());
    }
}
