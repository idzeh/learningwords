package com.example.idzeh.learningwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;

public class CreateTopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        final Context ctx = this;
        final EditText inputNameView = (EditText) findViewById(R.id.topic_name_input);
        Button saveButton = (Button) findViewById(R.id.buttonCreateTopic);
        Button backButton = (Button) findViewById(R.id.buttonBack);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.updateTopic(ctx, new Topic(-1, inputNameView.getText().toString()));
                toMainMenu(ctx);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainMenu(ctx);
            }
        });
    }
    private void toMainMenu(Context ctx){
        Intent intent = new Intent(ctx, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
