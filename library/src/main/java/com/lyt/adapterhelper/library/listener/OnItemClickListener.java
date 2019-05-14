package com.lyt.adapterhelper.library.listener;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View view,T entity,int position);

}
