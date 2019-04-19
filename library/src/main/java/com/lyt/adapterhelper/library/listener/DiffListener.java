package com.lyt.adapterhelper.library.listener;

public interface DiffListener<T> {

   boolean areItemsTheSame(T oldBean, T newBean);

   boolean areContentsTheSame(T oldBean, T newBean);

    Object getChangePayload(T oldBean, T newBean);
}
