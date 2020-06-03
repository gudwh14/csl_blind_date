package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.csl.csl_blinddate.Register.Register1_Fragment;
import com.csl.csl_blinddate.Register.Register2_Fragment;
import com.csl.csl_blinddate.Register.Register3_Fragment;
import com.csl.csl_blinddate.Register.Register4_Fragment;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class RegisterActivity extends AppCompatActivity {

    Register1_Fragment register1_fragment;
    Register2_Fragment register2_fragment;
    Register3_Fragment register3_fragment;
    Register4_Fragment register4_fragment;

    MaterialButton register_okButton;
    int fragment,age;
    String gender,school,mail,userID;
    boolean certification;

    SharedPreferences pref;// 자동로그인 Preference 생성
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pref = getSharedPreferences("auto_login",MODE_PRIVATE); // 자동로그인 Preference 생성
        editor = pref.edit();

        // Fragment 초기화
        register1_fragment = new Register1_Fragment();
        register2_fragment = new Register2_Fragment();
        register3_fragment = new Register3_Fragment();
        register4_fragment = new Register4_Fragment();

        // view 초기화
        fragment = 1;
        register_okButton = findViewById(R.id.register_okButton);

        getSupportFragmentManager().beginTransaction().replace(R.id.registerFrameLayout, register1_fragment).commitAllowingStateLoss();

        register_okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (fragment) {
                    case 1 :
                        Register1_Fragment register1Fragment = (Register1_Fragment)getSupportFragmentManager().findFragmentById(R.id.registerFrameLayout);
                        if(register1Fragment.gender.equals("")) {
                            Toast.makeText(RegisterActivity.this,"성별을 선택해주세요",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            gender = register1Fragment.gender;
                            getSupportFragmentManager().beginTransaction().replace(R.id.registerFrameLayout, register2_fragment).commitAllowingStateLoss();
                            fragment++;
                        }
                        break;
                    case 2 :
                        Register2_Fragment register2Fragment = (Register2_Fragment)getSupportFragmentManager().findFragmentById(R.id.registerFrameLayout);
                        if(register2Fragment.age == 0) {
                            Toast.makeText(RegisterActivity.this,"출생년도를 선택해주세요",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            age = register2Fragment.age;
                            getSupportFragmentManager().beginTransaction().replace(R.id.registerFrameLayout, register3_fragment).commitAllowingStateLoss();
                            fragment++;
                        }
                        break;
                    case 3 :
                        Register3_Fragment register3Fragment = (Register3_Fragment)getSupportFragmentManager().findFragmentById(R.id.registerFrameLayout);
                        if(register3Fragment.school.equals("")) {
                            Toast.makeText(RegisterActivity.this,"대학교를 선택해주세요",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            school = register3Fragment.school;
                            mail = register3Fragment.mail;
                            certification = register3Fragment.certification;
                            getSupportFragmentManager().beginTransaction().replace(R.id.registerFrameLayout, register4_fragment).commitAllowingStateLoss();
                            fragment++;
                        }
                        break;
                    case 4 :
                        Register4_Fragment register4Fragment = (Register4_Fragment)getSupportFragmentManager().findFragmentById(R.id.registerFrameLayout);
                        if(register4Fragment.register_userID.getText().toString().equals("")) {
                            Toast.makeText(RegisterActivity.this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // retrofit 통신
                            userID = register4Fragment.register_userID.getText().toString();
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("kakaoID", (getIntent().getStringExtra("kakaoID")));
                            data.put("userID",userID);
                            data.put("age", age);
                            data.put("school", school);
                            data.put("mail", mail);
                            data.put("gender", gender);
                            data.put("certification", certification);
                            data.put("token",pref.getString("token",""));

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                            Call<RetrofitRepo> call = retrofitService.RegisterData(data);
                            call.enqueue(new Callback<RetrofitRepo>() {
                                @Override
                                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                                    RetrofitRepo repo = response.body();
                                    if (repo.isSuccess()) { // 회원가입 성공시
                                        // Preference 데이터 저장
                                        editor.putBoolean("auto_login",true);
                                        editor.putString("kakaoID",getIntent().getStringExtra("kakaoID"));
                                        editor.commit();
                                        // 앱 시작화면으로 이동
                                        Intent intent = new Intent(RegisterActivity.this, SplashActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else { // 회원가입 실패( Primary key 중복)
                                        Toast.makeText(RegisterActivity.this, "닉네임 중복입니다, 다른 닉네임을 입력해주세요.", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 막기
    }
}
