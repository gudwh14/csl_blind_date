package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.csl.csl_blinddate.Adapter.ApplyListAdapter;
import com.csl.csl_blinddate.Data.ApplyListData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);

        applyList_RecyclerView = findViewById(R.id.applyList_RecyclerView);
        applyListAdapter = new ApplyListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyListActivity.this);
        applyList_RecyclerView.setLayoutManager(linearLayoutManager);
        applyList_RecyclerView.setAdapter(applyListAdapter);

        refresh();

    }

    public void refresh() {
        // 통신
        applyListAdapter.clear(); // Adapter 초기화
        applyListAdapter.notifyDataSetChanged();

        HashMap<String, Object> data = new HashMap<>();
        data.put("userID",SplashActivity.userData.getUserID());

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
                for(int temp =0; temp<arrayList.size(); temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    ApplyListData applyListData = new ApplyListData(repo.getSchool(),repo.getMember(),repo.getDate(),repo.getApply_status());
                    applyListAdapter.addItem(applyListData);
                }
                applyListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {

            }
        });

    }
}
