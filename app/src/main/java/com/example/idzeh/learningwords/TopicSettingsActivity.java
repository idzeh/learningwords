package com.example.idzeh.learningwords;

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
        ArrayList<Word> words = DB.getWordsByParentId(this, topicId);

        ListView wordsListView = (ListView) findViewById(R.id.words_list_view);
        Button toMainButton = (Button) findViewById(R.id.toMainButton);
        Button addWordButton = (Button) findViewById(R.id.addWordButton);
        Button deleteTopicButton = (Button) findViewById(R.id.deleteTopicButton);
        Button startTrainCardsButton = (Button) findViewById(R.id.startCardsButton);

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

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicSettingsActivity.this, WordSettingsActivity.class);
                Bundle b = new Bundle();
                b.putInt("word_id", -1);
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
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
        WordsAdapter wordsAdapter = new WordsAdapter(getApplicationContext(), R.layout.activity_topic_settings, words);
        wordsListView.setAdapter(wordsAdapter);
        wordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopicSettingsActivity.this, WordSettingsActivity.class);
                Bundle b = new Bundle();
                b.putInt("word_id", ((Word) parent.getItemAtPosition(position)).getId());
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });
    }
}
