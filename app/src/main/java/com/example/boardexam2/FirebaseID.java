package com.example.boardexam2;

import java.sql.Timestamp;

public class FirebaseID {

    public static final String board = "board";

    public static String documentId = "documentId";
    public static String email = "email";
    public static String password = "password";

    public static String type  = "type";
    public static String title = "title";
    public static String contents = "contents";
    public static String name = "name";
    public static String createdate = "createdate";
    public static String updatedate = "updatedate";
    public static String useflag = "useflag";


    //값 전달용
    public static String nickname = "";
    public static String useremail = "";
    public static String admin = "N";

    //현재 등록한건은 timestamp값이 없어 임시 조치
    public static Timestamp last_createdate = null;

    //관리자
    public static String[] arr = { "lasid84@gmail.com" };
}
