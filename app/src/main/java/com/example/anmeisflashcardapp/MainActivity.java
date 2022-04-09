package com.example.anmeisflashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;

    // must be a global variable because we need to access it in both classes in MA
    FlashcardDatabase flashcardDatabase;
    // list variable of all flashcards, so that we can flip through them?
    List<Flashcard> allFlashcards;
    int cardIndex = 0;

    // initializing timer
//    CountDownTimer countDownTimer;

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
                // simple instructions to flip between two cards
//                findViewById(R.id.flashcard_answer_textview).setVisibility(View.VISIBLE);
//                findViewById(R.id.flashcard_question_textview).setVisibility(View.INVISIBLE);

                View answerSideView = findViewById(R.id.flashcard_answer_textview);

                // get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);

                anim.setDuration(2000);
                anim.start();
            }
        });

        // to flip back to question view
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.edit_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
//                intent.putExtra("stringKey1", "harry potter");
//                intent.putExtra("stringKey2", "voldemort");
                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        // ensures that you get the most up to date list of flashcards
        allFlashcards = flashcardDatabase.getAllCards();

        // this code assumes that there are flashcards in the database, so we need to check if database empty
        if (allFlashcards != null && allFlashcards.size() > 0) {
            Flashcard firstCard = allFlashcards.get(0);
            flashcardQuestion.setText(firstCard.getQuestion());
            flashcardAnswer.setText(firstCard.getAnswer());
        }

        // implementing next button functionality
        // need to re-add the visibility of each card global variables!!
        // need to make sure that it goes to the next questionview rather than the next answerview
        findViewById(R.id.next_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {
                    return;
                }

                cardIndex += 1;

                if (cardIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            "Last flashcard reached! Returning to the start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    cardIndex = 0; // resets index
                }

                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                        findViewById(R.id.flashcard_answer_textview).setVisibility(View.INVISIBLE);
                        findViewById(R.id.flashcard_question_textview).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        findViewById(R.id.flashcard_question_textview).startAnimation(rightInAnim);

                        Flashcard currentCard = allFlashcards.get(cardIndex);
                        flashcardQuestion.setText(currentCard.getQuestion());
                        flashcardAnswer.setText(currentCard.getAnswer());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                findViewById(R.id.flashcard_question_textview).startAnimation(leftOutAnim);

            }
        });

        // countdown timer code, not sure where this goes
//        startTimer();
//        countDownTimer = new CountDownTimer(15000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//            }
//        };

        findViewById(R.id.delete_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question_textview)).getText().toString());

                allFlashcards = flashcardDatabase.getAllCards();

                if (allFlashcards == null || allFlashcards.size() == 0) {
                    flashcardQuestion.setText("Flashcard Deck Empty!");
                    flashcardAnswer.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "Add a card.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    Flashcard currentCard = allFlashcards.get(cardIndex - 1);
                    flashcardQuestion.setText(currentCard.getQuestion());
                    flashcardAnswer.setText(currentCard.getAnswer());
                }
            }
        });
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

              // saving new flashcard to database
              Flashcard flashcard = new Flashcard(questionString, answerString);
              flashcardDatabase.insertCard(flashcard);

              allFlashcards = flashcardDatabase.getAllCards();
           }
        }
    }

//    private void startTimer() {
//        countDownTimer.cancel();
//        countDownTimer.start();
//    }
}
