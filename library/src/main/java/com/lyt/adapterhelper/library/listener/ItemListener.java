package com.lyt.adapterhelper.library.listener;

import android.view.View;

public interface ItemListener<T> {
    void onItemClick(View view,T entity,int position);
    boolean onItemLongClick(View view,T entity,int position);
}
