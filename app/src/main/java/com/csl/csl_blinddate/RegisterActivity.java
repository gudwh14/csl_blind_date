package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {
    MaterialButton RegisterButton;
    TextView kakaoID_TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterButton = findViewById(R.id.RegisterButton);
        kakaoID_TextView = findViewById(R.id.kakaoID_TextView);

        kakaoID_TextView.setText(getIntent().getStringExtra("kakaoID"));

        // 버튼 클릭 리스너
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
