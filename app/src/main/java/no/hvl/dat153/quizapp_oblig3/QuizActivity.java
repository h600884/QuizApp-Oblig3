package no.hvl.dat153.quizapp_oblig3;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private ImageView dogImageView;
    private TextView scoreTextView;
    private TextView correctAnswerOrNotTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;

    private List<ImageEntity> imageList;
    private int currentQuestionIndex;
    private int score;
    private ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dogImageView = findViewById(R.id.quizDogImage);
        scoreTextView = findViewById(R.id.scoreText);
        correctAnswerOrNotTextView = findViewById(R.id.correctAnswerOrNot);
        option1Button = findViewById(R.id.button);
        option2Button = findViewById(R.id.button2);
        option3Button = findViewById(R.id.button3);

        // Opprett en liste med bildedata
        imageList = new ArrayList<>();
        // Fyll inn bildedataen (du kan erstatte dette med din egen logikk for å hente bildedata)
        addSampleImages();

        // Opprett adapteren og koble den til RecyclerView
        imageAdapter = new ImageAdapter(this, imageList);

        // Sett opp første spørsmål
        currentQuestionIndex = 0;
        setQuestion();

        // Lyttere for valgknapper
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option1Button.getText().toString());
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option2Button.getText().toString());
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option3Button.getText().toString());
            }
        });
    }

    private void addSampleImages() {
        // Legg til eksempelbilder (du kan erstatte dette med din egen logikk for å hente bildedata)
        imageList.add(new ImageEntity("Beer mug", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.beer_image)));
        imageList.add(new ImageEntity("Sheep", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.sheep_image)));
        imageList.add(new ImageEntity("Tree", Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.tree_image)));
    }

    private void setQuestion() {
        // Vis bildet for nåværende spørsmål
        ImageEntity currentImage = imageList.get(currentQuestionIndex);
        dogImageView.setImageURI(currentImage.getImageUri());

        // Sett alternativene tilfeldig
        List<String> options = new ArrayList<>();
        options.add(currentImage.getImageDescription());
        options.add(getRandomOption());
        options.add(getRandomOption());
        Collections.shuffle(options);

        // Vis alternativene på knappene
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
    }

    private String getRandomOption() {
        Random random = new Random();
        ImageEntity randomImage = imageList.get(random.nextInt(imageList.size()));
        return randomImage.getImageDescription();
    }

    private void checkAnswer(String selectedAnswer) {
        String correctAnswer = imageList.get(currentQuestionIndex).getImageDescription();
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            correctAnswerOrNotTextView.setText("Correct!");
        } else {
            correctAnswerOrNotTextView.setText("Incorrect. Correct answer is: " + correctAnswer);
        }

        // Oppdater poengsummen
        scoreTextView.setText(String.valueOf(score));

        // Gå til neste spørsmål eller avslutt quizen hvis alle spørsmålene er besvart
        currentQuestionIndex++;
        if (currentQuestionIndex < imageList.size()) {
            setQuestion();
        } else {
            // Avslutt quizen
            finishQuiz();
        }
    }

    private void finishQuiz() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Finished");
        builder.setMessage("Your score: " + score);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Avslutt QuizActivity
                finish();
            }
        });
        builder.setCancelable(false); // Hindrer at brukeren lukker dialogboksen uten å trykke på OK
        builder.show();
    }
}
