package com.csl.csl_blinddate.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RetrofitRepoList {
    @SerializedName("response")
    private ArrayList<RetrofitRepo> repoArrayList;

    public ArrayList<RetrofitRepo> getRepoArrayList() {
        return repoArrayList;
    }

    public void setRepoArrayList(ArrayList<RetrofitRepo> repoArrayList) {
        this.repoArrayList = repoArrayList;
    }
}
