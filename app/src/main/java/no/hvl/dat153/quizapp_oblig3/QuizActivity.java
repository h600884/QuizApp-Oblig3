package no.hvl.dat153.quizapp_oblig3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ImageView quizImageView;
    private TextView scoreTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private List<ImageEntity> imageList;
    private int currentQuestionIndex;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Endrer layouten ut i fra rotasjonen på telefonen
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_quiz_landscape);
        } else {
            setContentView(R.layout.activity_quiz);
        }

        quizImageView = findViewById(R.id.quizImage);
        scoreTextView = findViewById(R.id.scoreText);
        option1Button = findViewById(R.id.button1);
        option2Button = findViewById(R.id.button2);
        option3Button = findViewById(R.id.button3);

        // Opprett ViewModel
        ImageViewModel imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        // Lytt etter endringer i listen med bilder fra ViewModel
        imageViewModel.getAllImages().observe(this, images -> {
            imageList = images;

            // Gjør at bildene kommer i tilfeldig rekkefølge
            shuffleImageList();

            // Sett opp første spørsmål når bildene er lastet
            currentQuestionIndex = 0;
            setQuestion();
        });

        // Lyttere for valgknapper
        option1Button.setOnClickListener(v -> checkAnswer(option1Button.getText().toString()));

        option2Button.setOnClickListener(v -> checkAnswer(option2Button.getText().toString()));

        option3Button.setOnClickListener(v -> checkAnswer(option3Button.getText().toString()));
    }

    private void shuffleImageList() {
        // Sjekker at listen ikke er tom, dersom den inneholder bilder blir de shufflet
        if (imageList != null && !imageList.isEmpty()) {
            Collections.shuffle(imageList);
        }
    }

    private void setQuestion() {
        // Sjekk om bildelisten er tom
        if (imageList == null || imageList.isEmpty()) {
            return;
        }

        // Vis bildet for nåværende spørsmål
        ImageEntity currentImage = imageList.get(currentQuestionIndex);
        quizImageView.setImageURI(currentImage.getImageUri());

        // Lager en liste av alternativer, starter med det riktige svaret
        List<String> options = new ArrayList<>();
        options.add(currentImage.getImageDescription());

        // Kopierer og shuffler imageList for å sikre tilfeldighet
        List<ImageEntity> shuffledList = new ArrayList<>(imageList);
        Collections.shuffle(shuffledList);

        // Lager en liste av unike svar, eksludert det nåværende bilde
        List<String> otherDescriptions = new ArrayList<>();
        for (ImageEntity image : shuffledList) {
            if (!image.getImageDescription().equals(currentImage.getImageDescription())) {
                otherDescriptions.add(image.getImageDescription());
            }
        }

        // Shuffler listen av andre beksrivelser og velger de to første som tilfeldige svar
        Collections.shuffle(otherDescriptions);
        options.add(otherDescriptions.get(0));
        options.add(otherDescriptions.get(1));

        // Shuffler endelige listen med alternativer for å tilfeldiggjøre rekkefølgen
        Collections.shuffle(options);

        // Viser alternativene på knappene
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
    }

    private void checkAnswer(String selectedAnswer) {
        String correctAnswer = imageList.get(currentQuestionIndex).getImageDescription();
        String feedback;
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            feedback = "Correct!";
        } else {
            feedback = "Incorrect. Correct answer is: " + correctAnswer;
        }
        Toast.makeText(this, feedback, Toast.LENGTH_SHORT).show();

        // Oppdater poengsummen
        setScoreTextView(score, currentQuestionIndex + 1);

        // Gå til neste spørsmål eller avslutt quizen hvis alle spørsmålene er besvart
        currentQuestionIndex++;
        if (currentQuestionIndex < imageList.size()) {
            setQuestion();
        } else {
            // Avslutt quizen
            finishQuiz();
        }
    }

    private void setScoreTextView(int score, int totalQuestions) {
        // Oppdater poengsummen
        String updatedScore = "Score: " + score + " of " + (totalQuestions);
        scoreTextView.setText(updatedScore);
    }

    private void finishQuiz() {
        // Når en bruker har gått igjennom alle bildene vil du få en pop-up boks som sier hvordan brukeren gjorde det i quizen
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Finished");
        builder.setMessage("Your score: " + score + " of " + currentQuestionIndex);
        builder.setPositiveButton("OK", (dialog, which) -> {
            finish();
        });
        builder.setCancelable(false);
        builder.show();
    }
}
