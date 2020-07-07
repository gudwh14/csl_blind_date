package com.csl.csl_blinddate.Data;

import android.content.Context;

public class BoardData {
    private int board_id;
    private String board_title;
    private String userID;
    private String title;
    private String time;
    private String image_path;
    private int up;
    private int comments;
    private boolean hot = false;

    public BoardData(int board_id,String board_title,String userID,String title,String time,int up, int comments, String image_path) {
        this.board_id = board_id;
        this.board_title = board_title;
        this.userID = userID;
        this.title = title;
        this.time = time;
        this.up = up;
        this.comments = comments;
        this.image_path = image_path;
    }

    public String getTime() {
        return time;
    }

    public String getUserID() {
        return userID;
    }

    public int getBoard_id() {
        return board_id;
    }

    public int getComments() {
        return comments;
    }

    public int getUp() {
        return up;
    }

    public String getTitle() {
        return title;
    }

    public String getBoard_title() {
        return board_title;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isHot() {
        return hot;
    }
}
