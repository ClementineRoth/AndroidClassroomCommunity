package com.valzaris.classroomcommunity.class_source;

import java.io.Serializable;

public class Friend implements Serializable {
    public int id;
    public String first_name;
    public String last_name;
    public int is_present;
    public String photo_path;
    public String key;
    public int last_score;

    public Friend(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getIs_present() {
        return is_present;
    }

    public void setIs_present(int is_present) {
        this.is_present = is_present;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLast_score() {
        return last_score;
    }

    public void setLast_score(int last_score) {
        this.last_score = last_score;
    }
}
