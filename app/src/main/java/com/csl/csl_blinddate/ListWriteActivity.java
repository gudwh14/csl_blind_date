package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ListWriteActivity extends AppCompatActivity {
    ImageButton ListWrite_CloseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_write);

        // 초기화
        ListWrite_CloseButton = findViewById(R.id.ListWrite_CloseButton);
        // CloseButton 클릭 리스너
        ListWrite_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 종료
            }
        });

    }
}
