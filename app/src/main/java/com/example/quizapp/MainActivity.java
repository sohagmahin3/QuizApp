package com.example.quizapp;

import androidx.annotation.NonNull;
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

    private final String SCORE_KEY = "SCORE";
    private final String INDEX_KEY = "INDEX";
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

        if(savedInstanceState!=null){
            userScore = savedInstanceState.getInt(SCORE_KEY);
            quizQuestionIndex = savedInstanceState.getInt(INDEX_KEY);
        }else {
            userScore = 0;
            quizQuestionIndex= 0;
        }

        Toast.makeText(getApplicationContext(),"OnCreate method start",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        btnTrue = findViewById(R.id.btnTrue);
        btnWrong = findViewById(R.id.btnWrong);
        txtQuestionView = findViewById(R.id.txtQuestion);

        QuizModel q1 = questionCollections[quizQuestionIndex];
        mQuestion = q1.mQuestion;

        txtQuestionView.setText(mQuestion);

        progressBar = findViewById(R.id.quizPB);
        txtStatsView = findViewById(R.id.txtQuizStats);
        txtStatsView.setText(userScore+"");

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

    @Override
    protected void onStart() {
        //it called after onCreate method
        super.onStart();
        Toast.makeText(getApplicationContext(),"OnStart method start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        //it called after onStart Method
        super.onResume();
        Toast.makeText(getApplicationContext(),"OnResume method start",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        //it called when user press the recent button
        //In recent memu app is visible but not intarect to the user. so it called onPause
        super.onPause();
        Toast.makeText(getApplicationContext(),"OnPause method start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        //it called when user Pressed home button
        //also it called when user Pressed recent button
        //it need when we play video in the app .Then if user can pressed home button that time we need to pause or stop the video in the app.Otherwise it will running background
        super.onStop();
        Toast.makeText(getApplicationContext(),"OnStop method start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        //it called when we close our application
        //when close the application also called onPause,onStop method
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"OnDestroy method start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        //এই মেথড টাকে ব্যবহার করি তখন যখন আমাদের Portrait mode thaka landscape mode যাবো তখন Portrait থাকাকালিন সব ডাটা সেভ করে রাখার জন্য। কারন landscape গেলে পুরো App টা আবার নতুন করে শুরু
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY,userScore);
        outState.putInt(INDEX_KEY,quizQuestionIndex);
    }
}
