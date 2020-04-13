package com.csl.csl_blinddate;

import android.content.Context;

public class ChatListData {
    private Context context;
    private String school;
    private String name;
    private int member;

    public ChatListData(Context context, String school, String name, int member) {
        this.context = context;
        this.school = school;
        this.name = name;
        this.member = member;
    }

    public Context getContext() {
        return context;
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
