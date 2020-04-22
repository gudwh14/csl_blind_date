package com.csl.csl_blinddate.Data;

import android.content.Context;

public class ChatListData {
    private String school;
    private String name;
    private int member;

    public ChatListData(String school, String name, int member) {
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
}
