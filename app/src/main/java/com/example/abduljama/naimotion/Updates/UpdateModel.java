package com.example.abduljama.naimotion.Updates;

/**
 * Created by abduljama on 5/25/16.
 */
public class UpdateModel {
    String  title;
    String  desc;
    int  image;
    String  time ;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
