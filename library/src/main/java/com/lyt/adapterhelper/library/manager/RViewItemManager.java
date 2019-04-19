package com.lyt.adapterhelper.library.manager;

import android.support.v4.util.SparseArrayCompat;

import com.lyt.adapterhelper.library.holder.RViewHolder;
import com.lyt.adapterhelper.library.listener.RViewItem;

import java.util.List;

/**
 * 多类型、多样式类型管理器
 * @param <T>
 */
public class RViewItemManager<T> {
    private SparseArrayCompat<RViewItem<T>> styles = new SparseArrayCompat<>();

    /**
     *  获取item所有样式的数量
     * @return
     */
    public int getItemViewStylesCount(){
        return styles.size();
    }

    /**
     * 加入一个新的item样式
     * @param item
     */
    public void addStyles(RViewItem<T> item) {
        if (item!=null){
            styles.put(styles.size(),item);
        }
    }

    /**
     * 根据数据源和位置返回某item类型的viewtyle(从styles获取key)
     * @param entity
     * @param position
     * @return
     */
    public int getItemViewType(T entity, int position) {
     for (int i = styles.size()-1;i>=0;i--){
         RViewItem<T> item = styles.valueAt(i);
         if (item.isItemView(entity,position)){
             return styles.keyAt(i);
         }
     }
     throw new IllegalArgumentException("位置:"+position+",该item没有匹配的RViewItem类型");
    }

    /**
     * 根据显示的viewType返回RViewItem对象
     * @param viewType 布局类型
     * @return RViewItem对象
     */
    public RViewItem getRViewItem(int viewType){
        return styles.valueAt(viewType);
    }

    /**
     * 视图与数据源绑定
     * @param holder
     * @param t
     * @param position
     */
    public void convert(RViewHolder holder, T t, int position, List<Object> payloads) {
        for (int i= 0;i<styles.size();i++){
            RViewItem<T> item = styles.valueAt(i);
            if (item.isItemView(t,position)){
                item.convert(holder,t,position,payloads);
                return;
            }
        }
        throw new IllegalArgumentException("位置:"+position+",该item没有匹配的RViewItem类型");
    }


}
