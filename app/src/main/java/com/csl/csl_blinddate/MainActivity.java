package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout MainTabLayout;
    ViewPager MainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainTabLayout = findViewById(R.id.MainTabLayout); // TabLayout 초기화
        MainTabLayout.addTab(MainTabLayout.newTab().setText("1"));
        MainTabLayout.addTab(MainTabLayout.newTab().setText("2"));
        MainTabLayout.addTab(MainTabLayout.newTab().setText("3"));

        MainViewPager = findViewById(R.id.MainViewPager); // ViewPager 초기화
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),MainTabLayout.getTabCount());
        MainViewPager.setAdapter(tabPagerAdapter);
        MainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(MainTabLayout));


        MainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }
}
