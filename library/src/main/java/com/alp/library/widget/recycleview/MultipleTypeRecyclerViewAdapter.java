package com.alp.library.widget.recycleview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

public abstract class MultipleTypeRecyclerViewAdapter<T extends MultipleTypeItem> extends BasicRecyclerViewAdapter<T> {

    public MultipleTypeRecyclerViewAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, mInflater.inflate(getLayoutByType(viewType), parent, false));
    }

    public abstract int getLayoutByType(int viewTyp);

    @Override
    public final void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolderByType(holder, position, items.get(position));
    }

    protected abstract void onBindViewHolderByType(ViewHolder holder, int position, T item);

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemLayout() {
        return -1;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position, T item) {

    }
}