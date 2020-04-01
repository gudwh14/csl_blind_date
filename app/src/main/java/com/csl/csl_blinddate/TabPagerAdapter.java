package com.csl.csl_blinddate;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                HomeTabFragment homeTabFragment = new HomeTabFragment();
                return  homeTabFragment;
            case 1 :
                ListTabFragment listTabFragment = new ListTabFragment();
                return  listTabFragment;
            case 2 :
                ChatTabFragment chatTabFragment = new ChatTabFragment();
                return  chatTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
