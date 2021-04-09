package com.example.boardexam2.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;

public class Board {


    private String key;
    private String id;
    private String title;
    private String type;
    private String contents;
    private String name;
    private String email;
    @ServerTimestamp
    private Date createdate;
    private Date updatedate;
    private String useflag;

    public Board() {
    }

    public Board(String key, String id, String title, String type, String contents, String name, String email, Date createdate, Date updatedate, String admin) {
        this.key = key;
        this.id = id;
        this.title = title;
        this.type = type;
        this.contents = contents;
        this.name = name;
        this.email = email;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.useflag = "Y";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        if (type.length() == 0)
            return " ";
        else
            return type;
    }

    public void setType(String type) {this.type = type;}

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {return updatedate;}

    public void setUpdatedate(Date updatedate) {this.updatedate = updatedate;}

    public String getUseflag() {
        return useflag;
    }

    public void setUseflag(String useflag) {
        this.useflag = useflag;
    }

    @Override
    public String toString() {
        return "Board{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", useflag='" + useflag + '\'' +
                '}';
    }
}