package com.csl.csl_blinddate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    long time = 0;
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

        Bundle bundle = new Bundle();
        bundle.putParcelable("userData",getIntent().getParcelableExtra("userData"));
        homeTabFragment.setArguments(bundle);
        listTabFragment.setArguments(bundle);
        chatTabFragment.setArguments(bundle);

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
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=1000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<1000){
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}
