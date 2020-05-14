package com.csl.csl_blinddate;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    String URL = "http://121.137.115.129/blinddate/";


    @GET("index.php")
    Call<RetrofitRepo> getIndex(
            @Query("name") String name
    );

    @GET("test.php")
    Call<RetrofitRepo> getItem(
            @QueryMap Map<String, String> option
    );

    @FormUrlEncoded
    @POST("checkUser.php")
    Call<RetrofitRepo> checkUser(
            @Field("kakaoID") String kakaoID
    );

    @FormUrlEncoded
    @POST("Register.php")
    Call<RetrofitRepo> RegisterData(
            @FieldMap HashMap<String, Object> param
            );

    @FormUrlEncoded
    @POST("ListWrite.php")
    Call<RetrofitRepo> ListWriteData(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ListRefresh.php")
    Call<RetrofitRepoList> ListRefresh(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ListInFormRefresh.php")
    Call<RetrofitRepo> ListInFormRefresh(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ListApplyRefresh.php")
    Call<RetrofitRepoList> ListApplyRefresh(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ListApply.php")
    Call<RetrofitRepo> ListApply(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ListApplyedRefresh.php")
    Call<RetrofitRepoList> ListApplyedRefresh(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ApplySelect.php")
    Call<RetrofitRepo> ApplySelect(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ChatListRefresh.php")
    Call<RetrofitRepoList> ChatListRefresh(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ChatInsert.php")
    Call<RetrofitRepo> ChatInsert(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("ChatRefresh.php")
    Call<RetrofitRepoList> ChatRefresh(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("BoardWrite.php")
    Call<RetrofitRepo> BoardWrite(
            @FieldMap HashMap<String, Object> param
    );

    @FormUrlEncoded
    @POST("BoardRefresh.php")
    Call<RetrofitRepoList> BoardRefresh(
            @FieldMap HashMap<String, Object> param
    );


}
