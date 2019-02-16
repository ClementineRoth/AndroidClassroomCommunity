package com.valzaris.classroomcommunity.class_source;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    public int id;
    public String text;
    public int duration;
    public List<Answer> answers;

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
