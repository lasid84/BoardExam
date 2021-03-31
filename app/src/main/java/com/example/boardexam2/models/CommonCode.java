package com.example.boardexam2.models;


import com.example.boardexam2.FirebaseID;

import java.util.HashMap;

public class CommonCode extends com.example.boardexam2.FirebaseID {

    public HashMap<Integer, String> typeMap = new HashMap<Integer, String>();
    public String[] typeArr;

    public CommonCode() {
    }

    public CommonCode(String menuType, String adminYN) {
        switch (menuType) {
            case FirebaseID.board :
                if (adminYN == "Y")
                    typeMap.put(0, "notice");

                typeMap.put(1, "question");
                typeArr = new String[typeMap.size()];
                for (int i = 0; i <typeMap.size(); i++)
                    typeArr[i] = typeMap.get(i);

                break;
        }
    }
}
