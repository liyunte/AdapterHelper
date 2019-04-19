package com.lyt.adapterhelper.library.listener;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

public class DiffCallback<T> extends DiffUtil.Callback {
    private List<T> oldList = new ArrayList<>();
    private List<T> newList = new ArrayList<>();
    private DiffListener<T> diffListener;

    public void setOldList(List<T> oldList) {
        if (oldList != null) {
            this.oldList.clear();
            this.oldList.addAll(oldList);
        }
    }

    public void setNewList(List<T> newList) {
        if (newList != null) {
            this.newList.clear();
            this.newList.addAll(newList);
        }
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }


    public void setDiffListener(DiffListener<T> diffListener) {
        this.diffListener = diffListener;
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        if (diffListener != null) {
            return diffListener.areContentsTheSame(oldList.get(i), newList.get(i1));
        }
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int i, int i1) {
        if (diffListener != null) {
            return diffListener.getChangePayload(oldList.get(i), newList.get(i1));
        }
        return null;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        if (diffListener != null) {
            return diffListener.areItemsTheSame(oldList.get(i), newList.get(i1));
        }
        return false;
    }
}
