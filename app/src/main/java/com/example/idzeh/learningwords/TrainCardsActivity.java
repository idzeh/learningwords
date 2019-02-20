package com.example.idzeh.learningwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TrainCardsActivity extends AppCompatActivity {
    ArrayList<Word> words;
    int topicId;
    int curWordInd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_cards);
        Bundle b = getIntent().getExtras();
        topicId = b.getInt("topic_id");
        curWordInd = -1;

        words = DB.getWordsByParentId(this, topicId);
        Collections.shuffle(words);
        showNextCard();

        Button translateButton = (Button) findViewById(R.id.buttonTranslateCard);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView russianTranslate = (TextView) findViewById(R.id.russianCardView);
                TextView englishTranslate = (TextView) findViewById(R.id.englishCardView);

                russianTranslate.setText("Russian: " + words.get(curWordInd).getRussian());
                englishTranslate.setText("English: " + words.get(curWordInd).getEnglish());
            }
        });

        Button nextButton = (Button) findViewById(R.id.buttonNextCard);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextCard();
            }
        });

        Button backButton = (Button) findViewById(R.id.backToTopicFromCardsButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainCardsActivity.this, TopicSettingsActivity.class);
                Bundle b = new Bundle();
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });
    }

    void showNextCard(){
        curWordInd++;
        if (words.size() == curWordInd){
            Collections.shuffle(words);
            curWordInd = 0;
        }
        TextView russianTranslate = (TextView) findViewById(R.id.russianCardView);
        TextView englishTranslate = (TextView) findViewById(R.id.englishCardView);
        Random rnd = new Random();
        if (rnd.nextInt()%2 == 0){
            russianTranslate.setText("Russian: " + words.get(curWordInd).getRussian());
            englishTranslate.setText("English: ");
        }else{
            russianTranslate.setText("Russian: ");
            englishTranslate.setText("English: " + words.get(curWordInd).getEnglish());
        }
    }
}
