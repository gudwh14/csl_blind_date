package com.csl.csl_blinddate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csl.csl_blinddate.Adapter.BoardAdapter;
import com.csl.csl_blinddate.Adapter.CommentAdapter;
import com.csl.csl_blinddate.Data.CommentData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;
import com.csl.csl_blinddate.Data.UserData;

import java.util.ArrayList;
import java.util.HashMap;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class BoardViewActivity extends AppCompatActivity {
    long time = 0;
    Drawable up_drawable;
    Drawable up_after_drawable;
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
    ImageView uploaded_imageView;
    EditText boardView_commentText;
    String userID = UserData.getInstance().getUserID();
    String board_userID;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    int favorite = 0, up = 0;
    int board_id;
    String title;

    RetrofitRepo repo;
    CommentData commentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        title = getIntent().getStringExtra("title");
        // toolbar
        Toolbar toolbar = findViewById(R.id.boardView_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view Init
        board_id = getIntent().getIntExtra("board_id",0);
        up_drawable = getApplicationContext().getResources().getDrawable(R.drawable.heart_icon);
        up_after_drawable = getApplicationContext().getResources().getDrawable(R.drawable.heart_after_icon);
        favorite_before_drawable = getApplicationContext().getResources().getDrawable(R.drawable.favorite_before_icon);
        favorite_after_drawable = getApplicationContext().getResources().getDrawable(R.drawable.favorite_after_icon);

        up_drawable.setBounds(0,0,40,40);
        up_after_drawable.setBounds(0,0,40,40);
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
        uploaded_imageView =findViewById(R.id.uploaded_imageView);


        boardView_upText.setCompoundDrawables(up_drawable,null,null,null);
        boardView_favoriteText.setCompoundDrawables(favorite_before_drawable,null,null,null);
        boardView_up_countText.setCompoundDrawables(up_drawable,null,null,null);
        boardView_favorite_countText.setCompoundDrawables(favorite_after_drawable,null,null,null);

        boardView_recyclerView = findViewById(R.id.boardView_recyclerView);
        commentAdapter = new CommentAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        boardView_recyclerView.setLayoutManager(linearLayoutManager);
        boardView_recyclerView.setAdapter(commentAdapter);

        commentAdapter.setOnRefreshChanged(new CommentAdapter.OnRefreshChanged() {
            @Override
            public void onRefreshChanged(boolean refresh) {
                if(refresh) {
                    commentRefresh();
                }
            }
        });
        // 즐겨 찾기 클릭 리스너
        boardView_favoriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favorite == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
                    builder.setMessage("이글을 즐겨찾기에 추가 하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DetailInert(1,favorite,"");
                                    refresh();
                                }
                            })
                            .setNegativeButton("취소",null)
                            .create()
                            .show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
                    builder.setMessage("이글을 즐겨찾기에 삭제 하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DetailInert(1,favorite,"");
                                    refresh();
                                }
                            })
                            .setNegativeButton("취소",null)
                            .create()
                            .show();
                }
            }
        });

        // 좋아요 클릭 리스너
        boardView_upText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis()-time>=1000){
                    time=System.currentTimeMillis();
                    DetailInert(2,up,"");
                    refresh();
                }else if(System.currentTimeMillis()-time<1000){
                    Toast.makeText(getApplicationContext(),"잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                }
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
        data.put("id",board_id);
        data.put("userID",userID);

        Call<RetrofitRepo> call = retrofitService.BoardViewRefresh(data);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                repo = response.body();
                boardView_titleText.setText(repo.getTitle());
                if((getIntent().getStringExtra("title")).equals("익명게시판")) {
                    boardView_userText.setText("익명");
                }
                else {
                    board_userID = repo.getUserID();
                    boardView_userText.setText(repo.getUserID() + " 님");
                }

                if(repo.isFavorite()) {
                    favorite = 1;
                    boardView_favoriteText.setCompoundDrawables(favorite_after_drawable,null,null,null);
                }
                else {
                    favorite = 0;
                    boardView_favoriteText.setCompoundDrawables(favorite_before_drawable,null,null,null);
                }

                if(repo.isBoardUP()) {
                    up = 1;
                    boardView_upText.setCompoundDrawables(up_after_drawable,null,null,null);
                }
                else {
                    up = 0;
                    boardView_upText.setCompoundDrawables(up_drawable,null,null,null);
                }

                boardView_mainText.setText(repo.getMainText());
                boardView_up_countText.setText(" "+repo.getUp());
                boardView_favorite_countText.setText(" "+repo.getFavorite());
                String[] time = repo.getTime().split(" ");
                boardView_timeText.setText(time[1]+" "+time[2]);

                String image_path = repo.getImage_path();
                if( !(image_path.equals("")) ) {
                    Glide.with(BoardViewActivity.this).load(URL+"photos/"+image_path).into(uploaded_imageView);
                    uploaded_imageView.setVisibility(View.VISIBLE);
                }
                else {
                    uploaded_imageView.setVisibility(View.GONE);
                }
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
            boolean anonymous = false;
            if(title.equals("익명게시판")) {
                anonymous = true;
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("board_id",board_id);
            data.put("userID",userID);
            data.put("comment",comment);
            data.put("anonymous",anonymous);


            Call<RetrofitRepo> call = retrofitService.CommentInsert(data);
            call.enqueue(new Callback<RetrofitRepo>() {
                @Override
                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                    boardView_commentText.setText("");
                    repo = response.body();
                    if(!(repo.isSuccess())) {
                        Toast.makeText(getApplicationContext(),"인터넷 연결을 확인해 주세요",Toast.LENGTH_SHORT).show();
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
        data.put("board_id",board_id);
        data.put("userID",userID);

        Call<RetrofitRepoList> call = retrofitService.CommentRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();
                int size = arrayList.size();
                for (int temp = 0; temp < size; temp++) {
                    repo = arrayList.get(temp);
                    commentData = new CommentData(title,getIntent().getIntExtra("board_id",0),repo.getId(),repo.getUserID(),repo.getTime(),repo.getUp(),repo.getComment(),repo.isReply(),repo.isAnonymous(),repo.getAnony_count(),repo.isWriter(),repo.isCommentUp());
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

    public void DetailInert(final int code,int on,String reason) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",board_id);
        data.put("userID",userID);
        data.put("code",code);
        data.put("on",on);
        data.put("reason",reason);

        Call<RetrofitRepo> call = retrofitService.BoardDetailInsert(data);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                if(!repo.isSuccess()) {
                    if(code == 4) {
                        Toast.makeText(BoardViewActivity.this,"이미 신고한 게시글입니다",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
        try {
            Thread.sleep(150) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
            case R.id.board_remove :
                if(userID.equals(board_userID)) {
                    DetailInert(5, 0, "");
                    finish();
                }
                else {
                    Toast.makeText(BoardViewActivity.this,"글 작성자가 아닙니다",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.board_report :
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View layout = layoutInflater.inflate(R.layout.report,null);

                final AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
                builder.setView(layout);

                final TextView report_1 = layout.findViewById(R.id.report_1);
                final TextView report_2 = layout.findViewById(R.id.report_2);
                final TextView report_3 = layout.findViewById(R.id.report_3);
                final TextView report_4 = layout.findViewById(R.id.report_4);

                final AlertDialog alertDialog = builder.create();

                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String reason = "";
                        switch (view.getId()) {
                            case R.id.report_1 :
                                reason = report_1.getText().toString();
                                break;
                            case R.id.report_2 :
                                reason = report_2.getText().toString();
                                break;
                            case R.id.report_3 :
                                reason = report_3.getText().toString();
                                break;
                            case R.id.report_4 :
                                reason = report_4.getText().toString();
                                break;
                        }
                        DetailInert(4,0,reason);
                        alertDialog.dismiss();
                    }
                };

                report_1.setOnClickListener(onClickListener);
                report_2.setOnClickListener(onClickListener);
                report_3.setOnClickListener(onClickListener);
                report_4.setOnClickListener(onClickListener);

                alertDialog.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
