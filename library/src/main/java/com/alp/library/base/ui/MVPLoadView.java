package com.alp.library.base.ui;

import android.support.annotation.UiThread;

public interface MVPLoadView<T> extends MVPView {

    @UiThread
    void showLoading(boolean pullToRefresh);

    @UiThread
    void showContent(T data);

    @UiThread
    void showError(String errorMessage, boolean showToast);

    @UiThread
    void loadData(boolean pullToRefresh);

    boolean isShowContent();

}
