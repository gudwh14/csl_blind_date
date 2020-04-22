package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.csl.csl_blinddate.Adapter.ApplyListAdapter;
import com.csl.csl_blinddate.Data.ApplyListData;

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

        ApplyListData applyListData = new ApplyListData("서강대학교",2,"1일전",1);
        ApplyListData applyListData2 = new ApplyListData("단국대학교",3,"2일전",2);
        applyListAdapter.addItem(applyListData);
        applyListAdapter.addItem(applyListData2);
        applyListAdapter.notifyDataSetChanged();

    }
}
