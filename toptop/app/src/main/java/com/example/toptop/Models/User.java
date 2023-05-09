package com.example.toptop.Models;

import java.util.List;

public class User {
    private  String user_id, user_name,birthday,photoUrl,lastMesseger,status;

    public User() {
    }


    public User(String user_id, String user_name, String birthday, String photoUrl, String lastMesseger, String status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.birthday = birthday;
        this.photoUrl = photoUrl;
        this.lastMesseger = lastMesseger;
        this.status = status;
    }

    public User(String user_id, String user_name, String photoUrl) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.photoUrl = photoUrl;
    }

    private List<MediaObject> allPost;

    public List<MediaObject> getAllPost() {
        return allPost;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLastMesseger() {
        return lastMesseger;
    }

    public void setLastMesseger(String lastMesseger) {
        this.lastMesseger = lastMesseger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
