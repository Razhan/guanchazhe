package com.alp.library.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public final class ViewUtil {

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.widthPixels);
    }

    public static float pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px / ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp * ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
