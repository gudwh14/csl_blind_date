package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.CommentAdapter;
import com.csl.csl_blinddate.Data.CommentData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;

import java.util.ArrayList;
import java.util.HashMap;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class BoardViewActivity extends AppCompatActivity {

    Drawable up_drawable;
    Drawable favorite_before_drawable;
    Drawable favorite_after_drawable;
    TextView boardView_titleText;
    TextView boardView_userText;
    TextView boardView_timeText;
    TextView boardView_upText;
    TextView boardView_favoriteText;
    TextView boardView_mainText;
    TextView boardView_up_countText;
    TextView boardView_favorite_countText;
    RecyclerView boardView_recyclerView;
    CommentAdapter commentAdapter;
    ImageView boardView_commentSendView;
    EditText boardView_commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);

        // toolbar
        Toolbar toolbar = findViewById(R.id.boardView_toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view Init
        up_drawable = getApplicationContext().getResources().getDrawable(R.drawable.heart_icon);
        favorite_before_drawable = getApplicationContext().getResources().getDrawable(R.drawable.favorite_before_icon);
        favorite_after_drawable = getApplicationContext().getResources().getDrawable(R.drawable.favorite_after_icon);

        up_drawable.setBounds(0,0,40,40);
        favorite_before_drawable.setBounds(0,0,40,40);
        favorite_after_drawable.setBounds(0,0,40,40);

        boardView_titleText = findViewById(R.id.boardView_titleText);
        boardView_userText = findViewById(R.id.boardView_userText);
        boardView_timeText = findViewById(R.id.boardView_timeText);
        boardView_upText = findViewById(R.id.boardView_upText);
        boardView_favoriteText = findViewById(R.id.boardView_favoriteText);
        boardView_mainText = findViewById(R.id.boardView_mainText);
        boardView_up_countText = findViewById(R.id.boardView_up_countText);
        boardView_favorite_countText = findViewById(R.id.boardView_favorite_countText);
        boardView_commentSendView = findViewById(R.id.boardView_commentSendView);
        boardView_commentText = findViewById(R.id.boardView_commentText);


        boardView_upText.setCompoundDrawables(up_drawable,null,null,null);
        boardView_favoriteText.setCompoundDrawables(favorite_before_drawable,null,null,null);
        boardView_up_countText.setCompoundDrawables(up_drawable,null,null,null);
        boardView_favorite_countText.setCompoundDrawables(favorite_after_drawable,null,null,null);

        boardView_recyclerView = findViewById(R.id.boardView_recyclerView);
        commentAdapter = new CommentAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        boardView_recyclerView.setLayoutManager(linearLayoutManager);
        boardView_recyclerView.setAdapter(commentAdapter);

        // 즐겨 찾기 클릭 리스너
        boardView_favoriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardView_favoriteText.setCompoundDrawables(favorite_after_drawable,null,null,null);
            }
        });

        // 댓글 작성 클릭 리스너
        boardView_commentSendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        // refresh
        refresh();
        commentRefresh();
    }

    public void refresh() {
        HashMap<String,Object> data = new HashMap<>();
        data.put("id",getIntent().getIntExtra("board_id",0));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.BoardViewRefresh(data);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                boardView_titleText.setText(repo.getTitle());
                if((getIntent().getStringExtra("title")).equals("익명게시판")) {
                    boardView_userText.setText("익명");
                }
                else {
                    boardView_userText.setText(repo.getUserID() + " 님");
                }
                boardView_mainText.setText(repo.getMainText());
                boardView_up_countText.setText(" "+repo.getUp());
                boardView_favorite_countText.setText(" "+repo.getFavorite());
                String[] time = repo.getTime().split(" ");
                boardView_timeText.setText(time[1]+" "+time[2]);
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void send() {
        String comment = boardView_commentText.getText().toString();
        if(comment.trim().equals("")) {
            Toast.makeText(getApplicationContext(),"내용을 입력하세요",Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("board_id",getIntent().getIntExtra("board_id",0));
            data.put("userID",SplashActivity.userData.getUserID());
            data.put("comment",comment);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<RetrofitRepo> call = retrofitService.CommentInsert(data);
            call.enqueue(new Callback<RetrofitRepo>() {
                @Override
                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                    boardView_commentText.setText("");
                    RetrofitRepo repo = response.body();
                    if(!(repo.isSuccess())) {
                        Toast.makeText(getApplicationContext(),"통신 오류",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        commentRefresh();
                    }
                }

                @Override
                public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void commentRefresh() {
        commentAdapter.clear();
        commentAdapter.notifyDataSetChanged();

        HashMap<String,Object> data = new HashMap<>();
        data.put("board_id",getIntent().getIntExtra("board_id",0));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.CommentRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();

                for (int temp = 0; temp < arrayList.size(); temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    CommentData commentData = new CommentData(repo.getId(),repo.getUserID(),repo.getTime(),repo.getUp(),repo.getComment(),repo.isReply());
                    commentAdapter.addItem(commentData);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
