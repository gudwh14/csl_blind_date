package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.csl.csl_blinddate.Adapter.BoardAdapter;
import com.csl.csl_blinddate.Data.BoardData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;
import com.csl.csl_blinddate.Data.UserData;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class CommonBoardActivity extends AppCompatActivity {
    RecyclerView common_recyclerView;
    BoardAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_board);

        int code = getIntent().getIntExtra("code",999);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(code == 0) {
            toolbar.setTitle("즐겨찾기");
        }
        else if(code == 1) {
            toolbar.setTitle("내가 쓴 글");
        }
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view init
        common_recyclerView = findViewById(R.id.common_recyclerView);
        boardAdapter = new BoardAdapter();
        LinearLayoutManager linearLayout = new LinearLayoutManager(CommonBoardActivity.this);
        common_recyclerView.setLayoutManager(linearLayout);
        common_recyclerView.setAdapter(boardAdapter);

        //
        refresh(code);

    }

    public void refresh(int code) {
        boardAdapter.clear();
        boardAdapter.notifyDataSetChanged();

        HashMap<String,Object> data = new HashMap<>();
        data.put("userID", UserData.getInstance().getUserID());
        data.put("code",code);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.CommonBoardRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();
                int size = arrayList.size();
                for(int temp = 0; temp<size; temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    BoardData boardData = new BoardData(repo.getBoard_id(),repo.getBoard_title(),repo.getUserID(),repo.getTitle(),repo.getTime(),repo.getUp(),repo.getComments());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
