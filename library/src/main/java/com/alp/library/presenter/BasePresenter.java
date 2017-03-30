package com.alp.library.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alp.library.base.ui.MVPView;
import com.alp.library.exception.IErrorHandler;

import javax.inject.Inject;

public abstract class BasePresenter<V extends MVPView> implements IPresenter {

    protected V view;
    protected Activity activity;

    @Inject
    IErrorHandler errorHandler;

    public BasePresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void attachView(MVPView view) {
        this.view = (V) view;
    }

    @Override
    public void detachView() {
        activity = null;
        view = null;
        unSubscribe();
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public Context getContext() {
        return activity;
    }

    public String showErrorMessage(Throwable throwable) {
        String error = errorHandler.getErrorMessage(activity, throwable);

        if (!TextUtils.isEmpty(error)) {
            Log.d("error", error);
            getView().showMessage(error);
        }

        return error;
    }

}
