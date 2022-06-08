package com.example.Testproj.Entity;

import java.io.Serializable;

public class CommunityEntity implements Serializable {
    private int avatarId;
    private String username;
    private String time;
    private String content;
    private int imgId;

    public CommunityEntity(){

    }
    public CommunityEntity(int avatarId,String username,String time,String content,int imgId){
        this.avatarId = avatarId;
        this.username = username;
        this.time = time;
        this.content = content;
        this.imgId = imgId;
    }
    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
