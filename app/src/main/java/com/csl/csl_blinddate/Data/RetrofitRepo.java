package com.csl.csl_blinddate.Data;

import com.google.gson.annotations.SerializedName;

public class RetrofitRepo {
    @SerializedName("kakaoID")
    private int kakaoID;
    @SerializedName("userID")
    private String userID;
    @SerializedName("meeting_id")
    private int meeting_id;
    @SerializedName("apply_id")
    private  int apply_id;
    @SerializedName("board_id")
    private  int board_id;
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
    @SerializedName("apply_status")
    private int apply_status;
    @SerializedName("newbie")
    private boolean newbie;
    @SerializedName("success")
    private boolean success;
    @SerializedName("comment")
    private  String comment;
    @SerializedName("trait")
    private boolean[] trait;
    @SerializedName("date")
    private int date;
    @SerializedName("chatMSG")
    private String chatMSG;
    @SerializedName("time")
    private String time;
    @SerializedName("title")
    private String title;
    @SerializedName("mainText")
    private String mainText;
    @SerializedName("up")
    private int up;
    @SerializedName("comments")
    private int comments;

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

    public int getDate() {
        return date;
    }

    public int getApply_status() {
        return apply_status;
    }

    public int getApply_id() {
        return apply_id;
    }

    public String getTime() {
        return time;
    }

    public String getChatMSG() {
        return chatMSG;
    }

    public int getBoard_id() {
        return board_id;
    }

    public String getTitle() {
        return title;
    }

    public String getMainText() {
        return mainText;
    }

    public int getUp() {
        return up;
    }

    public int getComments() {
        return comments;
    }
}
