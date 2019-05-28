package com.example.idzeh.learningwords;
/*
класс для редактирования слова
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;

public class WordSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_settings);

        final Bundle b = getIntent().getExtras();

        final int wordId = b.getInt("word_id");
        final int topicId = b.getInt("topic_id");
        Word word = DB.getWordById(this, wordId);

        final EditText russianTranslateView = (EditText) findViewById(R.id.russianTranslateEditView);
        final EditText englishTranslateView = (EditText) findViewById(R.id.englishTranslateEditView);
        Button saveButton = (Button) findViewById(R.id.buttonSaveWord);
        Button deleteButton = (Button) findViewById(R.id.buttonDeleteWord);
        final Context  ctx = this;

        if (wordId == -1){
            englishTranslateView.setText("");
            russianTranslateView.setText("");
        }else{
            englishTranslateView.setText(word.getEnglish());
            russianTranslateView.setText(word.getRussian());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.updateWord(ctx, new Word(wordId, topicId, russianTranslateView.getText().toString(), englishTranslateView.getText().toString()));
                Intent intent = new Intent(WordSettingsActivity.this, WordsListActivity.class);
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.deleteWord(ctx, wordId);
                Intent intent = new Intent(WordSettingsActivity.this, WordsListActivity.class);
                b.putInt("topic_id", topicId);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });
    }
}
