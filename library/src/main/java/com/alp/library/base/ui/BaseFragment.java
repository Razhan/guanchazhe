package com.alp.library.base.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements IView {

    private static final String SAVED_STATE = "savedState";
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(SAVED_STATE);

            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            if (isHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, rootView);

        initData();
        initView(savedInstanceState);

        return rootView;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    public Context getContext() {
        return activity;
    }

    public void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int id) {
        showToast(getString(id));
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SAVED_STATE, isHidden());
    }

}
