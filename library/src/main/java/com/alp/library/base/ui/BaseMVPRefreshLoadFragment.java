package com.alp.library.base.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.alp.library.R;
import com.alp.library.presenter.BaseRefreshLoadPresenter;
import com.alp.library.utils.LoadAnimationUtil;

public abstract class BaseMVPRefreshLoadFragment<T, P extends BaseRefreshLoadPresenter> extends BaseMVPLoadFragment<T, P>
        implements SwipeRefreshLayout.OnRefreshListener, MVPRefreshLoadView<T> {

    protected SwipeRefreshLayout refresh;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refresh = (SwipeRefreshLayout) getView().findViewById(R.id.refresh);

        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary));
    }

    @CallSuper
    @Override
    public void showContent(T data) {
        hideRefresh();
        super.showContent(data);
    }

    @CallSuper
    @Override
    public void showError(String errorMessage, boolean showToast) {
        hideRefresh();

        if (showToast || isShowContent()) {
            showToast(errorMessage);
        } else {
            errorView.setText(errorMessage + "\nClick here to retry");
            LoadAnimationUtil.showErrorView(loadingView, contentView, errorView);
        }
    }

    @CallSuper
    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void hideRefresh() {
        refresh.setRefreshing(false);
    }
}
