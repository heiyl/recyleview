package com.materialdesign.heiyl.recyleview.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.materialdesign.heiyl.recyleview.R;
import com.materialdesign.heiyl.recyleview.ui.adpaters.MyAdapter;
import com.materialdesign.heiyl.recyleview.wrap.WrapRecyleView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyleView-添加头部和底部
 */
public class RVHeaderActivity extends AppCompatActivity {

    private WrapRecyleView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvheader);
        recyclerView = (WrapRecyleView) findViewById(R.id.recyclerView);
        addHeaderView();
        addFooterView();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add("item "+i);
        }
        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void addFooterView() {
        TextView footerView = new TextView(this);
        LayoutParams paramsFooter = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(paramsFooter);
        footerView.setGravity(Gravity.CENTER);
        footerView.setPadding(25,25,25,25);
        footerView.setBackgroundResource(android.R.color.darker_gray);
        footerView.setText("FooterView");
        recyclerView.addFooterView(footerView);
    }

    private void addHeaderView() {
        TextView headerView = new TextView(this);
        LayoutParams paramsHeader = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(paramsHeader);
        headerView.setGravity(Gravity.CENTER);
        headerView.setPadding(25,25,25,25);
        headerView.setBackgroundResource(android.R.color.holo_orange_light);
        headerView.setText("HeaderView");
        recyclerView.addHeaderView(headerView);
    }
}
