package com.lyt.adapterhelper;

import com.lyt.adapterhelper.library.base.RViewAdapter;



public class DemoAdapter extends RViewAdapter<DemoBean> {
    public DemoAdapter() {
        super(null);
        addItemStyles(new BRViewItem());
        addItemStyles(new ARViewItem());
        addItemStyles(new CRViewItem());
    }
}
