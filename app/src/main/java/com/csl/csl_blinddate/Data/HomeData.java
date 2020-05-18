package com.csl.csl_blinddate.Data;

import android.content.Context;

public class HomeData {
    private String homeTitle;
    private String homePost_1;
    private int homePost_1_id;
    private String homePost_2;
    private int homePost_2_id;
    private String homePost_3;
    private int homePost_3_id;

    public HomeData(String homeTitle) {
        this.homeTitle = homeTitle;
        this.homePost_1 = "";
        this.homePost_2 = "";
        this.homePost_3 = "";
    }

    public String getHomeTitle() {
        return homeTitle;
    }

    public String getHomePost_1() {
        return homePost_1;
    }

    public String getHomePost_2() {
        return homePost_2;
    }

    public String getHomePost_3() {
        return homePost_3;
    }

    public int getHomePost_1_id() {
        return homePost_1_id;
    }

    public int getHomePost_2_id() {
        return homePost_2_id;
    }

    public int getHomePost_3_id() {
        return homePost_3_id;
    }

    public void setHomePost_1(String homePost_1) {
        this.homePost_1 = homePost_1;
    }

    public void setHomePost_1_id(int homePost_1_id) {
        this.homePost_1_id = homePost_1_id;
    }

    public void setHomePost_2(String homePost_2) {
        this.homePost_2 = homePost_2;
    }

    public void setHomePost_2_id(int homePost_2_id) {
        this.homePost_2_id = homePost_2_id;
    }

    public void setHomePost_3(String homePost_3) {
        this.homePost_3 = homePost_3;
    }

    public void setHomePost_3_id(int homePost_3_id) {
        this.homePost_3_id = homePost_3_id;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }
}
