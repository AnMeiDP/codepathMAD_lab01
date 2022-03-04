package com.example.anmeisflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      the line below bridges the xml with activity (super important to have)
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            // can use Toast to check if your OnClickListener is working
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_textview).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question_textview).setVisibility(View.INVISIBLE);
            }
        });

        TextView flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            // can use Toast to check if your OnClickListener is working
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_textview).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question_textview).setVisibility(View.VISIBLE);
            }
        });

        TextView possibleAnswerOne = findViewById(R.id.possible_answer_one_textView);
        TextView possibleAnswerTwo = findViewById(R.id.possible_answer_two_textView);
        TextView correctAnswer = findViewById(R.id.possible_answer_three_textView);

        possibleAnswerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.possible_answer_one_textView).setBackgroundColor(getResources().getColor(R.color.red, null));
                findViewById(R.id.possible_answer_three_textView).setBackgroundColor(getResources().getColor(R.color.green, null));
            }
        });

        possibleAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.possible_answer_two_textView).setBackgroundColor(getResources().getColor(R.color.red, null));
                findViewById(R.id.possible_answer_three_textView).setBackgroundColor(getResources().getColor(R.color.green, null));
            }
        });

        correctAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.possible_answer_three_textView).setBackgroundColor(getResources().getColor(R.color.green, null));
            }
        });

//      implementing toggle functionality, create an object

    }
}
