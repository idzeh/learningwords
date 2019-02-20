package com.example.idzeh.learningwords;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;

public class TopicsAdapter extends ArrayAdapter<Topic>{
    ArrayList<Topic> topics = null;
    Context context;
    int resource;
    public TopicsAdapter(Context context, int resource, ArrayList<Topic> topics) {
        super(context, resource, topics);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Topic topic = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.topics_layout, parent, false);
        }
        TextView topicName = (TextView) convertView.findViewById(R.id.topic_text_view);
        topicName.setText(topic.getName());
        ImageView img =  convertView.findViewById(R.id.topicImage);
        img.setImageResource(topic.drawResource);
        return convertView;
    }
}
