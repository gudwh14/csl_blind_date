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

    public UserData(String userID, int age, String gender, String school, String mail, boolean certification) {
        this.userID = userID;
        this.age = age;
        this.gender = gender;
        this.school = school;
        this.mail = mail;
        this.certification = certification;
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
}
