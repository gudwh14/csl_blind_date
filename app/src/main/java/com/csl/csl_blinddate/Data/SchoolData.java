package com.csl.csl_blinddate.Data;

public class SchoolData {
    private String school;
    private String mail;

    public SchoolData(String school, String mail) {
        this.school = school;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public String getSchool() {
        return school;
    }
}
