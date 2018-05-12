package com.materialdesign.heiyl.recyleview.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.materialdesign.heiyl.recyleview.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_recyleview_elementary;
    private TextView tv_recyleview_diver;
    private TextView tv_recyleview_drafting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        tv_recyleview_elementary = this.findViewById(R.id.tv_recyleview_elementary);
        tv_recyleview_diver = this.findViewById(R.id.tv_recyleview_diver);
        tv_recyleview_drafting = this.findViewById(R.id.tv_recyleview_drafting);

        tv_recyleview_elementary.setOnClickListener(this);
        tv_recyleview_diver.setOnClickListener(this);
        tv_recyleview_drafting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_recyleview_elementary://recyleview基本用法
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RVElementaryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_recyleview_diver://recyleview添加分割线
                break;
            case R.id.tv_recyleview_drafting://recyleview拖拽动画
                break;
        }
    }
}
