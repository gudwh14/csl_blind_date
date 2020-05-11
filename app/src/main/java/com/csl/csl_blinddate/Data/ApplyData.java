package com.csl.csl_blinddate.Data;

public class ApplyData {
    private int apply_id;
    private int age;
    private String school;
    private boolean certify;
    private String comment;

    public ApplyData(int apply_id, int age, String school, boolean certify, String comment) {
        this.apply_id = apply_id;
        this.age = age;
        this.school = school;
        this.certify = certify;
        this.comment = comment;
    }

    public String getSchool() {
        return school;
    }


    public int getAge() {
        return age;
    }

    public String getComment() {
        return comment;
    }

    public boolean isCertify() {
        return certify;
    }

    public int getApply_id() {
        return apply_id;
    }
}
