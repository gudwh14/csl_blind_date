package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.csl.csl_blinddate.Adapter.ApplyListAdapter;
import com.csl.csl_blinddate.Data.ApplyListData;
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

public class ApplyListActivity extends AppCompatActivity {

    RecyclerView applyList_RecyclerView;
    ApplyListAdapter applyListAdapter;
    TextView applyList_isEmptyText;

    RetrofitRepo repo;
    ApplyListData applyListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        // toolbar
        Toolbar toolbar = findViewById(R.id.apply_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // View 초기화
        applyList_RecyclerView = findViewById(R.id.applyList_RecyclerView);
        applyListAdapter = new ApplyListAdapter();
        applyList_isEmptyText = findViewById(R.id.applyList_isEmptyText);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyListActivity.this);
        applyList_RecyclerView.setLayoutManager(linearLayoutManager);
        applyList_RecyclerView.setAdapter(applyListAdapter);

        applyList_isEmptyText.setVisibility(View.INVISIBLE);
        applyList_RecyclerView.setVisibility(View.INVISIBLE);

        // refresh()
        refresh();

    }

    public void refresh() {
        // 통신
        applyListAdapter.clear(); // Adapter 초기화
        applyListAdapter.notifyDataSetChanged();

        HashMap<String, Object> data = new HashMap<>();
        data.put("userID", UserData.getInstance().getUserID());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.ListApplyRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();
                int size = arrayList.size();
                if(size==0) {
                    applyList_isEmptyText.setVisibility(View.VISIBLE);
                }
                else {
                    applyList_RecyclerView.setVisibility(View.VISIBLE);
                }
                for(int temp =0; temp<size; temp++) {
                    repo = arrayList.get(temp);
                    applyListData = new ApplyListData(repo.getSchool(),repo.getMember(),repo.getDate(),repo.getApply_status());
                    applyListAdapter.addItem(applyListData);
                }
                applyListAdapter.notifyDataSetChanged();
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
