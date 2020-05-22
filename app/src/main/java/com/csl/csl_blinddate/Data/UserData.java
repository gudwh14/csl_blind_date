package com.csl.csl_blinddate.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.kakao.usermgmt.response.model.User;

public class UserData {
    private String userID;
    private int age;
    private String gender;
    private String school;
    private String mail;
    private boolean certification;

    private UserData() {
    }

    private static class UserDataHolder {
        public static final UserData INSTANCE = new UserData();
    }

    public static UserData getInstance() {
        return UserDataHolder.INSTANCE;
    }

    public String getSchool() {
        return school;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMail() {
        return mail;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isCertification() {
        return certification;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
