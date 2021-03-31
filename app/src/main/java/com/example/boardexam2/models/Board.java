package com.example.boardexam2.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;

public class Board {



    private String id;
    private String title;
    private String type;
    private String contents;
    private String name;
    private String email;
    @ServerTimestamp
    private Date createdate;
    private Date updatedate;

    public Board() {
    }

    public Board(String id, String title, String type, String contents, String name, String email, Date createdate, Date updatedate, String admin) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.contents = contents;
        this.name = name;
        this.email = email;
        this.createdate = createdate;
        this.updatedate = updatedate;
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

    public String getType() {return type;}

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

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", email=" + email +
                '}';
    }
}