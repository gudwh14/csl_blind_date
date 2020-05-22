package com.csl.csl_blinddate.Data;

import android.content.Context;

public class ListData {
    private String school;
    private String userID;
    private boolean certification;
    private int member;
    int age;
    private String gender;
    private boolean open;   // 0 : open , 1 : close
    private boolean newbie;
    private int list_id;  // 미팅 리스트 id

    public ListData(int list_id,int age,String userID, String school, boolean certification, int member, String gender, boolean newbie, boolean open) {
        this.list_id = list_id;
        this.userID = userID;
        this.school = school;
        this.age = age;
        this.certification = certification;
        this.member = member;
        this.gender = gender;
        this.open = open;
        this.newbie = newbie;
    }

    public String getSchool(){
        return school;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public void setGender(String gender) {
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

    public String getGender() {
        return gender;
    }

    public int getMember() {
        return member;
    }

    public boolean getCertification() {
        return certification;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isNewbie() {
        return newbie;
    }

    public int getList_id() {
        return list_id;
    }

    public int getAge() {
        return age;
    }

    public String getUserID() {
        return userID;
    }
}
