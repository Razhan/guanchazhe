package com.alp.library.presenter;

import android.app.Activity;

import com.alp.library.base.ui.MVPRefreshLoadView;

public abstract class BaseRefreshLoadPresenter<V extends MVPRefreshLoadView> extends BaseLoadPresenter<V> {

    public BaseRefreshLoadPresenter(Activity activity) {
        super(activity);
    }

}

