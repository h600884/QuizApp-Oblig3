package no.hvl.dat153.quizapp_oblig3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

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
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }
}
