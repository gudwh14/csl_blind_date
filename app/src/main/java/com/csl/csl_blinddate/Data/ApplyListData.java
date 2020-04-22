package com.csl.csl_blinddate.Data;

public class ApplyListData {
    private String school;
    private int member;
    private String time;
    private int status;  // 0 : 대기 , 1 : 거절, 2 수락


    public ApplyListData(String school, int member, String time, int status) {
        this.school = school;
        this.member = member;
        this.time = time;
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

    public String getTime() {
        return time;
    }
}
