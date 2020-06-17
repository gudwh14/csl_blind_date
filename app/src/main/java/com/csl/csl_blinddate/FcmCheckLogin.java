package com.csl.csl_blinddate;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.csl.csl_blinddate.RetrofitService.URL;

public class FcmCheckLogin {
    Context context;
    SharedPreferences pref;
    boolean auto_login;
    String kakaoID;

    public FcmCheckLogin(Context context) {
        this.context = context;
    }

    public void checkUser() {
        if(UserData.getInstance().getUserID() == null) {
            Log.i("fcm","userID = null");
            pref = context.getSharedPreferences("auto_login",MODE_PRIVATE);
            auto_login = pref.getBoolean("auto_login",false);
            kakaoID = pref.getString("kakaoID","");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            HashMap<String, Object> data = new HashMap<>();
            data.put("kakaoID",kakaoID);
            data.put("auto_login",auto_login);
            data.put("token",pref.getString("token",""));
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<RetrofitRepo> call = retrofitService.checkUser(data);
            call.enqueue(new Callback<RetrofitRepo>() {
                @Override
                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                    RetrofitRepo repo = response.body();

                    if(repo.isSuccess()) {
                        UserData userData = UserData.getInstance();
                        userData.setUserID(repo.getUserID());
                        userData.setAge(repo.getAge());
                        userData.setSchool(repo.getSchool());
                        userData.setGender(repo.getGender());
                        userData.setMail(repo.getMail());
                        userData.setCertification(repo.isCertification());
                    }
                }

                @Override
                public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        else {
            Log.i("fcm","userID != null");
        }
    }

}
