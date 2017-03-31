package com.studio.ran.guanchazhe.adapter;

import android.content.Context;

import com.alp.library.widget.recycleview.BasicRecyclerViewAdapter;
import com.alp.library.widget.recycleview.ViewHolder;
import com.studio.ran.guanchazhe.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BasicRecyclerViewAdapter<String> {

    private String previousKeyword;
    private String currentKeyword;

    public SearchAdapter(Context context, List<String> list) {
        super(context, list);
    }

    public void setKeyword(String keyword) {
        this.previousKeyword = this.currentKeyword;
        this.currentKeyword = keyword;
    }

    public String getKeyword() {
        return currentKeyword;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_search;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position, String item) {

    }

    public void handleNewData(List<String> lyrics) {
        if (lyrics == null) {
            lyrics = new ArrayList<>();
        }

        if (previousKeyword.equals(currentKeyword)) {
            add(lyrics);
        } else {
            set(lyrics);
        }
    }

    public boolean isLoadMore() {
        return currentKeyword.equals(previousKeyword);
    }

}
