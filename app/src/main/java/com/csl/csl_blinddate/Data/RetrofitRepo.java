package com.csl.csl_blinddate.Data;

import com.google.gson.annotations.SerializedName;

public class RetrofitRepo {
    @SerializedName("kakaoID")
    private int kakaoID;
    @SerializedName("userID")
    private String userID;
    @SerializedName("meeting_id")
    private int meeting_id;
    @SerializedName("age")
    private int age;
    @SerializedName("member")
    private int member;
    @SerializedName("gender")
    private String gender;
    @SerializedName("school")
    private String school;
    @SerializedName("mail")
    private String mail;
    @SerializedName("certification")
    private boolean certification;
    @SerializedName("isWrite")
    private boolean isWrite;
    @SerializedName("isMatch")
    private boolean isMatch;
    @SerializedName("status")
    private boolean status;
    @SerializedName("newbie")
    private boolean newbie;
    @SerializedName("success")
    private boolean success;
    @SerializedName("comment")
    private  String comment;
    @SerializedName("trait")
    private boolean[] trait;

    public boolean isSuccess() {
        return success;
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

    public boolean isMatch() {
        return isMatch;
    }

    public boolean isWrite() {
        return isWrite;
    }

    public int getMember() {
        return member;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public boolean isNewbie() {
        return newbie;
    }

    public boolean isStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public boolean[] getTrait() {
        return trait;
    }
}
