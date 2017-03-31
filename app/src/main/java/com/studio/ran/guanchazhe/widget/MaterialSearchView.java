package com.studio.ran.guanchazhe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alp.library.utils.GUIUtil;
import com.alp.library.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.studio.ran.guanchazhe.R;
import com.studio.ran.guanchazhe.adapter.SearchAdapter;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alp.library.utils.GUIUtil.ANIMATION_DURATION_MEDIUM;

public final class MaterialSearchView extends FrameLayout {

    @BindView(R.id.search_layout)
    View mSearchLayout;
    @BindView(R.id.transparent_view)
    View mTintView;
    @BindView(R.id.suggestion_list)
    RecyclerView mListView;
    @BindView(R.id.searchTextView)
    EditText mSearchSrcTextView;
    @BindView(R.id.action_up_btn)
    ImageButton mBackBtn;
    @BindView(R.id.action_empty_btn)
    ImageButton mEmptyBtn;
    @BindView(R.id.search_top_bar)
    RelativeLayout mSearchTopBar;

    private boolean mIsSearchOpen = false;
    private int mAnimationDuration;
    private boolean mClearingFocus;

    private CharSequence mUserQuery;

    private OnQueryListener mOnQueryChangeListener;
    private SearchViewListener mSearchViewListener;

    private SearchAdapter mAdapter;

    private SavedState mSavedState;

    private Context mContext;

    public MaterialSearchView(Context context) {
        this(context, null);
    }

    public MaterialSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        mContext = context;

