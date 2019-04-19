package com.lyt.adapterhelper.library.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyt.adapterhelper.library.base.RViewAdapter;

public interface RViewCreate<T> {

    //刷新控件的创建

    /**
     * 创建RecyclerView
     * @return
     */
    RecyclerView createRecyclerView();

    /**
     * 创建RViewAdapter
     * @return
     */
    RViewAdapter<T> createRecyclerViewAdapter();

    /**
     * 创建LayoutManager
     * @return
     */
    RecyclerView.LayoutManager createLayoutManager();

    /**
     * 创建ItemDecoration
     * @return
     */
    RecyclerView.ItemDecoration createItemDecoration();

    /**
     * 头部
     * @return
     */
    View addHeaderView();


    /**
     * 底部
     * @return
     */
    View addFooterView();


    /**
     * 空布局
     * @return
     */
    View addEmptyView();

}
