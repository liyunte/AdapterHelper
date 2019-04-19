package com.lyt.adapterhelper.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyt.adapterhelper.library.base.RViewAdapter;
import com.lyt.adapterhelper.library.listener.RViewCreate;

/**
 * RecyclerView帮助类
 * @param <T>
 */
public class RViewHelper<T> {
    RecyclerView mRecyclerView;
    RViewAdapter<T> mRViewAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.ItemDecoration mItemDecoration;
    View mHeaderView;
    View mFooterView;
    View mEmptyView;

    private RViewHelper(Builder builder) {
        mRecyclerView = builder.mRecyclerView;
        mRViewAdapter = builder.mRViewAdapter;
        mLayoutManager = builder.mLayoutManager;
        mItemDecoration = builder.mItemDecoration;
        mHeaderView = builder.mHeaderView;
        mFooterView = builder.mFooterView;
        mEmptyView = builder.mEmptyView;
        init();
    }

    private void init() {
        if (mRecyclerView == null) {
            throw new NullPointerException("recyclerview is not null");
        }
        if (mRViewAdapter == null) {
            throw new NullPointerException("adpater is not null");
        }
        if (mLayoutManager == null) {
            throw new NullPointerException("layoutManager is not null");
        }
        if (mHeaderView != null) {
            mRViewAdapter.addHeaderView(mHeaderView);
        }
        if (mFooterView != null) {
            mRViewAdapter.addFooterView(mFooterView);
        }
        if (mEmptyView!=null){
            mRViewAdapter.setEmptyView(mEmptyView);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        if (mItemDecoration != null) {
            mRecyclerView.addItemDecoration(mItemDecoration);
        }
        mRecyclerView.setAdapter(mRViewAdapter);

    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public RViewAdapter<T> getmRViewAdapter() {
        return mRViewAdapter;
    }

    public RecyclerView.LayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    public RecyclerView.ItemDecoration getmItemDecoration() {
        return mItemDecoration;
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public View getmFooterView() {
        return mFooterView;
    }

    public View getmEmptyView() {
        return mEmptyView;
    }

    public static class Builder<T> {
        RecyclerView mRecyclerView;
        RViewAdapter<T> mRViewAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.ItemDecoration mItemDecoration;
        View mHeaderView;
        View mFooterView;
        View mEmptyView;

        public RViewHelper build(RViewCreate rViewCreate) {
            mRecyclerView = rViewCreate.createRecyclerView();
            mRViewAdapter = rViewCreate.createRecyclerViewAdapter();
            mLayoutManager = rViewCreate.createLayoutManager();
            mItemDecoration = rViewCreate.createItemDecoration();
            mHeaderView = rViewCreate.addHeaderView();
            mFooterView = rViewCreate.addFooterView();
            mEmptyView = rViewCreate.addEmptyView();
            return new RViewHelper(this);
        }
    }
}
