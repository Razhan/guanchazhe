package com.studio.ran.guanchazhe.news;

import android.app.Fragment;
import android.os.Bundle;

import com.studio.ran.guanchazhe.base.BaseTabFragment;

public class NewsFragment extends BaseTabFragment {

    public static Fragment newInstance() {
          return new NewsFragment();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        pagerAdapter.addFragment(ImageNewsFragment.newInstance(), "滚动");
        pagerAdapter.addFragment(ImageNewsFragment.newInstance(), "时评");
        pagerAdapter.addFragment(ImageNewsFragment.newInstance(), "要闻");
        pagerAdapter.addFragment(ImageNewsFragment.newInstance(), "朋友圈");
        pagerAdapter.addFragment(ImageNewsFragment.newInstance(), "精评");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(pagerAdapter.getCount() / 2);
    }

}
