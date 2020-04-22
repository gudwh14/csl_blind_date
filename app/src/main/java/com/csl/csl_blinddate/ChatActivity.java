package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.csl.csl_blinddate.Adapter.ChatAdapter;
import com.csl.csl_blinddate.Data.ChatData;

public class ChatActivity extends AppCompatActivity {

    TextView chat_titleText;
    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_titleText = findViewById(R.id.chat_titleText);
        chat_titleText.setText(getIntent().getStringExtra("title"));

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new ChatAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        ChatData chatData = new ChatData(1,"테스트ID","안녕하세요",true);
        chatAdapter.addItem(chatData);
        chatAdapter.notifyDataSetChanged();
    }
}
