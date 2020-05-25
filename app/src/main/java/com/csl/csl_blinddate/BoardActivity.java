package com.csl.csl_blinddate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.csl.csl_blinddate.Adapter.BoardAdapter;
import com.csl.csl_blinddate.Data.BoardData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;
import com.csl.csl_blinddate.Data.UserData;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class BoardActivity extends AppCompatActivity {

    MaterialButton Board_WriteButton;
    RecyclerView board_recyclerView;
    BoardAdapter boardAdapter;
    private int REQUEST_WRITE = 1;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // Toolbar 를 Appbar 로 지정하기
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view 초기화
        Board_WriteButton = findViewById(R.id.Board_WriteButton);
        title = getIntent().getStringExtra("title");

        board_recyclerView = findViewById(R.id.board_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BoardActivity.this);
        boardAdapter = new BoardAdapter();

        board_recyclerView.setLayoutManager(linearLayoutManager);
        board_recyclerView.setAdapter(boardAdapter);

        if (title.equals("공지사항") && !(UserData.getInstance().getUserID().equals("운영자"))) {
            Board_WriteButton.setVisibility(View.INVISIBLE);
        }

        //
        Board_WriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, BoardWriteActivity.class);
                intent.putExtra("title", title);
                startActivityForResult(intent, REQUEST_WRITE);
            }
        });

        //
        refresh();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WRITE) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }
    }

    public void refresh() {
        boardAdapter.clear();
        boardAdapter.notifyDataSetChanged();

        HashMap<String,Object> data = new HashMap<>();
        data.put("board_title",title);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.BoardRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();

                int size = arrayList.size();
                for(int temp = 0; temp<size; temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    BoardData boardData = new BoardData(repo.getBoard_id(),title,repo.getUserID(),repo.getTitle(),repo.getTime(),repo.getUp(),repo.getComments());
                    boardAdapter.addItem(boardData);
                }
                boardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}