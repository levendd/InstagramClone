package com.levojuk.instagramclone.model;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Post {
    public String useremail ;
    public String username;
    public String comment;
    public String downloadUrl;
    public String  timestamp;


    public Post(String useremail,String username, String comment, String downloadUrl, String timestamp) {
        this.useremail = useremail;
        this.comment = comment;
        this.downloadUrl = downloadUrl;
        this.timestamp=timestamp;
        this.username=username;

    }
}
