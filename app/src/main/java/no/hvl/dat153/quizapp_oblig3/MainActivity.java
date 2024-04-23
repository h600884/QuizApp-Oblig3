package no.hvl.dat153.quizapp_oblig3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageRepository imageRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sett opp knappen for å åpne GalleryActivity
        Button galleryButton = findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(v -> openGalleryActivity());

        // Sett opp knappen for å åpne QuizActivity
        Button quizButton = findViewById(R.id.quiz_button);
        quizButton.setOnClickListener(v -> openQuizActivity());

        imageRepository = new ImageRepository(getApplication());

        //imageRepository.deleteAll();

        // Legger til standardbilder dersom en bruker har slettet alle bildene
        imageRepository.getAllImages().observe(this, images -> {
            if(images.isEmpty()) {
                    // Legg til standardbilder
                    imageRepository.insert(new ImageEntity("Beer mug", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.beer_image)));
                    imageRepository.insert(new ImageEntity("Sheep", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.sheep_image)));
                    imageRepository.insert(new ImageEntity("Tree", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.tree_image)));
            }
        });
    }

    // Metode for å åpne GalleryActivity
    private void openGalleryActivity() {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    // Metode for å åpne QuizActivity
    private void openQuizActivity() {
        // Sjekk antall bilder i galleriet
        imageRepository.getAllImages().observe(this, images -> {
            if (images.size() >= 3) {
                // Åpne QuizActivity hvis det er minst 3 bilder i galleriet
                Intent intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
            } else {
                // Gi en melding til brukeren om at det må være minst 3 bilder i galleriet
                Toast.makeText(this, "You need at least 3 images in the gallery to start the quiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
