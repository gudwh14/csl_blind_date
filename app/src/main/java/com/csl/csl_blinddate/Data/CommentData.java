package com.csl.csl_blinddate.Data;

public class CommentData {
    private int comment_id;
    private String userID;
    private String time;
    private int up;
    private String comment;
    private boolean reply; // 0 : comment , 1 : reply

    public CommentData(int comment_id, String userID, String time, int up, String comment, boolean reply) {
        this.comment_id = comment_id;
        this.userID = userID;
        this.time = time;
        this.up = up;
        this.comment = comment;
        this.reply = reply;
    }

    public int getUp() {
        return up;
    }

    public String getUserID() {
        return userID;
    }

    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    public int getComment_id() {
        return comment_id;
    }

    public boolean isReply() {
        return reply;
    }
}
