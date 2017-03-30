package com.alp.library.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.alp.library.R;

public class LoadAnimationUtil {

    private LoadAnimationUtil() {
    }

    public static void showLoading(@NonNull View loadingView, @NonNull View contentView,
                                   @NonNull View errorView) {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    public static void showErrorView(@NonNull final View loadingView, @NonNull final View contentView,
                                     final View errorView) {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        ObjectAnimator in = ObjectAnimator.ofFloat(errorView, View.ALPHA, 1f);

        in.setDuration(200);

        in.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                errorView.setVisibility(View.VISIBLE);
            }
        });

        in.start();
    }

    public static void showContent(@NonNull final View loadingView, @NonNull final View contentView,
                                   @NonNull final View errorView) {
        if (contentView.getVisibility() == View.VISIBLE) {
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        } else {

            errorView.setVisibility(View.GONE);

            final Resources resources = loadingView.getResources();
            final int translateInPixels = resources.getDimensionPixelSize(R.dimen.load_animation_translate_y);
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView, View.ALPHA, 0f, 1f);
            ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(contentView, View.TRANSLATION_Y,
                    translateInPixels, 0);

            ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView, View.ALPHA, 1f, 0f);
            ObjectAnimator loadingTranslateOut = ObjectAnimator.ofFloat(loadingView, View.TRANSLATION_Y, 0,
                    -translateInPixels);

            set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut, loadingTranslateOut);
            set.setDuration(500);

            set.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    contentView.setTranslationY(0);
                    loadingView.setTranslationY(0);
                    contentView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingView.setVisibility(View.GONE);
                    loadingView.setAlpha(1f);
                    contentView.setTranslationY(0);
                    loadingView.setTranslationY(0);
                }
            });

            set.start();
        }
    }
}
