package com.csl.csl_blinddate.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitRepo {
    @SerializedName("kakaoID")
    @Expose
    private int kakaoID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("meeting_id")
    @Expose
    private int meeting_id;
    @SerializedName("apply_id")
    @Expose
    private  int apply_id;
    @SerializedName("board_id")
    @Expose
    private  int board_id;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("member")
    @Expose
    private int member;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("certification")
    @Expose
    private boolean certification;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("apply_status")
    @Expose
    private int apply_status;
    @SerializedName("newbie")
    @Expose
    private boolean newbie;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("comment")
    @Expose
    private  String comment;
    @SerializedName("trait")
    @Expose
    private boolean[] trait;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("chatMSG")
    @Expose
    private String chatMSG;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("mainText")
    @Expose
    private String mainText;
    @SerializedName("up")
    @Expose
    private int up;
    @SerializedName("comments")
    @Expose
    private int comments;
    @SerializedName("favorite")
    @Expose
    private int favorite;
    @SerializedName("reply")
    @Expose
    private boolean reply;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("board_title")
    @Expose
    private String board_title;
    @SerializedName("isFavorite")
    @Expose
    private boolean isFavorite;
    @SerializedName("isBoardUp")
    private boolean isBoardUp;
    @SerializedName("anonymous")
    @Expose
    private boolean anonymous;
    @SerializedName("anony_count")
    @Expose
    private int anony_count;
    @SerializedName("writer")
    @Expose
    private boolean writer;
    @SerializedName("isCommentUp")
    @Expose
    private boolean isCommentUp;
    @SerializedName("image_path")
    @Expose
    private String image_path;

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

    public int getFavorite() {
        return favorite;
    }

    public boolean isReply() {
        return reply;
    }

    public int getId() {
        return id;
    }

    public String getBoard_title() {
        return board_title;
    }

    public boolean isBoardUP() {
        return isBoardUp;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getAnony_count() {
        return anony_count;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public boolean isWriter() {
        return writer;
    }

    public boolean isCommentUp() {
        return isCommentUp;
    }

    public String getImage_path() {
        return image_path;
    }
}
