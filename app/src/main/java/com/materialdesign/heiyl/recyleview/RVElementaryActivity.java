package com.materialdesign.heiyl.recyleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * recyleview基本用法
 */
public class RVElementaryActivity extends AppCompatActivity implements RVElementaryAdapter.ItemClickListener{

    private RecyclerView recyleview;
    private RVElementaryAdapter adapter;
    private RVElementaryStaggerdGridAdapter rvElementaryStaggerdGridAdapter;
    private List<String> data = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager horizontalGridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;

    private int currentViewType = Constnats.VIEW_TYPE_LISTVIEW;

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

        recyleview = this.findViewById(R.id.recyleview);
        //LayoutManager布局摆放管理器(线性摆放、瀑布流)
        //九宫格布局管理
        gridLayoutManager = new GridLayoutManager(this,3);
        //九宫格横向滑动
        horizontalGridLayoutManager = new GridLayoutManager(this,3,RecyclerView.HORIZONTAL,false);
        //瀑布流效果布局管理
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);
        //线性布局管理：reverseLayout:数据倒置，水平布局从右边开始滑动 垂直布局
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyleview.setLayoutManager(linearLayoutManager);

        adapter = new RVElementaryAdapter(data);
        rvElementaryStaggerdGridAdapter = new RVElementaryStaggerdGridAdapter(data);

        adapter.setOnItemClickListener(this);
        recyleview.setAdapter(adapter);
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

    /**
     *切换布局效果
     */
    private void changeView(){
        adapter.setViewType(currentViewType);
        switch (currentViewType){
            case Constnats.VIEW_TYPE_LISTVIEW:
                recyleview.setLayoutManager(linearLayoutManager);
                break;
            case Constnats.VIEW_TYPE_GRIDVIEW:
                recyleview.setLayoutManager(gridLayoutManager);
                break;
            case Constnats.VIEW_TYPE_HORIZONTALGRIDVEW:
                recyleview.setLayoutManager(horizontalGridLayoutManager);
                break;
            case Constnats.VIEW_TYPE_STAGGEREDGRIDVIEW:
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
        adapter.removeData(position);
    }

    private void initData(){
        for (int i = 0; i < 30; i++){
            data.add("item"+i);
        }
    }
}
