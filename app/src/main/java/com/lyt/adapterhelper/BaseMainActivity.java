package com.lyt.adapterhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lyt.adapterhelper.library.base.RViewAdapter;
import com.lyt.adapterhelper.library.listener.RViewCreate;

public abstract class BaseMainActivity extends AppCompatActivity implements RViewCreate<DemoBean>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public RecyclerView.ItemDecoration createItemDecoration() {
        return null;
    }

    @Override
    public View addHeaderView() {
        return  LayoutInflater.from(this).inflate(R.layout.app_demo_header,new LinearLayout(this),false);
    }

    @Override
    public View addFooterView() {
        return LayoutInflater.from(this).inflate(R.layout.app_demo_footer,new LinearLayout(this),false);
    }

    @Override
    public View addEmptyView() {
        return LayoutInflater.from(this).inflate(R.layout.app_demo_empty,new LinearLayout(this),false);
    }
}
