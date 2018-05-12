package com.materialdesign.heiyl.recyleview.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.materialdesign.heiyl.recyleview.commons.Constnats;
import com.materialdesign.heiyl.recyleview.R;
import com.materialdesign.heiyl.recyleview.ui.adpaters.RVElementaryAdapter;
import com.materialdesign.heiyl.recyleview.diver.DividerGridViewItemDecoration;
import com.materialdesign.heiyl.recyleview.diver.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * recyleview基本用法
 */
public class RVElementaryActivity extends AppCompatActivity implements RVElementaryAdapter.ItemClickListener {

    private RecyclerView recyleview;
    private RVElementaryAdapter adapter;
    private List<String> data = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager horizontalGridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;

    private int currentViewType = Constnats.VIEW_TYPE_LISTVIEW;
    private DividerItemDecoration dividerItemDecoration;
    private DividerGridViewItemDecoration dividerGridViewItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvelementary);
        initData();
        initViews();
    }

    private void initViews(){
        toolbar = this.findViewById(R.id.toolbar);
        toolbar.setTitle("RecyleView基本用方法");

        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_plus:
                        addData(2);
                        break;
                    case R.id.action_del:
                        delData(2);
                        break;
                    case R.id.action_listview:
                        currentViewType = Constnats.VIEW_TYPE_LISTVIEW;
                        changeView();
                        break;
                    case R.id.action_gridview:
                        currentViewType = Constnats.VIEW_TYPE_GRIDVIEW;
                        changeView();
                        break;
                    case R.id.action_horizontalgridVew:
                        currentViewType = Constnats.VIEW_TYPE_HORIZONTALGRIDVEW;
                        changeView();
                        break;
                    case R.id.action_staggeredgridView:
                        currentViewType = Constnats.VIEW_TYPE_STAGGEREDGRIDVIEW;
                        changeView();
                        break;
                }
                return true;
            }
        });

        dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.line_diver_gray);
        dividerGridViewItemDecoration = new DividerGridViewItemDecoration(this,R.drawable.grid_diver_gray);

        //LayoutManager布局摆放管理器(线性摆放、瀑布流)
        //九宫格布局管理
        gridLayoutManager = new GridLayoutManager(this,3);
        //九宫格横向滑动
        horizontalGridLayoutManager = new GridLayoutManager(this,3,RecyclerView.HORIZONTAL,false);
        //瀑布流效果布局管理
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);
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
        adapter = new RVElementaryAdapter(data);
        recyleview.setAdapter(adapter);
        //设置条目的点击事件
        adapter.setOnItemClickListener(this);
        //设置条目的长按事件
        adapter.setOnItemLongClickListener(this);
//        recyleview.setAdapter(rvElementaryStaggerdGridAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"当前点击的条目为" + data.get(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(this,"长按删除条目" + position,Toast.LENGTH_SHORT).show();
        delData(position);
    }

    /**
     *切换布局效果
     */
    private void changeView(){
        adapter.setViewType(currentViewType);
        recyleview.removeItemDecoration(dividerItemDecoration);
        recyleview.removeItemDecoration(dividerGridViewItemDecoration);
        switch (currentViewType){
            case Constnats.VIEW_TYPE_LISTVIEW:
                recyleview.addItemDecoration(dividerItemDecoration);
                recyleview.setLayoutManager(linearLayoutManager);
                break;
            case Constnats.VIEW_TYPE_GRIDVIEW:
                recyleview.addItemDecoration(dividerGridViewItemDecoration);
                recyleview.setLayoutManager(gridLayoutManager);
                break;
            case Constnats.VIEW_TYPE_HORIZONTALGRIDVEW:
                recyleview.addItemDecoration(dividerGridViewItemDecoration);
                recyleview.setLayoutManager(horizontalGridLayoutManager);
                break;
            case Constnats.VIEW_TYPE_STAGGEREDGRIDVIEW:
                recyleview.addItemDecoration(dividerGridViewItemDecoration);
                recyleview.setLayoutManager(staggeredGridLayoutManager);
                break;
        }
    }

    /**
     * 数据添加
     * @param position
     */
    private void addData(int position){
        adapter.addData(position);
    }

    private void delData(int position){
        if(position < data.size()){
            adapter.removeData(position);
        }else{
            Toast.makeText(this,"删除的条目不存在!",Toast.LENGTH_SHORT).show();
        }
    }

    private void initData(){
        for (int i = 0; i < 30; i++){
            data.add("item"+i);
        }
    }
}
