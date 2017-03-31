package com.studio.ran.guanchazhe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.ran.guanchazhe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class ProgressButton extends FrameLayout {

    @BindView(R.id.text)
    TextView title;
    @BindView(R.id.progressbar)
    ProgressBar progress;

    private boolean inProgress = false;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_progress_button, this);
        ButterKnife.bind(this);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        String titleText = array.getString(R.styleable.ProgressButton_button_title);
//        int background = array.getResourceId(R.styleable.ProgressButton_button_background, R.drawable.bg_rounded_corners_gray);

        array.recycle();

        setTitle(titleText);
//        setBackground(background);
        progress.getIndeterminateDrawable().setColorFilter(Color.WHITE,
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setBackground(int res) {
        setBackgroundResource(res);
    }

    public void start() {
        title.setVisibility(INVISIBLE);
        progress.setVisibility(VISIBLE);
        inProgress = true;
    }

    public void end() {
        title.setVisibility(VISIBLE);
        progress.setVisibility(INVISIBLE);
        inProgress = false;
    }

    public boolean isInProgress() {
        return inProgress;
    }

}
