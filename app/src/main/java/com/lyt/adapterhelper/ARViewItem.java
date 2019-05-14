package com.lyt.adapterhelper;

import android.graphics.Color;
import android.util.Log;

import com.lyt.adapterhelper.library.holder.RViewHolder;
import com.lyt.adapterhelper.library.listener.RViewItem;

import java.util.List;

public class ARViewItem implements RViewItem<DemoBean> {
    private static final String LOG_TAG = "liyunte";
    @Override
    public int getItemLayout() {
        return R.layout.app_adapter_item_demo;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public int[] getClickIds() {
        return new int[]{R.id.tv_app_demo_item_content};
    }

    @Override
    public int[] getLongClickIds() {
        return new int[0];
    }

    @Override
    public boolean isItemView(DemoBean entity, int position) {
        return entity.getId()%3==0;
    }

    @Override
    public void convert(RViewHolder holder, DemoBean entity, int position, List<Object> payloads) {
    if (payloads.isEmpty()){
        holder.setText(R.id.tv_app_demo_item_content,entity.getName());
        holder.setBackgroundColor(R.id.tv_app_demo_item_content, Color.parseColor("#dcdcdc"));
    }else {
        DemoBean demoBean = (DemoBean) payloads.get(0);
        if (demoBean==null){
            return;
        }
        holder.setText(R.id.tv_app_demo_item_content,entity.getName());
        holder.setBackgroundColor(R.id.tv_app_demo_item_content, Color.parseColor("#dcdcdc"));
    }
    }
}
