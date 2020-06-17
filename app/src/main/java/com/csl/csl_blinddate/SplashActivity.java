package com.csl.csl_blinddate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;


import java.security.MessageDigest;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class SplashActivity extends AppCompatActivity {
    LoginButton kakaoLoginButton;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean auto_login;
    String kakaoID;

    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                            // 서버 통신
                            kakaoID = result.getId()+"";
                            if(pref.getString("token","").equals("")) {
                                ProgressBar progressBar = new ProgressBar(SplashActivity.this);
                                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                                builder.setView(progressBar);

                                final AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                alertDialog.show();

                                Toast.makeText(SplashActivity.this,"잠시후 다시 시도해 주세요",Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                            else {
                                login();
                            }

                            /* 연결해제
                            UserManagement.getInstance()
                                    .requestUnlink(new UnLinkResponseCallback() {
                                        @Override
                                        public void onSessionClosed(ErrorResult errorResult) {
                                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                                        }

                                        @Override
                                        public void onFailure(ErrorResult errorResult) {
                                            Log.e("KAKAO_API", "연결 끊기 실패: " + errorResult);

                                        }
                                        @Override
                                        public void onSuccess(Long result) {
                                            Log.i("KAKAO_API", "연결 끊기 성공. id: " + result);
                                        }
                                    });

                             */
                            /*
                            UserAccount kakaoAccount = result.getKakaoAccount();
                            if (kakaoAccount != null) {

                                // 이메일
                                String email = kakaoAccount.getEmail();

                                if (email != null) {
                                    Log.i("KAKAO_API", "email: " + email);

                                } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 이메일 획득 가능
                                    // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                } else {
                                    // 이메일 획득 불가
                                }

                                // 프로필
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                    Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                    Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());

                                } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 프로필 정보 획득 가능

                                } else {
                                    // 프로필 획득 불가
                                }
                            }

                             */
                        }
                    });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // preferences
        pref = getSharedPreferences("auto_login",MODE_PRIVATE);
        editor = pref.edit();
        auto_login = pref.getBoolean("auto_login",false);
        kakaoID = pref.getString("kakaoID","");
        // View 초기화
        kakaoLoginButton = findViewById(R.id.KakaoLoginButton);


        if(auto_login == false) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            kakaoLoginButton.setVisibility(View.VISIBLE);
                        }
                    },1000);
                }
            });
        }
        else {
            kakaoLoginButton.setVisibility(View.INVISIBLE);
            login();
        }
        //getAppKeyHash(); 해쉬키 구하기
        checkSelfPermission();
        Session.getCurrentSession().addCallback(sessionCallback);

    }


    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void login() {
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
                Intent intent;
                if(repo.isSuccess()) {
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                    editor.putBoolean("auto_login",true);
                    editor.putString("kakaoID",kakaoID);
                    editor.commit();
                    UserData userData = UserData.getInstance();
                    userData.setUserID(repo.getUserID());
                    userData.setAge(repo.getAge());
                    userData.setSchool(repo.getSchool());
                    userData.setGender(repo.getGender());
                    userData.setMail(repo.getMail());
                    userData.setCertification(repo.isCertification());
                    startActivity(intent);
                    finish();

                }
                else {
                    intent = new Intent(SplashActivity.this,RegisterActivity.class);
                    intent.putExtra("kakaoID",kakaoID);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //동의
                    Log.d("MainActivity","권한 허용 : " + permissions[i]);
                }
                else {
                    Toast.makeText(SplashActivity.this,"사용을 위해 해당 권한이 필요합니다",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


    public void checkSelfPermission() {
        String temp = ""; //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        } //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (TextUtils.isEmpty(temp) == false) { //권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }
        else {  //모두 허용 상태
            //Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }


}
