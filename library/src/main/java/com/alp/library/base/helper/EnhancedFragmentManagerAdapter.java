package com.alp.library.base.helper;

import android.app.Fragment;

public interface EnhancedFragmentManagerAdapter {

    Fragment onCreateFragment(int position);

    String getTag(int position);

    int getCount();

    int getViewPosition(String tag);

}
