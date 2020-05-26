package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnTrue;
    private Button btnWrong;
    private TextView txtQuestionView;
    private int quizQuestionIndex;
    private int mQuestion;
    private int userScore;
    private ProgressBar progressBar;
    private TextView txtStatsView;

    private QuizModel[] questionCollections = new QuizModel[]{
            new QuizModel(R.string.q1, true),
            new QuizModel(R.string.q2, false),
            new QuizModel(R.string.q3, true),
            new QuizModel(R.string.q4, false),
            new QuizModel(R.string.q5, true),
            new QuizModel(R.string.q6, false),
            new QuizModel(R.string.q7, true),
            new QuizModel(R.string.q8, false),
            new QuizModel(R.string.q9, true),
            new QuizModel(R.string.q10, false),
    };
    final int USER_PROGRESS = (int) Math.ceil((100.0 / questionCollections.length));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTrue = findViewById(R.id.btnTrue);
        btnWrong = findViewById(R.id.btnWrong);
        txtQuestionView = findViewById(R.id.txtQuestion);

        QuizModel q1 = questionCollections[quizQuestionIndex];
        mQuestion = q1.mQuestion;

        txtQuestionView.setText(mQuestion);

        progressBar = findViewById(R.id.quizPB);
        txtStatsView = findViewById(R.id.txtQuizStats);

        //True button
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(true);
                changeQuestion();
            }
        });

        //Wrong button
        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(false);
                changeQuestion();
            }
        });

    }

    void changeQuestion() {
        quizQuestionIndex = (quizQuestionIndex + 1) % 10;
        if(quizQuestionIndex == 0){
            AlertDialog.Builder finishedDialog = new AlertDialog.Builder(this);
            finishedDialog.setTitle("Your Quiz result");
            finishedDialog.setMessage("Your score is: "+userScore);
            finishedDialog.setCancelable(false);
            finishedDialog.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            finishedDialog.show();
        }
        mQuestion = questionCollections[quizQuestionIndex].getmQuestion();
        txtQuestionView.setText(mQuestion);
        progressBar.incrementProgressBy(USER_PROGRESS);
        txtStatsView.setText(userScore + "");
    }

    void evaluateUserAnswer(boolean userGuess) {
        boolean correctAnswer = questionCollections[quizQuestionIndex].ismAnswer();
        if (userGuess == correctAnswer) {
            userScore++;
            Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast_message, Toast.LENGTH_LONG).show();
        }
    }
}
