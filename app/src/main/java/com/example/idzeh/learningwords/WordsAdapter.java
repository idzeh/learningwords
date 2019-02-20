package com.example.idzeh.learningwords;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<Word>{
    Context context;
    int resource;
    ArrayList<Word> words = null;
    public WordsAdapter(Context context, int resource, ArrayList<Word> words) {
        super(context, resource, words);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.words_layout, parent, false);
        }
        TextView englishTranslateView = (TextView) convertView.findViewById(R.id.englishTranslateView);
        TextView russianTranslateView = (TextView) convertView.findViewById(R.id.russianTranslateView);
        englishTranslateView.setText(word.getEnglish());
        russianTranslateView.setText(word.getRussian());
        return convertView;
    }
}
