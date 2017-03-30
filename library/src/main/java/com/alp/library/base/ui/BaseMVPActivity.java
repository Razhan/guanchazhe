package com.alp.library.base.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alp.library.presenter.BasePresenter;

import javax.inject.Inject;

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements MVPView {

    @Inject
    P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();
        presenter.attachView(this);
    }

    protected abstract void initializeInjector();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }

        super.onDestroy();
    }

    @NonNull
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

}
