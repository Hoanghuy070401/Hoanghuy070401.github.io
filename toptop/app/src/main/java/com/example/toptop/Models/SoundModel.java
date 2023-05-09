package com.example.toptop.Models;

public class SoundModel {

    public SoundModel(){

    }
    private  String soundid,sound_img,sound_sound,sound_title,datePost;

    public SoundModel(String soundid,  String sound_sound, String sound_title, String datePost) {
        this.soundid = soundid;
        this.sound_sound = sound_sound;
        this.sound_title = sound_title;
        this.datePost = datePost;
    }

    public String getSoundid() {
        return soundid;
    }

    public void setSoundid(String soundid) {
        this.soundid = soundid;
    }
    public String getSound_sound() {
        return sound_sound;
    }

    public void setSound_sound(String sound_sound) {
        this.sound_sound = sound_sound;
    }

    public String getSound_title() {
        return sound_title;
    }

    public void setSound_title(String sound_title) {
        this.sound_title = sound_title;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }
}
