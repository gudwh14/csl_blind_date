package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class FcmLoadingActivity extends AppCompatActivity {
    FcmCheckLogin fcmCheckLogin;
    Context context;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_loading);

        flag = getIntent().getStringExtra("fragment");
        context = getApplicationContext();
        fcmCheckLogin = new FcmCheckLogin(context);
        fcmCheckLogin.checkUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if(flag.equals("chat")) {
                    intent = new Intent(context,MainActivity.class);
                    intent.putExtra("fragment","chat");
                }
                else if(flag.equals("applyed")) {
                    intent = new Intent(context,ApplyedListActivity.class);
                }
                else if(flag.equals("apply")) {
                    intent = new Intent(context,ApplyListActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },500);

    }
}
