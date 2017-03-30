package com.studio.ran.guanchazhe.news;

import android.app.Fragment;

import com.alp.library.base.ui.BaseFragment;
import com.studio.ran.guanchazhe.R;

public class ImageNewsFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new ImageNewsFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_news_image;
    }
}
