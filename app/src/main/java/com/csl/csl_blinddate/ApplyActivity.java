package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.csl.csl_blinddate.Adapter.ApplyAdapter;
import com.csl.csl_blinddate.Data.ApplyData;

public class ApplyActivity extends AppCompatActivity {
    RecyclerView applyRecyclerView;
    ApplyAdapter applyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        applyRecyclerView = findViewById(R.id.applyRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyActivity.this);
        applyRecyclerView.setLayoutManager(linearLayoutManager);

        applyAdapter = new ApplyAdapter();
        applyRecyclerView.setAdapter(applyAdapter);

        ApplyData applyData = new ApplyData(22,"목원대학교",true,"안녕하세요 저희는 22살 목원대 xx과 입니다\n재밌게 한번 같이 놀아요!");
        applyAdapter.addItem(applyData);

        applyAdapter.notifyDataSetChanged();

    }
}
