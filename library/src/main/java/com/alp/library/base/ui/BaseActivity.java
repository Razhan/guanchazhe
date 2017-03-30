package com.alp.library.base.ui;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alp.library.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IView {

    protected final String TAG = this.getClass().getSimpleName();
    @Nullable
    protected Toolbar toolbar;
    @Nullable
    protected TextView title;
    private boolean translucentStatusBar = false;
    private boolean screenRotate = false;
    private boolean fullScreen = false;
    private boolean doubleClickExit = false;
    private boolean immersiveMode = false;
    private boolean BackPressedOnce = false;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeInitView();
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        initData();
        initView(savedInstanceState);
    }

    private void beforeInitView() {
        if (!screenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (fullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (translucentStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {

    }

    @CallSuper
    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.toolbar_title);

        if (toolbar == null) {
            return;
        }

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBackIcon());
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBackIcon());
        getSupportActionBar().setTitle(null);

        if (title != null) {
            title.setText(setToolBarText());
        }

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    protected boolean isShowBackIcon() {
        return true;
    }

    protected String setToolBarText() {
        return null;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (immersiveMode && hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    @Override
    public void onBackPressed() {
        if (!doubleClickExit) {
            super.onBackPressed();
            return;
        }

        if (BackPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.BackPressedOnce = true;

        showToast(getString(R.string.double_confirm));

        new Handler().postDelayed(() -> BackPressedOnce = false, 1000);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int id) {
        showToast(getString(id));
    }

    public void setTranslucentStatusBar(boolean translucentStatusBar) {
        this.translucentStatusBar = translucentStatusBar;
    }

    public void setScreenRotate(boolean screenRotate) {
        this.screenRotate = screenRotate;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public void setDoubleClickExit(boolean doubleClickExit) {
        this.doubleClickExit = doubleClickExit;
    }

    public void setImmersiveMode(boolean immersiveMode) {
        this.immersiveMode = immersiveMode;
    }

}