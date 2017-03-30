package com.alp.library.widget.recycleview;

import android.view.View;

public interface OnItemLongClickListener<T> {

    void onItemLongClick(View itemView, int pos, T item);

}
