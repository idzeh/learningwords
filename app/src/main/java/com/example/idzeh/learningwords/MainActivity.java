package com.example.idzeh.learningwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView topicListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topicListView = (ListView) findViewById(R.id.topic_list_view);
        ArrayList<Topic> topics = DB.getAllTopics(this);
        TopicsAdapter topicsAdapter = new TopicsAdapter(getApplicationContext(), R.layout.topics_layout, topics);
        topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = (Topic) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, TopicSettingsActivity.class);
                Bundle b = new Bundle();
                b.putInt("topic_id", topic.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        topicListView = (ListView) findViewById(R.id.topic_list_view);
        topicListView.setAdapter(topicsAdapter);

        Button createTopicButton = findViewById(R.id.createTopicButton);

        createTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTopicActivity.class);
                Bundle b = new Bundle();
                b.putInt("topic_id", -1);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
