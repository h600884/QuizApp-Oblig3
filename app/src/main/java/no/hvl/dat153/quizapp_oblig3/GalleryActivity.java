package no.hvl.dat153.quizapp_oblig3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickImageLauncher;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<ImageEntity> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Initialiser RecyclerView
        recyclerView = findViewById(R.id.recycler_view_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Opprett en tom liste for bilder
        imageList = new ArrayList<>();

        // Opprett adapteren og koble den til RecyclerView
        imageAdapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(imageAdapter);

        // Legg til eksempelbilder (du kan erstatte dette med din egen logikk for å legge til bilder)
        addSampleImages();

        // Legg til funksjonalitet for å legge til nye oppføringer når brukeren trykker på "Add" -knappen
        Button addButton = findViewById(R.id.addbutton);
        addButton.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        // Legg til funksjonalitet for å sortere oppføringene når brukeren trykker på "Sort" -knappen
        Button sortButton = findViewById(R.id.sortbutton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sorter oppføringene
                Collections.sort(imageList, Comparator.comparing(ImageEntity::getImageDescription));
                // Oppdater RecyclerView etter sorteringen
                imageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addSampleImages() {
        // Legg til eksempelbilder (du kan erstatte dette med din egen logikk for å legge til bilder)
        imageList.add(new ImageEntity("Beer mug", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.beer_image)));
        imageList.add(new ImageEntity("Sheep", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.sheep_image)));
        imageList.add(new ImageEntity("Tree", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.tree_image)));

        // Oppdater RecyclerView etter å ha lagt til nye oppføringer
        imageAdapter.notifyDataSetChanged();
    }

}

