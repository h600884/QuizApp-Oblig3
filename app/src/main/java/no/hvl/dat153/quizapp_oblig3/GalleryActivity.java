package no.hvl.dat153.quizapp_oblig3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ImageAdapter imageAdapter;
    private List<ImageEntity> imageList;
    private ImageViewModel imageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Endrer layouten ut i fra rotasjonen på telefonen
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_gallery_landscape);
        } else {
            setContentView(R.layout.activity_gallery);
        }
        // Initialiser RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setVisibility(View.GONE);

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @SuppressLint("WrongConstant")
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();

                    if (intent != null) {

                        Uri selectedImageUri = intent.getData();
                        if (selectedImageUri != null) {
                            int takeFlags = intent.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                            EditText editText = findViewById(R.id.textinput);
                            String imageText = editText.getText().toString();

                            ImageEntity image = new ImageEntity(imageText, selectedImageUri);
                            imageViewModel.insert(image);

                        }
                    }
                }
            }
        });
        // Opprett ImageViewModel
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        // Observer for å oppdatere imageList når databasen endres
        imageViewModel.getAllImages().observe(this, images -> {
            if (images != null) {
                imageList.clear(); // Fjern eksisterende bilder fra imageList
                imageList.addAll(images); // Legg til alle bildene fra databasen i imageList
                imageAdapter.notifyDataSetChanged(); // Oppdater RecyclerView
            }
        });

        // Opprett en tom liste for bilder
        imageList = new ArrayList<>();

        // Opprett adapteren og koble den til RecyclerView
        imageAdapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(imageAdapter);

        // Legg til bilder
        Button addButton = findViewById(R.id.addbutton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        // Sortere listen med bildene etter alfabetisk-rekkefølge
        Button sortAlphabeticalButton = findViewById(R.id.sortalphabeticalbutton);
        sortAlphabeticalButton.setOnClickListener(v -> {
            // Sorter bildene
            imageList.sort((o1, o2) -> o1.getImageDescription().compareToIgnoreCase(o2.getImageDescription()));
            // Oppdater RecyclerView etter sorteringen
            imageAdapter.notifyDataSetChanged();
        });

        // Sortere listen med bildene etter motsatt alfabetisk-rekkefølge
        Button sortUnalphabeticalButton = findViewById(R.id.sortunalphabeticalbutton);
        sortUnalphabeticalButton.setOnClickListener(v -> {
            // Sorter bildene
            imageList.sort((o1, o2) -> o2.getImageDescription().compareToIgnoreCase(o1.getImageDescription()));
            // Oppdater RecyclerView etter sorteringen
            imageAdapter.notifyDataSetChanged();
        });

        // Lytter for å slette bilder
        imageAdapter.setOnItemClickListener(image -> {
            imageViewModel.deleteWithId(image.getId());
        });

    }
}
