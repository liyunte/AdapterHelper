package com.lyt.adapterhelper.library.listener;

public interface DiffListener<T> {

    /**
     * 判断是否是同一个 item 一般根据 id是否相同来判断
     * @param oldBean 旧数据
     * @param newBean 新数据
     * @return
     */
   boolean areItemsTheSame(T oldBean, T newBean);

    /**
     * 判断内容是否相同
     * @param oldBean 旧数据
     * @param newBean 新数据
     * @return
     */
   boolean areContentsTheSame(T oldBean, T newBean);

    /**
     * 注意应返回新的对象
     * @param oldBean 旧数据
     * @param newBean 新数据
     * @return
     */
    Object getChangePayload(T oldBean, T newBean);
}
