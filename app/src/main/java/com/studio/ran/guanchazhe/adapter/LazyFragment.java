package com.studio.ran.guanchazhe.adapter;

import com.alp.library.base.ui.BaseMVPLoadFragment;
import com.alp.library.presenter.BaseLoadPresenter;

public abstract class LazyFragment<T, P extends BaseLoadPresenter> extends BaseMVPLoadFragment<T, P> {

    private boolean isVisible = false;
    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        if (!isVisible || !isFirstLoad) {
            return;
        }

        isFirstLoad = false;

        lazyLoad();
    }

    protected void onInvisible() {

    }

    protected void lazyLoad() {
        getPresenter().loadData();
    }

}
