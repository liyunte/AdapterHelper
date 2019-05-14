package com.lyt.adapterhelper.library.base;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.lyt.adapterhelper.library.holder.RViewHolder;
import com.lyt.adapterhelper.library.listener.OnItemClickListener;
import com.lyt.adapterhelper.library.listener.OnItemLongClickListener;
import com.lyt.adapterhelper.library.listener.RViewItem;
import com.lyt.adapterhelper.library.manager.HeaderFooterManager;
import com.lyt.adapterhelper.library.manager.NotifyObserver;
import com.lyt.adapterhelper.library.manager.RViewItemManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 多类型、带头部底部、空布局、新旧数据比较局部刷新的RecyclerViewAdapter
 *
 * @param <T> bean对象类型
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {
    private List<T> mData;//数据源
    private RViewItemManager<T> mItemStyle;//item布局类型管理器
    private OnItemClickListener<T> mItemListener;//item点击监听
    private OnItemLongClickListener<T> itemLongClickListener;//item长按事件监听
    private long lastClickTime;//上次点击的时间
    private static final long QUICK_EVENT_TIME_SPAN = 700;//item点击事件误操作设置的时间间隔
    private HeaderFooterManager headerFooterManager;
    private static final int HEADER_VIEW = 520;
    private static final int FOOTER_VIEW = 1024;
    private static final int EMPTY_VIEW = 2019;
    private boolean openInterceptClick;//开启拦截点击事件

    /**
     * 单类型
     *
     * @param data 数据源
     */
    public RViewAdapter(List<T> data) {
        this(data, null);
    }

    /**
     * 多类型
     *
     * @param data 数据源
     * @param item 布局类型接口
     */
    public RViewAdapter(List<T> data, RViewItem<T> item) {
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            this.mData = data;
        }
        mItemStyle = new RViewItemManager<>();
        headerFooterManager = new HeaderFooterManager();
        if (item != null) {
            addItemStyles(item);
        }
    }


    /**
     * 根据position获取当前item的布局类型
     *
     * @param position 位置
     * @return 布局类型
     */
    @Override
    public int getItemViewType(int position) {
        if (getHeaderCount() > 0 && position == 0) {
            return HEADER_VIEW;
        } else if (getFooterCount() > 0 && position == getItemCount() - 1) {
            return FOOTER_VIEW;
        } else if (mData.size() == 0 && getEmptyCount() > 0) {
            return EMPTY_VIEW;
        } else {
            //如果有多样式
            if (hasMultiStyle()) {
                return mItemStyle.getItemViewType(mData.get(position - getHeaderCount()), position);
            }
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            return RViewHolder.createViewHolder(headerFooterManager.getHeaderLayout());
        }
        if (viewType == FOOTER_VIEW) {
            return RViewHolder.createViewHolder(headerFooterManager.getFooterLayout());
        }
        if (viewType == EMPTY_VIEW) {
            return RViewHolder.createViewHolder(headerFooterManager.getEmptyLayout());
        }
        RViewItem rViewItem = mItemStyle.getRViewItem(viewType);
        int layoutId = rViewItem.getItemLayout();
        int[] clickIds = rViewItem.getClickIds();
        int[] longClickIds = rViewItem.getLongClickIds();

        RViewHolder viewHolder = RViewHolder.createViewHolder(parent.getContext(), parent, layoutId);
        if (rViewItem.openClick()) {
            setListener(viewHolder,clickIds,longClickIds);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            default:
                if (hasMultiStyle()) {
                    convert(holder, mData.get(holder.getAdapterPosition() - getHeaderCount()), holder.getAdapterPosition() - getHeaderCount(), new ArrayList<>());
                }

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            switch (holder.getItemViewType()) {
                case HEADER_VIEW:
                    break;
                case FOOTER_VIEW:
                    break;
                case EMPTY_VIEW:
                    break;
                default:
                    if (hasMultiStyle()) {
                        convert(holder, mData.get(holder.getAdapterPosition() - getHeaderCount()), holder.getAdapterPosition() - getHeaderCount(), payloads);
                    }

            }


        }
    }

    /**
     * item数量
     *
     * @return item数量
     */
    @Override
    public int getItemCount() {
        int headCount = getHeaderCount();
        int footerCount = getFooterCount();
        int emptyCount = 0;
        int dataSize;
        if (mData == null || mData.size() == 0) {
            dataSize = 0;
            emptyCount = getEmptyCount();
        } else {
            dataSize = mData.size();
        }
        return headCount + footerCount + emptyCount + dataSize;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == HEADER_VIEW || type == FOOTER_VIEW || type == EMPTY_VIEW) {
            this.setFullSpan(holder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return type != HEADER_VIEW && type != FOOTER_VIEW && type != EMPTY_VIEW ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }

    }

    /**
     * @return 是否是多样式
     */
    private boolean hasMultiStyle() {
        return mItemStyle.getItemViewStylesCount() > 0;
    }

    /**
     * 事件绑定
     *
     * @param holder   RViewHolder
     * @param t        bean
     * @param position 位置
     * @param payloads 数据
     */
    private void convert(RViewHolder holder, T t, int position, List<Object> payloads) {
        mItemStyle.convert(holder, t, position, payloads);
    }



    private void setListener(final RViewHolder viewHolder,int[] clickIds,int[] longClickIds) {
        if (clickIds!=null&&clickIds.length>0){
            for (int id :clickIds){
                viewHolder.getView(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemListener!=null){
                            if (openInterceptClick){
                                long timespan = System.currentTimeMillis() - lastClickTime;
                                if (timespan < QUICK_EVENT_TIME_SPAN) {
                                    //点击阻塞防止误操作
                                    return;
                                }
                                lastClickTime = System.currentTimeMillis();
                            }
                            int position = viewHolder.getAdapterPosition() - getHeaderCount();
                            mItemListener.onItemClick(v, mData.get(position), position);
                        }

                    }
                });
            }
        }
        if (longClickIds!=null&&longClickIds.length>0){
            for (int id :longClickIds){
               viewHolder.getView(id).setOnLongClickListener(new View.OnLongClickListener() {
                   @Override
                   public boolean onLongClick(View v) {
                       if (itemLongClickListener!=null){
                           int position = viewHolder.getAdapterPosition() - getHeaderCount();
                           itemLongClickListener.onItemLongClick(v,mData.get(position),position);
                       }
                       return false;
                   }
               });
            }
        }

    }


    /**
     * 添加样式
     *
     * @param item 样式
     */
    public void addItemStyles(RViewItem<T> item) {
        mItemStyle.addStyles(item);
    }


    /**
     * 设置事件监听
     *
     * @param itemListener item事件
     */
    public void setOnItemClickListener(OnItemClickListener<T> itemListener) {
        this.mItemListener = itemListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> itemLongClickListener){
        this.itemLongClickListener = itemLongClickListener;
    }

    /**
     * 添加空布局
     *
     * @param empty 空布局
     */
    public void setEmptyView(View empty) {
        setEmptyView(empty, -1);
    }

    /**
     * 添加空布局
     *
     * @param empty 空布局
     * @param index 指定位置
     */
    public void setEmptyView(View empty, int index) {
        headerFooterManager.setEmptyView(empty, index);
    }

    /**
     * 添加头部
     *
     * @param header 头部view
     */
    public void addHeaderView(@NonNull View header) {
        addHeaderView(header, -1);
    }

    /**
     * 添加指定位置的头部
     *
     * @param header 头部view
     * @param index  位置
     */
    public void addHeaderView(@NonNull View header, int index) {
        headerFooterManager.addHeaderView(header, index);
        notifyDataSetChanged();
    }

    /**
     * 移除指定的头部
     *
     * @param header 头部view
     */
    public void removeHeaderView(@NonNull View header) {
        headerFooterManager.removeHeaderView(header);
        notifyDataSetChanged();
    }

    /**
     * 移除所有的头部
     */
    public void removeAllHeaderView() {
        headerFooterManager.removeAllHeaderView();
        notifyDataSetChanged();
    }

    /**
     * 添加底部
     *
     * @param footer 底部view
     */
    public void addFooterView(@NonNull View footer) {
        addFooterView(footer, -1);
    }

    /**
     * 添加底部
     *
     * @param footer 底部view
     * @param index  指定位置
     */
    public void addFooterView(@NonNull View footer, int index) {
        headerFooterManager.addFooterView(footer, index);
        notifyDataSetChanged();
    }

    /**
     * 移除指定的底部view
     *
     * @param footer 底部view
     */
    public void removeFooterView(@NonNull View footer) {
        headerFooterManager.removeFooterView(footer);
        notifyDataSetChanged();
    }

    public void removeAllEmptyView() {
        headerFooterManager.removeAllEmptyView();
        notifyDataSetChanged();
    }

    public void removeEmptyView(@NonNull View footer) {
        headerFooterManager.removeFooterView(footer);
        notifyDataSetChanged();
    }

    /**
     * 移除所有的底部view
     */
    public void removeAllFooterView() {
        headerFooterManager.removeAllFooterView();
        notifyDataSetChanged();
    }

    /**
     * 获取头部数量
     *
     * @return
     */
    public int getHeaderCount() {
        return headerFooterManager.getHeaderCount();
    }

    /**
     * 获取底部数量
     *
     * @return 底部数量
     */
    public int getFooterCount() {
        return headerFooterManager.getFooterCount();
    }

    /**
     * 获取空布局数量
     *
     * @return 空布局数量
     */
    public int getEmptyCount() {
        return headerFooterManager.getEmptyViewCount();
    }


    /**
     * DiffUtil 新旧数据比较 推荐用DiffManager来处理Diff
     *
     * @param newData 新的全数据
     * @param result  DiffResult
     */
    public void setNewData(List<T> newData, DiffUtil.DiffResult result) {
        if (newData == null) return;
        if (getEmptyCount() > 0 && mData.size() == 0) {
            notifyDataSetChanged();
        }
        this.mData = new ArrayList<>(newData);
        result.dispatchUpdatesTo(this);
    }

    /**
     * 设置新的数据 刷新
     *
     * @param data 数据集合
     */
    public void setNewData(@NonNull List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mData = data;
        notifyDataSetChanged();
    }


    /**
     * 添加新数据
     *
     * @param data 数据集合
     */
    public void addData(List<T> data) {
        if (data == null) {
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 开启拦截点击事件
     */
    public void setOpenInterceptClick(){
        openInterceptClick = true;
    }

    /**
     * 获取数据
     * @return
     */
    public List<T> getData() {
        return mData;
    }


    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        NotifyObserver observer1 = new NotifyObserver(observer, getHeaderCount());
        super.registerAdapterDataObserver(observer1);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        NotifyObserver observer1 = new NotifyObserver(observer, getHeaderCount());
        super.unregisterAdapterDataObserver(observer1);
    }


}
