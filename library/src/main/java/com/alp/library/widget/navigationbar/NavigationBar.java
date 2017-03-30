package com.alp.library.widget.navigationbar;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alp.library.R;
import com.alp.library.base.helper.EnhancedFragmentManager;
import com.alp.library.utils.ViewUtil;

public class NavigationBar extends FrameLayout implements View.OnClickListener {

    private static final float maxScale = 1.15f;

    private Context mContext;
    private int activeColor;
    private int inActiveColor;

    private float moveDistance;
    private int currentTabPosition;

    private EnhancedFragmentManager manager;
    private NavigationBarFragmentAdapter adapter;

    private LinearLayout itemContainer;

    private NavigationItemClickListener listener;

    public NavigationBar(Context context) {
        super(context);
        init(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        moveDistance = ViewUtil.dpToPx(mContext, 2);
        currentTabPosition = 0;

        activeColor = ContextCompat.getColor(context, R.color.colorPrimary);
        inActiveColor = ContextCompat.getColor(context, R.color.grey);

        View rootView = View.inflate(mContext, R.layout.view_navigation_bar, this);
        itemContainer = (LinearLayout) rootView.findViewById(R.id.navigation_bar_item_container);
    }

    public void setFragmentManager(EnhancedFragmentManager fragmentManager) {
        this.manager = fragmentManager;
        this.adapter = (NavigationBarFragmentAdapter) manager.getAdapter();
        addItems();
    }

    private void addItems() {
        int index = 0;
        final int itemSize = adapter.getCount();

        View[] viewsToAdd = new View[itemSize];

        for (int i = 0; i < itemSize; i++) {
            ViewGroup bottomItem = (ViewGroup) View.inflate(mContext, R.layout.item_bottom_bar, null);

            ImageView icon = (ImageView) bottomItem.findViewById(R.id.navigation_bar_icon);
            icon.setImageResource(adapter.getDrawable(i));

            TextView title = (TextView) bottomItem.findViewById(R.id.navigation_bar_title);
            title.setText(adapter.getTag(i));

            bottomItem.setOnClickListener(this);
            bottomItem.setTag(adapter.getTag(i));
            viewsToAdd[index] = bottomItem;
            index++;
        }

        int screenWidth = ViewUtil.getScreenWidth(mContext);
        int proposedItemWidth = screenWidth / itemSize;

        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(proposedItemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (View bottomBarView : viewsToAdd) {
            bottomBarView.setLayoutParams(params);
            itemContainer.addView(bottomBarView);
        }

        tabAnimation(itemContainer.getChildAt(currentTabPosition), true);
        manager.showFragment(currentTabPosition);
    }

    private void tabAnimation(View bottomItem, boolean selected) {
        int color;
        float scale;
        float translationY;

        if (selected) {
            color = activeColor;
            scale = maxScale;
            translationY = -moveDistance;
        } else {
            color = inActiveColor;
            scale = 1;
            translationY = 0;
        }

        ImageView icon = (ImageView) bottomItem.findViewById(R.id.navigation_bar_icon);
        TextView title = (TextView) bottomItem.findViewById(R.id.navigation_bar_title);

        icon.setColorFilter(color);
        title.setTextColor(color);

        title.animate()
                .scaleX(scale)
                .scaleY(scale)
                .start();

        bottomItem.animate()
                .translationY(translationY)
                .start();
    }

    @Override
    public void onClick(View v) {
        int pos = adapter.getViewPosition((String) v.getTag());

        if (pos == currentTabPosition) {
            return;
        }

        manager.showFragment(pos);

        tabAnimation(v, true);
        tabAnimation(itemContainer.getChildAt(currentTabPosition), false);

        currentTabPosition = pos;

        if (listener != null) {
            listener.onBottomItemClick(pos);
        }
    }

    public void setListener(NavigationItemClickListener listener) {
        this.listener = listener;
    }

}

