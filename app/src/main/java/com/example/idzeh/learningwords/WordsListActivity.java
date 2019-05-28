package com.example.idzeh.learningwords;
/*
класс для отображения списка слов
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.*;

public class WordsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);
        Bundle b = getIntent().getExtras();
        final int topicId = b.getInt("topic_id");
        ArrayList<Word> words = DB.getWordsByParentId(this, topicId);
        ListView wordsListView = (ListView) findViewById(R.id.words_list_view);
        Button addWordButton = (Button) findViewById(R.id.addWordButton);
        Button toTopicButton = (Button) findViewById(R.id.backToTopicButton);

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsListActivity.this, WordSettingsActivity.class);
                Bundle b = new Bundle();
                b.putInt("word_id", -1);
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });

        toTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WordsAdapter wordsAdapter = new WordsAdapter(getApplicationContext(), R.layout.activity_topic_settings, words);
        wordsListView.setAdapter(wordsAdapter);
        wordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WordsListActivity.this, WordSettingsActivity.class);
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
