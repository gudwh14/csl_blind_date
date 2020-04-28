package com.csl.csl_blinddate;

import com.csl.csl_blinddate.Data.RetrofitRepo;

import java.util.HashMap;
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
    String URL = "http://112.168.40.113/blinddate/";


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

}
