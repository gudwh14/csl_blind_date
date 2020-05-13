package com.csl.csl_blinddate.Data;

public class ChatData {
    private String userID;
    private String chatMsg;
    private String time;
    private boolean myText;

    public ChatData(String userID,String chatMsg,String time,boolean myText){
        this.userID = userID;
        this.chatMsg = chatMsg;
        this.time = time;
        this.myText = myText;
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

    public boolean isMyText() {
        return myText;
    }
}
