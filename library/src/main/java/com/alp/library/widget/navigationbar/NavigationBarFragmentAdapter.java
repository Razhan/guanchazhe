package com.alp.library.widget.navigationbar;

import android.app.Fragment;
import android.text.TextUtils;

import com.alp.library.base.helper.EnhancedFragmentManagerAdapter;

import java.util.List;

public class NavigationBarFragmentAdapter implements EnhancedFragmentManagerAdapter {

    private List<Fragment> fragments;
    private List<Integer> drawables;
    private List<String> tags;

    public NavigationBarFragmentAdapter(List<Fragment> fragments, List<Integer> drawables,
                                        List<String> tags) {
        this.fragments = fragments;
        this.drawables = drawables;
        this.tags = tags;
    }

    @Override
    public Fragment onCreateFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public String getTag(int position) {
        return tags.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int getDrawable(int position) {
        return drawables.get(position);
    }

    @Override
    public int getViewPosition(String tag) {
        int pos = 0;

        if (TextUtils.isEmpty(tag)) {
            return pos;
        }

        for (int i = 0; i < tags.size(); i++) {
            if (tag.equals(tags.get(i))) {
                return i;
            }
        }

        return pos;
    }

}