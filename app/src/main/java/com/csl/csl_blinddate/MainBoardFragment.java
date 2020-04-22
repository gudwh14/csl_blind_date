package com.csl.csl_blinddate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csl.csl_blinddate.Adapter.HomeAdapter;
import com.csl.csl_blinddate.Data.HomeData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainBoardFragment extends Fragment {
    RecyclerView homeRecyclerView;
    HomeAdapter homeAdapter;
    public MainBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_board, container, false);
        homeRecyclerView = view.findViewById(R.id.homeRecyclerView);
        homeAdapter = new HomeAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(linearLayoutManager);

        homeRecyclerView.setAdapter(homeAdapter);

        HomeData homeData = new HomeData(getContext(),"공지사항","테스트1","테스트2","테스트3");
        HomeData homeData2 = new HomeData(getContext(),"자유게시판","테스트4","테스트5","테스트6");
        HomeData homeData3 = new HomeData(getContext(),"OOTD","테스트테스트","테스트테스트","테스트테스트");
        homeAdapter.addItem(homeData);
        homeAdapter.addItem(homeData2);
        homeAdapter.addItem(homeData3);
        homeAdapter.notifyDataSetChanged();


        return view;
    }
}
