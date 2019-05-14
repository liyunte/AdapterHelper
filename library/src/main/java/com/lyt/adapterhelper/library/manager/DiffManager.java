package com.lyt.adapterhelper.library.manager;

import android.support.v7.util.DiffUtil;

import com.lyt.adapterhelper.library.listener.DiffCallback;
import com.lyt.adapterhelper.library.listener.DiffListener;

import java.util.List;

/**
 * 1.DiffUtil管理器
 * 2.初始化 DiffManager
 * 3.设置 setDiffListener
 * 4.添加新旧数据
 * 5.执行diff()
 * @param <T>
 */
public class DiffManager<T> {

    private DiffCallback<T> diffCallback;

    public DiffManager() {
        diffCallback = new DiffCallback<T>();
    }
    public void setDiffListener(DiffListener<T> diffListener) {
        diffCallback.setDiffListener(diffListener);
    }

    private DiffCallback getDiffCallback() {
        return diffCallback;
    }

    /**
     * 设置旧数据  一般为adapter.getData()
     * @param oldList 旧数据
     */
    public void setOldList(List<T> oldList) {
        diffCallback.setOldList(oldList);
    }

    /**
     * 设置新数据 一般为网络上的数据
     * @param newList 新数据
     */
    public void setNewList(List<T> newList) {
        diffCallback.setNewList(newList);
    }

    /**
     * 开始差分 建议在子线程中调用
     * @return 差分后的数据源
     */
    public DiffUtil.DiffResult diff() {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        return result;
    }


}
