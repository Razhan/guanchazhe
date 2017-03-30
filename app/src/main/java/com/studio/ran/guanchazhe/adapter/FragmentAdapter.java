package com.studio.ran.guanchazhe.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;
    private int pageCount = 0;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        pageCount++;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
