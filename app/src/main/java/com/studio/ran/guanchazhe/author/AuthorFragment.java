package com.studio.ran.guanchazhe.author;

import android.app.Fragment;

import com.alp.library.base.ui.BaseFragment;
import com.studio.ran.guanchazhe.R;

public class AuthorFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new AuthorFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_author;
    }
}
