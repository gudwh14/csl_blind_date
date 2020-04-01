package com.csl.csl_blinddate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment {
    TabLayout HomeTabLayout;

    public HomeTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        // HomeTabLayout 초기화
        HomeTabLayout = view.findViewById(R.id.HomeTabLayout);
        HomeTabLayout.addTab(HomeTabLayout.newTab().setText("전체"));
        HomeTabLayout.addTab(HomeTabLayout.newTab().setText("게시판"));

        return view;
    }
}
