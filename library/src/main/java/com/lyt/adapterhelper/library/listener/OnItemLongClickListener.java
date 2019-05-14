package com.lyt.adapterhelper.library.listener;

import android.view.View;

public interface OnItemLongClickListener<T> {
    void onItemLongClick(View view, T entity, int position);
}
