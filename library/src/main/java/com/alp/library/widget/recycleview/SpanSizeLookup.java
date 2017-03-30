package com.alp.library.widget.recycleview;

import android.support.v7.widget.GridLayoutManager;

public class SpanSizeLookup<T extends MultipleTypeItem> extends GridLayoutManager.SpanSizeLookup {

    private final MultipleTypeRecyclerViewAdapter<T> adapter;
    private final GridLayoutManager layoutManager;
    private int specialType;

    public SpanSizeLookup(MultipleTypeRecyclerViewAdapter<T> adapter, GridLayoutManager layoutManager, int specialType) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        this.specialType = specialType;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isSpecial = adapter.getItem(position).getType() == specialType;
        return isSpecial ? layoutManager.getSpanCount() : 1;
    }
}