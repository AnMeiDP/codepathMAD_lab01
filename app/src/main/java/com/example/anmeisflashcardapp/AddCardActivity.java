package com.example.anmeisflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.cancel_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputQuestion = ((EditText) findViewById(R.id.flashcard_question_edittext)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.flashcard_answer_edittext)).getText().toString();

                if (inputQuestion.matches("") || inputAnswer.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Must answer both question and answer!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    Intent data = new Intent();
                    data.putExtra("QUESTION_KEY", inputQuestion);
                    data.putExtra("ANSWER_KEY", inputAnswer);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}