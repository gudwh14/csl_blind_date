package com.csl.csl_blinddate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTabFragment extends Fragment {
    RecyclerView listRecyclerView;
    ListAdapter listAdapter;
    Button listWriteButton;
    public ListTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MaterialTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View view = localInflater.inflate(R.layout.fragment_list_tab, container, false);

        // RecyclerView, Adapter 초기화
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

        //
        listWriteButton = view.findViewById(R.id.ListWriteButton);
        listWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ListWriteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
