package com.example.idzeh.learningwords;
/*
модель слова для запоминания
 */
public class Word {
    private int parentId, id;
    private String russian, english;


    public int getParentId() {
        return parentId;
    }

    public String getRussian() {
        return russian;
    }

    public String getEnglish() {
        return english;
    }

    public int getId() {
        return id;
    }

    public Word(int id, int parentId, String russian, String english) {
        this.parentId = parentId;
        this.russian = russian;
        this.english = english;
        this.id = id;
    }
}
