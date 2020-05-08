package com.csl.csl_blinddate.Data;

public class ApplyListData {
    private String school;
    private int member;
    private int date;
    private int status;  // 0 : 대기 , 1 : 거절, 2 수락


    public ApplyListData(String school, int member, int date, int status) {
        this.school = school;
        this.member = member;
        this.date = date;
        this.status = status;
    }

    public String getSchool() {
        return school;
    }

    public int getMember() {
        return member;
    }

    public int getStatus() {
        return status;
    }

    public int getDate() {
        return date;
    }
}
