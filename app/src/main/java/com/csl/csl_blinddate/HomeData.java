package com.csl.csl_blinddate;

import android.content.Context;

public class HomeData {
    private Context context;
    private String homeTitle;
    private String homePost_1;
    private String homePost_2;
    private String homePost_3;

    public HomeData(Context context,String homeTitle, String homePost_1, String homePost_2, String homePost_3) {
        this.context = context;
        this.homeTitle = homeTitle;
        this.homePost_1 = homePost_1;
        this.homePost_2 = homePost_2;
        this.homePost_3 = homePost_3;
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

    public Context getContext() {
        return context;
    }
}
