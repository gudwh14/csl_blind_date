package com.csl.csl_blinddate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment {
    TabLayout HomeTabLayout;
    ViewPager MainViewPager;

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
        HomeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // ViewPager 초기화
        MainViewPager = view.findViewById(R.id.MainViewPager);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(),HomeTabLayout.getTabCount());
        MainViewPager.setAdapter(tabPagerAdapter);
        // Viewpager Change 리스너 등록
        MainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(HomeTabLayout));

        // TabLayout 선택 리스너 등록
        HomeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
