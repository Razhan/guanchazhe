package com.studio.ran.guanchazhe.base;

import com.alp.library.base.ui.BaseMVPRefreshLoadFragment;
import com.alp.library.presenter.BaseRefreshLoadPresenter;

public abstract class LazyRefreshLoadFragment<T, P extends BaseRefreshLoadPresenter> extends BaseMVPRefreshLoadFragment<T, P> {

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
