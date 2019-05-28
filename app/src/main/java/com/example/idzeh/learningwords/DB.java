package com.example.idzeh.learningwords;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.ContentValues;
import android.util.Log;
/*
класс с методами для работы с БД
 */
public class DB{

    public static ArrayList<Word> words;

    public static ArrayList<Topic> getAllTopics(Context context){
        ArrayList<Topic> topics = new ArrayList<>();
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        Cursor c = db.query("topics_table", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("ID");
            int nameColIndex = c.getColumnIndex("NAME");
            int pictureColIndex = c.getColumnIndex("PICTURE_ID");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                topics.add(new Topic(c.getInt(idColIndex), c.getString(nameColIndex), c.getInt(pictureColIndex)));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
        return topics;
    }



    public static ArrayList<Word> getWordsByParentId(Context context, int id){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        Cursor c = db.query("words_table", null, "TOPIC_ID = " + id, null, null, null, null);
        ArrayList<Word> res = new ArrayList<>();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("ID");
            int russianColIndex = c.getColumnIndex("RUSSIAN");
            int englishColIndex = c.getColumnIndex("ENGLISH");
            int topicIdColIndex = c.getColumnIndex("TOPIC_ID");

            do {
                res.add(new Word(c.getInt(idColIndex), c.getInt(topicIdColIndex), c.getString(russianColIndex), c.getString(englishColIndex)));
            } while (c.moveToNext());
        }
        return res;
    }
    public static Word getWordById(Context context, int id){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        Cursor c = db.query("words_table", null, "ID = " + id, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("ID");
            int russianColIndex = c.getColumnIndex("RUSSIAN");
            int englishColIndex = c.getColumnIndex("ENGLISH");
            int topicIdColIndex = c.getColumnIndex("TOPIC_ID");
            return new Word(c.getInt(idColIndex), c.getInt(topicIdColIndex), c.getString(russianColIndex), c.getString(englishColIndex));
        }
        return null;
    }
    public static String getTopicNameById(Context context, int id){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        Cursor c = db.query("topics_table", null, "id = " + id, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("NAME");
            return c.getString(nameColIndex);
        }
        return "Enter topic name here";
    }

    public static void updateWord (Context context, Word word){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        if (word.getId() == -1){
            ContentValues cv = new ContentValues();
            cv.put("RUSSIAN", word.getRussian());
            cv.put("ENGLISH", word.getEnglish());
            cv.put("TOPIC_ID", word.getParentId());
            db.insert("words_table", null, cv);
            return;
        }
        db.execSQL("UPDATE  words_table set ENGLISH = '" + word.getEnglish() + "', RUSSIAN = '" + word.getRussian() + "' where id = " + word.getId());
    }

    public static void deleteWord(Context context, int id){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        if (id != -1){
            db.execSQL("DELETE FROM words_table WHERE id = " + id);
        }
    }

    public static void updateTopic (Context context, Topic topic){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        if (topic.getId() == -1){
            ContentValues cv = new ContentValues();
            cv.put("NAME", topic.getName());
            cv.put("PICTURE_ID", topic.getDrawResource());
            db.insert("topics_table", null, cv);
            return;
        }
        db.execSQL("UPDATE  topics_table set  NAME = '" + topic.getName() + "' where id = " + topic.getId());
    }

    public static void deleteTopic(Context context, int id){
        SQLiteDatabase db = new DB_HELPER(context).getReadableDatabase();
        if (id != -1){
            db.execSQL("DELETE FROM topics_table WHERE id = " + id);
        }
    }
}
class DB_HELPER extends SQLiteOpenHelper{

    public final static String DATABASE_NAME = "learningwords.db";
    private static ArrayList<Word> words;

    public DB_HELPER (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS topics_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT UNIQUE, PICTURE_ID INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS words_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, RUSSIAN TEXT, ENGLISH TEXT, TOPIC_ID INTEGER);");

        ContentValues cv = new ContentValues();

        cv.put("NAME","Food" );
        cv.put("PICTURE_ID", R.drawable.food);
        db.insert("topics_table", null, cv);
        cv.clear();

        cv.put("NAME","Animals" );
        cv.put("PICTURE_ID", R.drawable.animals);
        db.insert("topics_table", null, cv);
        cv.clear();

        cv.put("NAME","Family" );
        cv.put("PICTURE_ID", R.drawable.family);
        db.insert("topics_table", null, cv);
        cv.clear();

        cv.put("NAME","Professions" );
        cv.put("PICTURE_ID", R.drawable.professions);
        db.insert("topics_table", null, cv);
        cv.clear();

        initWords();

        for (Word w: words){
            cv.put("RUSSIAN", w.getRussian());
            cv.put("ENGLISH", w.getEnglish());
            cv.put("TOPIC_ID", w.getParentId());
            db.insert("words_table", null, cv);
        }
    }
    private static void initWords(){
        words = new ArrayList<>();
        words.add(new Word(1, 1, "Хлеб","Bread"));
        words.add(new Word(2, 1, "Масло","Butter"));
        words.add(new Word(3, 1, "Картошка","Potato"));
        words.add(new Word(4, 2, "Собака","Dog"));
        words.add(new Word(5, 2, "Кошка","Cat"));
        words.add(new Word(6, 2, "Лиса","Fox"));
        words.add(new Word(7, 3, "Мать","Mother"));
        words.add(new Word(8, 3, "Отец","Father"));
        words.add(new Word(9, 3, "Брат","Brother"));
        words.add(new Word(10, 4, "Программист","Programmer"));
        words.add(new Word(11, 4, "Учитель","Teacher"));
        words.add(new Word(12, 4, "Врач","Doctor"));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}