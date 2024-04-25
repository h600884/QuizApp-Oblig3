package no.hvl.dat153.quizapp_oblig3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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
    private String correctAnswer;

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
            Collections.shuffle(imageList);

            // Sett opp første spørsmål når bildene er lastet
            currentQuestionIndex = 0;
            setQuestion();

            if (imageList != null) {
                setScoreTextView(0, currentQuestionIndex);
            }
        });
        // Lyttere for valgknapper
        option1Button.setOnClickListener(v -> checkAnswer(option1Button.getText().toString()));
        option2Button.setOnClickListener(v -> checkAnswer(option2Button.getText().toString()));
        option3Button.setOnClickListener(v -> checkAnswer(option3Button.getText().toString()));
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
        correctAnswer = currentImage.getImageDescription();
        options.add(currentImage.getImageDescription());

        // Legg til de to første alternativene som IKKE er riktig svar.
        // break når det er lagt til 2 andre alternativer
        int addedDescriptions = 0;
        for (ImageEntity image : imageList) {
            if (!image.getImageDescription().equals(currentImage.getImageDescription())) {
                options.add(image.getImageDescription());
                addedDescriptions++;
            }
            if (addedDescriptions == 2) { break; }
        }
        // Shuffler endelige listen med alternativer for å tilfeldiggjøre rekkefølgen
        Collections.shuffle(options);

        // Viser alternativene på knappene
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
    }

    private void checkAnswer(String selectedAnswer) {
        // Sjekk om imageList er tom eller currentQuestionIndex er utenfor grensene til imageList
        if (imageList.isEmpty() || currentQuestionIndex < 0 || currentQuestionIndex >= imageList.size()) {
            // Håndter tilfellet der imageList er tom eller currentQuestionIndex er ugyldig
            Toast.makeText(this, "Error: No questions available", Toast.LENGTH_SHORT).show();
            return;
        }

        String correctAnswer = imageList.get(currentQuestionIndex).getImageDescription();
        String feedback;
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            feedback = "Correct!";
        } else {
            feedback = "Incorrect. Correct answer is: " + correctAnswer;
        }
        Toast.makeText(this, feedback, Toast.LENGTH_SHORT).show();

        currentQuestionIndex++;

        // Oppdater poengsummen
        setScoreTextView(score, currentQuestionIndex);

        // Gå til neste spørsmål eller avslutt quizen hvis alle spørsmålene er besvart

        if (currentQuestionIndex < imageList.size()) {
            setQuestion();
        } else {
            // Avslutt quizen
            finishQuiz();
        }
    }

    private void setScoreTextView(Integer score, Integer currentQuestionIndex) {
        // Sjekk om imageList er initialisert og har data
        if (imageList != null && !imageList.isEmpty() && score != null && currentQuestionIndex != null) {
            String scoreText = "Score: " + score + " of " + currentQuestionIndex;
            scoreTextView.setText(scoreText);
        }
    }

    private void finishQuiz() {
        // Når en bruker har gått igjennom alle bildene vil du få en pop-up boks som sier hvordan brukeren gjorde det i quizen
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Finished");
        builder.setMessage("Your score: " + score + " of " + currentQuestionIndex);
        builder.setPositiveButton("OK", (dialog, which) -> finish());
        builder.setCancelable(false);
        builder.show();
    }

    public int getCorrectButton() {
        if (correctAnswer == null) {
            Log.e("QuizActivity", "Correct answer is not initialized yet.");
            return -1;
        }

        if (correctAnswer.equals(option1Button.getText().toString())) {
            return R.id.button1;
        } else if (correctAnswer.equals(option2Button.getText().toString())) {
            return R.id.button2;
        } else if (correctAnswer.equals(option3Button.getText().toString())) {
            return R.id.button3;
        }

        return -1;
    }}
