package com.example.idzeh.learningwords;
/*
модель топика
 */
public class Topic {
    public int id;
    public String name;
    int drawResource;

    public Topic(int id, String name, int drawResource) {
        this.id = id;
        this.name = name;
        this.drawResource = drawResource;
    }

    public int getDrawResource() {
        return drawResource;
    }

    public void setDrawResource(int drawResource) {
        this.drawResource = drawResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
        this.drawResource = R.drawable.english;
    }
}
