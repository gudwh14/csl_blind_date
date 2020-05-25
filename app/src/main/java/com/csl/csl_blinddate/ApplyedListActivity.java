package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.csl.csl_blinddate.Adapter.ApplyAdapter;
import com.csl.csl_blinddate.Data.ApplyData;
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

public class ApplyedListActivity extends AppCompatActivity {
    RecyclerView applyRecyclerView;
    ApplyAdapter applyAdapter;
    TextView applyedList_isEmptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyed_list);
        // toolbar
        Toolbar toolbar = findViewById(R.id.applyed_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // View 초기화
        applyedList_isEmptyText = findViewById(R.id.applyedList_isEmptyText);
        applyRecyclerView = findViewById(R.id.applyRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyedListActivity.this);
        applyRecyclerView.setLayoutManager(linearLayoutManager);

        applyAdapter = new ApplyAdapter();
        applyRecyclerView.setAdapter(applyAdapter);

        applyedList_isEmptyText.setVisibility(View.INVISIBLE);
        applyRecyclerView.setVisibility(View.INVISIBLE);

        // refresh

        refresh();

    }

    public void refresh() {
        applyAdapter.clear(); // 초기화
        applyAdapter.notifyDataSetChanged();

        HashMap<String, Object> data = new HashMap<>();
        data.put("userID", UserData.getInstance().getUserID());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.ListApplyedRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();

                if(arrayList.isEmpty()) {
                    applyedList_isEmptyText.setVisibility(View.VISIBLE);
                }
                else {
                    applyRecyclerView.setVisibility(View.VISIBLE);
                }

                int size = arrayList.size();
                for (int temp = 0 ; temp < size; temp ++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    ApplyData applyData = new ApplyData(repo.getApply_id(),repo.getAge(),repo.getSchool(),repo.isCertification(),repo.getComment());
                    applyAdapter.addItem(applyData);
                }
                applyAdapter.notifyDataSetChanged();
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
