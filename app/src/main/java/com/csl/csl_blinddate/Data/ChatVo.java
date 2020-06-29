package com.csl.csl_blinddate.Data;

public class ChatVo {
    private int meeting_id;
    private String userID;
    private String chatMsg;
    private String time;
    private boolean exit = false;

    public ChatVo(){}

    public ChatVo(int meeting_id,String userID, String chatMsg, String time){
        this.meeting_id = meeting_id;
        this.userID = userID;
        this.chatMsg = chatMsg;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public String getUserID() {
        return userID;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
