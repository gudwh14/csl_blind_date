package com.csl.csl_blinddate;

public class ChatData {
    private int chatID;
    private String userID;
    private String chatMsg;
    private boolean myText;

    public ChatData(int chatID,String userID,String chatMsg,boolean myText){
        this.chatID = chatID;
        this.userID = userID;
        this.chatMsg = chatMsg;
        this.myText = myText;
    }

    public int getChatID() {
        return chatID;
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
