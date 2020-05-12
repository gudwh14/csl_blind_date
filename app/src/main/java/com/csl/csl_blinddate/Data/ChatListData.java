package com.csl.csl_blinddate.Data;

import android.content.Context;

public class ChatListData {
    private int list_id;
    private String school;
    private String name;
    private int member;

    public ChatListData(int list_id,String school, String name, int member) {
        this.list_id = list_id;
        this.school = school;
        this.name = name;
        this.member = member;
    }


    public int getMember() {
        return member;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public int getList_id() {
        return list_id;
    }
}
