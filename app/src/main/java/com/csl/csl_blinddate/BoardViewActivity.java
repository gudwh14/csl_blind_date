package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csl.csl_blinddate.Data.RetrofitRepo;

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


        boardView_upText.setCompoundDrawables(up_drawable,null,null,null);
        boardView_favoriteText.setCompoundDrawables(favorite_before_drawable,null,null,null);
        boardView_up_countText.setCompoundDrawables(up_drawable,null,null,null);
        boardView_favorite_countText.setCompoundDrawables(favorite_after_drawable,null,null,null);

        //
        boardView_favoriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardView_favoriteText.setCompoundDrawables(favorite_after_drawable,null,null,null);
            }
        });

        // refresh
        refresh();
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
                boardView_userText.setText(repo.getUserID()+" 님");
                boardView_mainText.setText(repo.getMainText());
                boardView_up_countText.setText(repo.getUp()+"");
                boardView_favorite_countText.setText(repo.getFavorite()+"");
                String[] time = repo.getTime().split(" ");
                boardView_timeText.setText(time[1]+" "+time[2]);
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
