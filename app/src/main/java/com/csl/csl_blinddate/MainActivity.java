package com.csl.csl_blinddate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView BottomNavView;
    HomeTabFragment homeTabFragment;
    ListTabFragment listTabFragment;
    ChatTabFragment chatTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavView = findViewById(R.id.BottomNavView);
        // fragment 생성
        homeTabFragment = new HomeTabFragment();
        listTabFragment = new ListTabFragment();
        chatTabFragment = new ChatTabFragment();

        // 제일 처음 띄울 fragment 뷰 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameLayout, homeTabFragment).commitAllowingStateLoss();

        // BottomNavigationView 아이콘 선택 리스너
        BottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab1 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameLayout, homeTabFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.tab2 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameLayout, listTabFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.tab3 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameLayout, chatTabFragment).commitAllowingStateLoss();
                    default:
                        return false;
                }
            }
        });

    }
}
