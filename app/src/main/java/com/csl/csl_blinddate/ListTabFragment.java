package com.csl.csl_blinddate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTabFragment extends Fragment {
    RecyclerView listRecyclerView;
    ListAdapter listAdapter;
    public ListTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_tab, container, false);
        listRecyclerView = view.findViewById(R.id.ListRecyclerView);
        listAdapter = new ListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listRecyclerView.setLayoutManager(linearLayoutManager);

        listRecyclerView.setAdapter(listAdapter);

        // RecyclerView 에 DATA 넣기
        ListData data = new ListData(getContext(),"서울대학교",true,2,false,false);
        ListData data2 = new ListData(getContext(),"건국대학교",false,3,true,true);
        listAdapter.addItem(data);
        listAdapter.addItem(data2);
        listAdapter.notifyDataSetChanged();

        return view;
    }
}
