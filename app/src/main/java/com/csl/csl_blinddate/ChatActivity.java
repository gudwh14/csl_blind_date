package com.csl.csl_blinddate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.ChatAdapter;
import com.csl.csl_blinddate.Data.ChatVo;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class ChatActivity extends AppCompatActivity {

    //
    EditText chat_msgText;
    ImageView chat_sendButton;
    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;
    String userID = UserData.getInstance().getUserID();
    int meeting_id;
    String chat_userID,title;
    LinearLayout chat_layout;

    //
    private AtomicInteger verticalScrollOffset = new AtomicInteger(0);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    // Retrofit 정의
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // toolbar init
        title = getIntent().getStringExtra("title");
        Toolbar chat_toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(chat_toolbar);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view init
        meeting_id = getIntent().getIntExtra("meeting_id",0);
        chat_userID = getIntent().getStringExtra("chat_userID");
        myRef = database.getReference("chat_log").child(meeting_id+"");
        chat_msgText = findViewById(R.id.chat_msgText);
        chat_sendButton = findViewById(R.id.chat_sendButton);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new ChatAdapter();
        chat_layout = findViewById(R.id.chat_layout);

        if(getIntent().getStringExtra("title") == null) { // 상대방이 채팅을 나갔을때
            chat_layout.setVisibility(View.INVISIBLE);
        }

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

        //
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

        // 중요
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

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                Log.d("chatlist","key :" +key + "value : " + value);
                ChatVo chatVo = dataSnapshot.getValue(ChatVo.class);
                chatAdapter.addItem(chatVo);
                chatAdapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            Date today = new Date(); // 현재 시각 불러오기
            SimpleDateFormat timeNow = new SimpleDateFormat("a h:mm");
            myRef.push().setValue(new ChatVo(meeting_id,userID,MSG, timeNow.format(today))); // FireBase 에 Chatdata 삽입

            HashMap<String, Object> data = new HashMap<>();   // Retrofit 통신할 HashMap 생성
            data.put("userID",chat_userID);  // HashMap 에 data 넣어주기
            data.put("MSG",MSG);
            data.put("title",title);
            Call<RetrofitRepo> call = retrofitService.ChatFcm(data); // Retrofit call
            call.enqueue(new Callback<RetrofitRepo>() {
                @Override
                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {

                }

                @Override
                public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                    t.printStackTrace(); // 실패했을때 오류 출력
                }
            });
            chat_msgText.setText(""); // 채팅 editText 초기화
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit :
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setMessage("채팅을 종료할까요?\n종료 후 채팅을 이어나갈수 없습니다")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap<String, Object> data = new HashMap<>();
                                data.put("id",meeting_id);
                                data.put("userID",userID);
                                Call<RetrofitRepo> call = retrofitService.ChatRemove(data);
                                call.enqueue(new Callback<RetrofitRepo>() {
                                    @Override
                                    public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                                        RetrofitRepo repo = response.body();
                                        if(repo.isSuccess()) {
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("취소",null)
                        .create()
                        .show();

                break;
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
