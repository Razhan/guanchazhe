package com.alp.library.base.helper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;

public class EnhancedFragmentManager {

    private EnhancedFragmentManagerAdapter mAdapter;
    private FragmentManager mFragmentManager;

    @IdRes
    private int mContainerViewId;
    private int mCurrentPosition = 0;

    public EnhancedFragmentManager(FragmentManager fragmentManager, EnhancedFragmentManagerAdapter adapter,
                                   @IdRes int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mAdapter = adapter;
        this.mContainerViewId = containerViewId;
    }

    public void showFragment(int position) {
        showFragment(position, false);
    }

    public void showFragment(int position, boolean reset) {
        showFragment(position, reset, false);
    }

    public void showFragment(int position, boolean reset, boolean allowingStateLoss) {
        this.mCurrentPosition = position;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        int count = mAdapter.getCount();

        for (int i = 0; i < count; i++) {
            if (position == i) {
                if (reset) {
                    remove(position, transaction);
                    add(position, transaction);
                } else {
                    show(i, transaction);
                }
            } else {
                hide(i, transaction);
            }
        }
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public void resetFragments() {
        resetFragments(mCurrentPosition);
    }

    public void resetFragments(int position) {
        resetFragments(position, false);
    }

    public void resetFragments(int position, boolean allowingStateLoss) {
        this.mCurrentPosition = position;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        removeAll(transaction);
        add(position, transaction);

        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public void removeAllFragment() {
        removeAllFragment(false);
    }

    public void removeAllFragment(boolean allowingStateLoss) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        removeAll(transaction);
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public Fragment getCurrentFragment() {
        return getFragment(mCurrentPosition);
    }

    public Fragment getFragment(int position) {
        String tag = mAdapter.getTag(position);
        return mFragmentManager.findFragmentByTag(tag);
    }

    private void show(int position, FragmentTransaction transaction) {
        String tag = mAdapter.getTag(position);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            add(position, transaction);
        } else {
            transaction.show(fragment);
        }
    }

    private void hide(int position, FragmentTransaction transaction) {
        String tag = mAdapter.getTag(position);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.hide(fragment);
        }
    }

    private void add(int position, FragmentTransaction transaction) {
        Fragment fragment = mAdapter.onCreateFragment(position);
        String tag = mAdapter.getTag(position);
        transaction.add(mContainerViewId, fragment, tag);
    }

    private void removeAll(FragmentTransaction transaction) {
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            remove(i, transaction);
        }
    }

    private void remove(int position, FragmentTransaction transaction) {
        String tag = mAdapter.getTag(position);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.remove(fragment);
        }
    }

    public EnhancedFragmentManagerAdapter getAdapter() {
        return mAdapter;
    }

}
