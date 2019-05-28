package com.example.idzeh.learningwords;
/*
класс для редактирования топика
 */
import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class TopicSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_settings);
        Bundle b = getIntent().getExtras();
        final int topicId = b.getInt("topic_id");
        final TextView topicNameInput = (TextView) findViewById(R.id.topic_name_input);
        topicNameInput.setText(DB.getTopicNameById(this, topicId));
        topicNameInput.setSelected(false);


        Button toMainButton = (Button) findViewById(R.id.toMainButton);
        Button deleteTopicButton = (Button) findViewById(R.id.deleteTopicButton);
        Button startTrainCardsButton = (Button) findViewById(R.id.startCardsButton);
        Button startExamButton = (Button) findViewById(R.id.startTestButton);
        Button wordsListButton = (Button) findViewById(R.id.toWordsListButton);

        final Context ctx = this;
        toMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.updateTopic(ctx, new Topic(topicId, topicNameInput.getText().toString()));
                Intent intent = new Intent(TopicSettingsActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        wordsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicSettingsActivity.this, WordsListActivity.class);
                Bundle b = new Bundle();
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        deleteTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.deleteTopic(ctx, topicId);
                Intent intent = new Intent(TopicSettingsActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        startTrainCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicSettingsActivity.this, TrainCardsActivity.class);
                Bundle b = new Bundle();
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });
        startExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicSettingsActivity.this, ExamActivity.class);
                Bundle b = new Bundle();
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });

    }
}