        initiateView();
        initStyle(attrs, defStyleAttr);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MaterialSearchView, defStyleAttr, 0);

        if (a != null) {
            if (a.hasValue(R.styleable.MaterialSearchView_searchBackground)) {
                setBackground(a.getDrawable(R.styleable.MaterialSearchView_searchBackground));
            }

            if (a.hasValue(R.styleable.MaterialSearchView_android_textColor)) {
                setTextColor(a.getColor(R.styleable.MaterialSearchView_android_textColor, 0));
            }

            if (a.hasValue(R.styleable.MaterialSearchView_android_hint)) {
                setHint(a.getString(R.styleable.MaterialSearchView_android_hint));
            }

            if (a.hasValue(R.styleable.MaterialSearchView_searchCloseIcon)) {
                setCloseIcon(a.getDrawable(R.styleable.MaterialSearchView_searchCloseIcon));
            }

            if (a.hasValue(R.styleable.MaterialSearchView_searchBackIcon)) {
                setBackIcon(a.getDrawable(R.styleable.MaterialSearchView_searchBackIcon));
            }

            if (a.hasValue(R.styleable.MaterialSearchView_searchSuggestionBackground)) {
                setListBackground(a.getDrawable(R.styleable.MaterialSearchView_searchSuggestionBackground));
            }

            a.recycle();
        }
    }

    private void initiateView() {
        ButterKnife.bind(this, LayoutInflater.from(mContext).inflate(R.layout.view_search, this, true));

        initSearchView();
        initRecycleView();

        mListView.setVisibility(GONE);
        setAnimationDuration(ANIMATION_DURATION_MEDIUM);
    }

    private void initSearchView() {
        mSearchSrcTextView.setOnEditorActionListener((v, actionId, event) -> {
            startQuery(v.getText());
            hideKeyboard(MaterialSearchView.this);
            return true;
        });

        mSearchSrcTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MaterialSearchView.this.onTextChanged(s);
                startQuery(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSearchSrcTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showKeyboard(mSearchSrcTextView);
            }
        });
    }

    private void startQuery(CharSequence query) {

        if (query == null || query.toString().isEmpty()) {
            return;
        }
        mAdapter.setKeyword(query.toString());
        mOnQueryChangeListener.onQuery(query.toString(), mAdapter.isLoadMore());
    }

    @OnClick({R.id.transparent_view, R.id.action_up_btn, R.id.action_empty_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.transparent_view:
                closeSearch();
                break;
            case R.id.action_up_btn:
                closeSearch();
                break;
            case R.id.action_empty_btn:
                mSearchSrcTextView.setText(null);
                break;
        }
    }

    private void onTextChanged(CharSequence newText) {
        mUserQuery = newText;
        boolean hasText = !TextUtils.isEmpty(newText);
        if (hasText) {
            mEmptyBtn.setVisibility(VISIBLE);
        } else {
            mEmptyBtn.setVisibility(GONE);
            mListView.setVisibility(GONE);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    @Override
    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mSearchTopBar.setBackground(background);
        } else {
            mSearchTopBar.setBackground(background);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        mSearchTopBar.setBackgroundColor(color);
    }

    public void setTextColor(int color) {
        mSearchSrcTextView.setTextColor(color);
    }

    public void setHint(CharSequence hint) {
        mSearchSrcTextView.setHint(hint);
    }

    public void setCloseIcon(Drawable drawable) {
        mEmptyBtn.setImageDrawable(drawable);
    }

    public void setBackIcon(Drawable drawable) {
        mBackBtn.setImageDrawable(drawable);
    }

    public void setListBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mListView.setBackground(background);
        } else {
            mListView.setBackground(background);
        }
    }

    public void setCursorDrawable(int drawable) {
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mSearchSrcTextView, drawable);
        } catch (Exception ignored) {
            Log.e("MaterialSearchView", ignored.toString());
        }
    }

    public void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);

        mListView.setLayoutManager(linearLayoutManager);
        mAdapter = new SearchAdapter(getContext(), null);
        mAdapter.setClickListener((view, pos, item) -> {
            if (mOnQueryChangeListener != null) {
                mOnQueryChangeListener.onClickQuery(item);
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mAdapter.setKeyword(mAdapter.getKeyword());
                startQuery(mAdapter.getKeyword());
            }
        });
    }

    public void updateListData(List<String> list) {
        mListView.setVisibility(VISIBLE);
        mAdapter.handleNewData(list);
    }

    public void dismissList() {
        if (mAdapter != null) {
            mAdapter.removeAll();
        }

        if (mListView.getVisibility() == VISIBLE) {
            mListView.setVisibility(GONE);
        }
    }

    public void resetQuery(CharSequence query) {
        mSearchSrcTextView.setText(query);
        if (query != null) {
            mSearchSrcTextView.setSelection(mSearchSrcTextView.length());
            mUserQuery = query;
        }
    }

    public void setMenuItem(MenuItem menuItem) {
        menuItem.setOnMenuItemClickListener(item -> {
                    showSearch();
                    return true;
                }
        );
    }

    public boolean isSearchOpen() {
        return mIsSearchOpen;
    }

    public void setAnimationDuration(int duration) {
        mAnimationDuration = duration;
    }

    public void showSearch() {
        showSearch(true);
    }

    public void showSearch(boolean animate) {
        if (isSearchOpen()) {
            return;
        }

        mSearchSrcTextView.requestFocus();

        if (animate) {
            setVisibleWithAnimation();
        } else {
            mSearchLayout.setVisibility(VISIBLE);
            if (mSearchViewListener != null) {
                mSearchViewListener.onSearchViewShown();
            }
        }
        mIsSearchOpen = true;
    }

    private void setVisibleWithAnimation() {
        GUIUtil.AnimationListener animationListener = new GUIUtil.AnimationListener() {
            @Override
            public boolean onAnimationStart(View view) {
                return false;
            }

            @Override
            public boolean onAnimationEnd(View view) {
                if (mSearchViewListener != null) {
                    mSearchViewListener.onSearchViewShown();
                }
                return false;
            }

            @Override
            public boolean onAnimationCancel(View view) {
                return false;
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSearchLayout.setVisibility(View.VISIBLE);
            GUIUtil.reveal(mSearchTopBar, animationListener);
        } else {
            GUIUtil.fadeInView(mSearchLayout, mAnimationDuration, animationListener);
        }
    }

    public void closeSearch() {
        if (!isSearchOpen()) {
            return;
        }

        dismissList();
        clearFocus();

        mSearchSrcTextView.setText(null);
        mSearchLayout.setVisibility(GONE);
        if (mSearchViewListener != null) {
            mSearchViewListener.onSearchViewClosed();
        }
        mIsSearchOpen = false;
    }

    public void setOnQueryTextListener(OnQueryListener listener) {
        mOnQueryChangeListener = listener;
    }

    public void setOnSearchViewListener(SearchViewListener listener) {
        mSearchViewListener = listener;
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return !mClearingFocus && isFocusable() && mSearchSrcTextView.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void clearFocus() {
        mClearingFocus = true;
        hideKeyboard(this);
        super.clearFocus();

        mSearchSrcTextView.clearFocus();
        mClearingFocus = false;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        mSavedState = new SavedState(superState);
        mSavedState.query = mUserQuery != null ? mUserQuery.toString() : null;
        mSavedState.isSearchOpen = this.mIsSearchOpen;

        return mSavedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        mSavedState = (SavedState) state;

        if (mSavedState.isSearchOpen) {
            showSearch(false);
            resetQuery(mSavedState.query);
        }

        super.onRestoreInstanceState(mSavedState.getSuperState());
    }

    public interface OnQueryListener {

        void onQuery(String str, boolean isLoadMore);

        void onClickQuery(Object item);
    }

    public interface SearchViewListener {
        void onSearchViewShown();

        void onSearchViewClosed();
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        String query;
        boolean isSearchOpen;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.query = in.readString();
            this.isSearchOpen = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(query);
            out.writeInt(isSearchOpen ? 1 : 0);
        }
    }

}
