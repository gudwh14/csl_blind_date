package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class RegisterActivity extends AppCompatActivity {
    MaterialButton RegisterButton;
    TextView kakaoID_TextView;
    Spinner ageSpinner;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // View 초기화
        RegisterButton = findViewById(R.id.RegisterButton);
        kakaoID_TextView = findViewById(R.id.kakaoID_TextView);
        ageSpinner = findViewById(R.id.ageSpinner);

        kakaoID_TextView.setText(getIntent().getStringExtra("kakaoID"));

        // 버튼 클릭 리스너
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // retrofit 통신
                HashMap<String, Object> data = new HashMap<>();
                data.put("kakaoID",(getIntent().getStringExtra("kakaoID")));
                data.put("userID","Retrofit테스트");
                data.put("age",age);
                data.put("school","수원대학교");
                data.put("mail","test@naver.com");
                data.put("gender","M");
                data.put("certification",true);

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
                        if(repo.isSuccess()) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"통신 실패",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

        // 스피너 이벤트 처리
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=1) {
                    age = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
