package com.materialdesign.heiyl.recyleview.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.materialdesign.heiyl.recyleview.R;
import com.materialdesign.heiyl.recyleview.diver.DividerItemDecoration;
import com.materialdesign.heiyl.recyleview.touch.ItemTouchHelperCallBack;
import com.materialdesign.heiyl.recyleview.touch.StartDragListener;
import com.materialdesign.heiyl.recyleview.ui.adpaters.RVTouchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * recyleview交互动画
 */
public class RVTouchActivity extends AppCompatActivity implements StartDragListener {

    private RecyclerView recyleview;
    private RVTouchAdapter adapter;
    private List<String> data = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private DividerItemDecoration dividerItemDecoration;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rve_touch);
        initData();
        initViews();
    }

    private void initViews(){
        toolbar = this.findViewById(R.id.toolbar);
        toolbar.setTitle("RecyleView交互动画");
        setSupportActionBar(toolbar);

        dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.line_diver_gray);

        //线性布局管理：reverseLayout:数据倒置，水平布局从右边开始滑动 垂直布局
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyleview = this.findViewById(R.id.recyleview);
        //设置布局管理器
        recyleview.setLayoutManager(linearLayoutManager);
        //添加分割线
        recyleview.addItemDecoration(dividerItemDecoration);
        // //设置Item增加、移除动画
        recyleview.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        adapter = new RVTouchAdapter(data,this);
        recyleview.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallBack(adapter);
        //条目触摸辅助类
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyleview);

    }

    @Override
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    private void initData(){
        for (int i = 0; i < 30; i++){
            data.add("item"+i);
        }
    }
}
