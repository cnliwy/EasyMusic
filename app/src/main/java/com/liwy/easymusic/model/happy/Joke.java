package com.liwy.easymusic.model.happy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liwy on 2017/5/4.
 */

public class Joke {
    private String id;
    private int type;
    private String title;
    private String img;

    @SerializedName("ct")
    private String time;



    @SerializedName("text")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
