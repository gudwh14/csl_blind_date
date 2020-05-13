package com.csl.csl_blinddate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.ChatAdapter;
import com.csl.csl_blinddate.Data.ChatData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import gun0912.tedkeyboardobserver.BaseKeyboardObserver;
import gun0912.tedkeyboardobserver.TedKeyboardObserver;
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

    private AtomicInteger verticalScrollOffset = new AtomicInteger(0);

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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
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

        //refresh
        refresh();

        /*TedKeyboardObserver tedKeyboardObserver = new TedKeyboardObserver(this);
        tedKeyboardObserver.listen(new BaseKeyboardObserver.OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean b) {
                if(b) {
                    chatRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
                }
            }
        }); */

        chatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                final int y = i7-i3;
                if(Math.abs(y) > 0) {
                    chatRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if(y > 0 || Math.abs(verticalScrollOffset.get()) >= Math.abs(y)) {
                                chatRecyclerView.scrollBy(0,y);
                            }
                            else {
                                chatRecyclerView.scrollBy(0,verticalScrollOffset.get());
                            }
                        }
                    });
                }
            }
        });

        chatRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            AtomicInteger state = new AtomicInteger(RecyclerView.SCROLL_STATE_IDLE);
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                state.compareAndSet(RecyclerView.SCROLL_STATE_IDLE, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE :
                        if (!state.compareAndSet(RecyclerView.SCROLL_STATE_SETTLING, newState)) {
                            state.compareAndSet(RecyclerView.SCROLL_STATE_DRAGGING, newState);
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING :
                        state.compareAndSet(RecyclerView.SCROLL_STATE_IDLE, newState);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING :
                        state.compareAndSet(RecyclerView.SCROLL_STATE_DRAGGING, newState);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(state.get() != RecyclerView.SCROLL_STATE_IDLE) {
                    verticalScrollOffset.getAndAdd(dy);
                }
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
                    else {
                        refresh();
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

        HashMap<String,Object> data = new HashMap<>();
        data.put("id",getIntent().getIntExtra("meeting_id",0));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.ChatRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();

                for(int temp = 0; temp <arrayList.size(); temp ++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    ChatData chatData = new ChatData(repo.getUserID(),repo.getChatMSG(),repo.getTime(),repo.getUserID().equals(SplashActivity.userData.getUserID()));
                    chatAdapter.addItem(chatData);
                }
                chatAdapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                t.printStackTrace();
            }
        });



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
