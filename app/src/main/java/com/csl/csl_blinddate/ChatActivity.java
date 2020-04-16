package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    TextView chat_titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_titleText = findViewById(R.id.chat_titleText);
        chat_titleText.setText(getIntent().getStringExtra("title"));
    }
}
