package com.alp.library.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alp.library.base.ui.MVPLoadView;

public abstract class BaseLoadPresenter<V extends MVPLoadView> extends BasePresenter<V> {

    public BaseLoadPresenter(Activity activity) {
        super(activity);
    }

    public String showErrorView(Throwable throwable, boolean showToast) {
        String error = errorHandler.getErrorMessage(activity, throwable);

        if (!TextUtils.isEmpty(error)) {
            Log.d("error", error);
            getView().showError(error, showToast);
        }

        return error;
    }

    abstract public void loadData();

}
