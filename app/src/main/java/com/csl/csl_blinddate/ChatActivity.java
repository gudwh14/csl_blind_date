package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.ChatAdapter;
import com.csl.csl_blinddate.Data.ChatData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class ChatActivity extends AppCompatActivity {

    EditText chat_msgText;
    MaterialButton chat_sendButton;
    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // toolbar init
        Toolbar chat_toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(chat_toolbar);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view init
        chat_msgText = findViewById(R.id.chat_msgText);
        chat_sendButton = findViewById(R.id.chat_sendButton);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new ChatAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(chatAdapter);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // send
        chat_sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    public void send() {
        String MSG = chat_msgText.getText().toString();
        if(MSG.trim().equals("")) {
            Toast.makeText(ChatActivity.this,"공백은 전송할수 없습니다",Toast.LENGTH_SHORT).show();
            chat_msgText.setText("");
        }
        else {
            HashMap<String,Object> data = new HashMap<>();
            data.put("MSG",MSG);
            data.put("userID",SplashActivity.userData.getUserID());
            data.put("id",getIntent().getIntExtra("meeting_id",0));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<RetrofitRepo> call = retrofitService.ChatInsert(data);
            call.enqueue(new Callback<RetrofitRepo>() {
                @Override
                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                    chat_msgText.setText("");
                    RetrofitRepo repo = response.body();
                    if(!repo.isSuccess()) {
                        Toast.makeText(ChatActivity.this,"통신 오류",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }

    public void refresh() {
        chatAdapter.clear();
        chatAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
