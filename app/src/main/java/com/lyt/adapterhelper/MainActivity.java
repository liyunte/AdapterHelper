package com.lyt.adapterhelper;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.lyt.adapterhelper.library.RViewHelper;
import com.lyt.adapterhelper.library.base.RViewAdapter;
import com.lyt.adapterhelper.library.listener.OnItemClickListener;
import com.lyt.adapterhelper.library.listener.OnItemLongClickListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMainActivity {
    private DemoAdapter demoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RViewHelper holper = new RViewHelper.Builder().build(this);
        demoAdapter = (DemoAdapter) holper.getmRViewAdapter();
        demoAdapter.setOpenInterceptClick();//开启拦截点击事件
        demoAdapter.setOnItemClickListener(new OnItemClickListener<DemoBean>() {
            @Override
            public void onItemClick(View view, DemoBean entity, int position) {
                Log.e("liyunte",entity.getName());
            }
        });
        demoAdapter.setOnItemLongClickListener(new OnItemLongClickListener<DemoBean>() {
            @Override
            public void onItemLongClick(View view, DemoBean entity, int position) {

            }
        });
        initData();
    }

    private void initData() {
        findViewById(R.id.tv_main_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DemoBean> demoBeanList = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    DemoBean demoBean = new DemoBean(i, "李四" + i);
                    demoBeanList.add(demoBean);
                }
                demoAdapter.addData(demoBeanList);
            }
        });
        findViewById(R.id.tv_main_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    List<DemoBean> demoBeanList = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    DemoBean demoBean = new DemoBean(i, "王五" + i);
                    demoBeanList.add(demoBean);
                }*/
                demoAdapter.setNewData(new ArrayList<DemoBean>());
            }
        });

     /*   List<DemoBean> demoBeanList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            DemoBean demoBean = new DemoBean(i, "张三" + i);
            demoBeanList.add(demoBean);
        }
        demoAdapter.setNewData(demoBeanList);*/
    }

    @Override
    public RecyclerView createRecyclerView() {
        return findViewById(R.id.rv_main_demo);
    }

    @Override
    public RViewAdapter<DemoBean> createRecyclerViewAdapter() {
        return new DemoAdapter();
    }


}
