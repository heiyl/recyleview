package com.materialdesign.heiyl.recyleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * recyleview基本用法
 */
public class RVElementaryActivity extends AppCompatActivity implements View.OnClickListener,RVElementaryAdapter.ItemClickListener{

    private RecyclerView recyleview;
    private RVElementaryAdapter adapter;
    private RVElementaryStaggerdGridAdapter rvElementaryStaggerdGridAdapter;
    private List<String> data = new ArrayList<>();
    private TextView tv_layout_manager;
    private TextView tv_add;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvelementary);
        initViews();
    }

    private void initViews(){
        initData();
        recyleview = this.findViewById(R.id.recyleview);
        tv_layout_manager = this.findViewById(R.id.tv_layout_manager);
        tv_add = this.findViewById(R.id.tv_add);
        tv_layout_manager.setOnClickListener(this);
        tv_add.setOnClickListener(this);

        //LayoutManager布局摆放管理器(线性摆放、瀑布流)
        //九宫格布局管理
        gridLayoutManager = new GridLayoutManager(this,3);
        //瀑布流效果布局管理
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);
        //线性布局管理：reverseLayout:数据倒置，水平布局从右边开始滑动 垂直布局
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyleview.setLayoutManager(staggeredGridLayoutManager);

        adapter = new RVElementaryAdapter(data);
        rvElementaryStaggerdGridAdapter = new RVElementaryStaggerdGridAdapter(data);

        adapter.setOnItemClickListener(this);
        recyleview.setAdapter(adapter);
//        recyleview.setAdapter(rvElementaryStaggerdGridAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"当前点击的条目为" + data.get(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_layout_manager:
                changeView();
                break;
            case R.id.tv_add:
                addData(0);
                break;
        }
    }


    boolean isList = false;
    /**
     *切换布局效果
     */
    private void changeView(){
        if(!isList){
            recyleview.setLayoutManager(linearLayoutManager);
        }else{
            recyleview.setLayoutManager(staggeredGridLayoutManager);
        }
        isList = !isList;
    }

    /**
     * 数据添加
     * @param position
     */
    private void addData(int position){
        adapter.addData(position);
    }

    private void initData(){
        for (int i = 0; i < 30; i++){
            data.add("item"+i);
        }
    }
}
