package com.example.idzeh.learningwords;
/*
класс режима тестирования
 */
import android.media.*;
import android.graphics.Color;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.res.*;
import android.annotation.*;

public class ExamActivity extends AppCompatActivity {

    ArrayList<Word> words;
    int topicId;
    int curWordInd;
    ArrayList<Integer> list = new ArrayList<>();
    CheckBox[] answer = new CheckBox[3];
    Button nextButton;
    Button backButton;
    TextView examWord;
    TextView examMessage;
    Random rnd = new Random();
    int lang;
    private int mErrorSound, mSuccessSound;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            createOldSoundPool();
        } else {
            createNewSoundPool();
        }
        mAssetManager = getAssets();
        mErrorSound = loadSound("error_sound.ogg");
        mSuccessSound = loadSound("success_sound.ogg");
        super.onCreate(savedInstanceState);
        list.add(0);
        list.add(1);
        list.add(2);
        setContentView(R.layout.activity_exam);
        Bundle b = getIntent().getExtras();
        topicId = b.getInt("topic_id");
        curWordInd = 0;
        nextButton = (Button) findViewById(R.id.exam_next_button);
        backButton = (Button) findViewById(R.id.exam_back_button);
        examWord = (TextView) findViewById(R.id.exam_word);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextCard();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        examMessage = (TextView) findViewById(R.id.exam_message);
        nextButton.setClickable(false);
        TextView topicTitle = (TextView) findViewById(R.id.exam_topic);
        topicTitle.setText(DB.getTopicNameById(this, topicId));

        words = DB.getWordsByParentId(this, topicId);
        Collections.shuffle(words);
        answer[0] = (CheckBox) findViewById(R.id.answer1);
        answer[1]= (CheckBox) findViewById(R.id.answer2);
        answer[2] = (CheckBox) findViewById(R.id.answer3);
        showNextCard();

    }

    void showNextCard(){

        lang = rnd.nextInt() % 2;
        Collections.shuffle(list);
        Collections.shuffle(words);
        nextButton.setClickable(false);
        examMessage.setText("Select correct translation");
        examMessage.setTextColor(Color.BLACK);
        if (lang == 0){
            examWord.setText(words.get((curWordInd)).getRussian());

        }else{
            examWord.setText(words.get((curWordInd)).getEnglish());
        }
        for (int i=0; i < 3; ++i) {
            answer[i].setChecked(false);
            answer[i].setTextColor(Color.BLACK);
            answer[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();
                }
            });
            if (lang == 0){
                answer[i].setText(words.get((curWordInd + list.get(i))%words.size()).getEnglish());
            }else{
                answer[i].setText(words.get((curWordInd + list.get(i))%words.size()).getRussian());
            }
        }
        curWordInd++;
        if (words.size() == curWordInd){
            Collections.shuffle(words);
            curWordInd = 0;
        }
    }
    void check () {
        int correct = -1;
        int selected = -1;

        nextButton.setClickable(true);

        for (int i = 0; i < 3; ++i){
            if (answer[i].isChecked()) selected = i;
            if (list.get(i) == 0) correct = i;
            answer[i].setClickable(false);
        }
        answer[selected].setTextColor(Color.RED);
        answer[correct].setTextColor(Color.GREEN);
        if (correct == selected) {
            examMessage.setText("Correct :)");
            examMessage.setTextColor(Color.GREEN);
            playSound(mSuccessSound);
        }else{
            examMessage.setText("Uncorrect :(");
            examMessage.setTextColor(Color.RED);
            playSound(mErrorSound);
        }

    }
    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }
    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }
    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }
}
