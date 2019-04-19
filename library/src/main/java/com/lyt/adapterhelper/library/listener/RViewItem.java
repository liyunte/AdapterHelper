package com.lyt.adapterhelper.library.listener;

import com.lyt.adapterhelper.library.holder.RViewHolder;

import java.util.List;

/**
 * 某一类item对象的接口
 */
public interface RViewItem<T> {
    /**
     * item布局
     * @return
     */
    int getItemLayout();

    /**
     * 是否开启点击
     * @return
     */
    boolean openClick();

    /**
     * 是否为当前item布局
     * @return
     */
    boolean isItemView(T entity,int position);


    /**
     * 将item与数据绑定
     */

    void convert(RViewHolder holder, T entity, int position,List<Object> payloads);





}
