package com.csl.csl_blinddate.Data;

public class CommentData {
    private String board_title;
    private int board_id;
    private int comment_id;
    private String userID;
    private String time;
    private int up;
    private String comment;
    private boolean reply; // 0 : comment , 1 : reply

    public CommentData(String board_title, int board_id, int comment_id, String userID, String time, int up, String comment, boolean reply) {
        this.board_title = board_title;
        this.board_id = board_id;
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

    public int getBoard_id() {
        return board_id;
    }

    public String getBoard_title() {
        return board_title;
    }
}
