package com.lyt.adapterhelper.library.manager;

import android.support.v7.widget.RecyclerView;

public class NotifyObserver extends RecyclerView.AdapterDataObserver {
    private int headerCount;
    RecyclerView.AdapterDataObserver mDataObserver;
    public NotifyObserver(RecyclerView.AdapterDataObserver dataObserver,int headCount) {
        mDataObserver = dataObserver;
        this.headerCount = headCount;
    }
    @Override
    public void onChanged() {
        mDataObserver.onChanged();
    }
    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mDataObserver.onItemRangeChanged(positionStart + headerCount , itemCount);
    }
    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        mDataObserver.onItemRangeChanged(positionStart + headerCount , itemCount, payload);
    }
    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mDataObserver.onItemRangeInserted(positionStart + headerCount , itemCount);
    }
    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        mDataObserver.onItemRangeMoved(fromPosition, toPosition, itemCount);
    }
    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mDataObserver.onItemRangeRemoved(positionStart + headerCount , itemCount);
    }
}
