package com.csl.csl_blinddate.Data;

public class RetrofitRepo {
    private int kakaoID;
    private String userID;
    private int age;
    private char gender;
    private String school;
    private String mail;
    private boolean certification;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public String getSchool() {
        return school;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public int getKakaoID() {
        return kakaoID;
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
