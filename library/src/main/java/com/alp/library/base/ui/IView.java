package com.alp.library.base.ui;

import android.os.Bundle;

public interface IView {

    int getContentViewId();

    void initData();

    void initView(Bundle savedInstanceState);

}
