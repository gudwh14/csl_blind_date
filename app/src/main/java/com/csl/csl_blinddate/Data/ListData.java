package com.csl.csl_blinddate.Data;

import android.content.Context;

public class ListData {
    private String school;
    private boolean certification;
    private int member;
    private boolean gender;
    private boolean open;
    private Context context;
    private int list_id = 1;  // 미팅 리스트 id

    public ListData(Context context,String school, boolean certification, int member, boolean gender, boolean open) {
        this.context = context;
        this.school = school;
        this.certification = certification;
        this.member = member;
        this.gender = gender;
        this.open = open;
    }

    public String getSchool(){
        return school;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public boolean getGender() {
        return gender;
    }

    public int getMember() {
        return member;
    }

    public boolean getCertification() {
        return certification;
    }

    public boolean getOpen(){
        return open;
    }

    public Context getContext() {
        return context;
    }

    public int getList_id() {
        return list_id;
    }
}
