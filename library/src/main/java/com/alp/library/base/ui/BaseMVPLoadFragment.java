package com.alp.library.base.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alp.library.R;
import com.alp.library.presenter.BaseLoadPresenter;
import com.alp.library.utils.LoadAnimationUtil;

public abstract class BaseMVPLoadFragment<T, P extends BaseLoadPresenter> extends BaseMVPFragment<P>
        implements MVPLoadView<T> {

    protected View loadingView;
    protected View contentView;
    protected TextView errorView;

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingView = view.findViewById(R.id.loading_view);
        contentView = view.findViewById(R.id.content_view);
        errorView = (TextView) view.findViewById(R.id.error_view);

        if (loadingView == null) {
            throw new NullPointerException("Loading view is null!");
        }

        if (contentView == null) {
            throw new NullPointerException("Content view is null!");
        }

        if (errorView == null) {
            throw new NullPointerException("Error view is null!");
        }

        errorView.setOnClickListener(v -> onErrorViewClicked());
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (!pullToRefresh) {
            LoadAnimationUtil.showLoading(loadingView, contentView, errorView);
        }
    }

    @CallSuper
    @Override
    public void showContent(T data) {
        LoadAnimationUtil.showContent(loadingView, contentView, errorView);
    }

    @Override
    public void showError(String errorMessage, boolean pullToRefresh) {
        if (pullToRefresh) {
            showToast(errorMessage);
        } else {
            errorView.setText(errorMessage + "\nClick here to retry");
            LoadAnimationUtil.showErrorView(loadingView, contentView, errorView);
        }
    }

    @Override
    public boolean isShowContent() {
        return contentView.getVisibility() == View.VISIBLE;
    }

    private void onErrorViewClicked() {
        loadData(false);
    }

    @Override
    public void onDestroyView() {
        loadingView = null;
        contentView = null;
        errorView.setOnClickListener(null);
        errorView = null;

        super.onDestroyView();
    }

}
