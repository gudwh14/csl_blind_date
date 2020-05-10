package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csl.csl_blinddate.Adapter.ApplyAdapter;
import com.csl.csl_blinddate.Data.ApplyData;

public class ApplyedListActivity extends AppCompatActivity {
    RecyclerView applyRecyclerView;
    ApplyAdapter applyAdapter;
    TextView applyedList_isEmptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyed_list);

        // View 초기화
        applyedList_isEmptyText = findViewById(R.id.applyedList_isEmptyText);
        applyRecyclerView = findViewById(R.id.applyRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyedListActivity.this);
        applyRecyclerView.setLayoutManager(linearLayoutManager);

        applyAdapter = new ApplyAdapter();
        applyRecyclerView.setAdapter(applyAdapter);

        applyedList_isEmptyText.setVisibility(View.INVISIBLE);
        //
        ApplyData applyData = new ApplyData(22,"목원대학교",true,"안녕하세요 저희는 22살 목원대 xx과 입니다\n재밌게 한번 같이 놀아요!");
        applyAdapter.addItem(applyData);

        applyAdapter.notifyDataSetChanged();

    }
}
