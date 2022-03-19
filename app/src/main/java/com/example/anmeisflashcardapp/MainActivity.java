package com.example.anmeisflashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      the line below bridges the xml with activity (super important to have)
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            // can use Toast to check if your OnClickListener is working
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_textview).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question_textview).setVisibility(View.INVISIBLE);
            }
        });

        flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            // can use Toast to check if your OnClickListener is working
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_textview).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question_textview).setVisibility(View.VISIBLE);
            }
        });

//        TextView possibleAnswerOne = findViewById(R.id.possible_answer_one_textView);
//        TextView possibleAnswerTwo = findViewById(R.id.possible_answer_two_textView);
//        TextView correctAnswer = findViewById(R.id.possible_answer_three_textView);
//
//        possibleAnswerOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findViewById(R.id.possible_answer_one_textView).setBackgroundColor(getResources().getColor(R.color.red, null));
//                findViewById(R.id.possible_answer_three_textView).setBackgroundColor(getResources().getColor(R.color.green, null));
//            }
//        });
//
//        possibleAnswerTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findViewById(R.id.possible_answer_two_textView).setBackgroundColor(getResources().getColor(R.color.red, null));
//                findViewById(R.id.possible_answer_three_textView).setBackgroundColor(getResources().getColor(R.color.green, null));
//            }
//        });
//
//        correctAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findViewById(R.id.possible_answer_three_textView).setBackgroundColor(getResources().getColor(R.color.green, null));
//            }
//        });

        // does this need to go outside of oncreate?
        findViewById(R.id.add_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

//      implementing toggle functionality, create an object

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            // this 100 needs to match the 100 we used when we called startActivityForResult!
           if (data != null) {
              String questionString = data.getExtras().getString("QUESTION_KEY"); // 'string1' needs to match the key we used when we put the string in the Intent
              String answerString = data.getExtras().getString("ANSWER_KEY");
              flashcardQuestion.setText(questionString);
              flashcardAnswer.setText(answerString);
           }
        }
    }
}
