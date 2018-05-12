package com.materialdesign.heiyl.recyleview.ui.adpaters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流效果
 */
public class RVElementaryStaggerdGridAdapter extends RecyclerView.Adapter<RVElementaryStaggerdGridAdapter.ElementaryViewHolder> {

    private List<String> data;
    private List<Integer> heights;

    public RVElementaryStaggerdGridAdapter(List<String> data){
        this.data = data;
        heights = new ArrayList<Integer>();
        for (int i = 0; i < data.size(); i++) {
            heights.add((int) Math.max(200,Math.random()*600));
        }

    }

    @NonNull
    @Override
    public ElementaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
        ElementaryViewHolder elementaryViewHolder = new ElementaryViewHolder(itemView);
        return elementaryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVElementaryStaggerdGridAdapter.ElementaryViewHolder holder, int position) {
        //绑定数据
        ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
        params.height = heights.get(position);
        holder.tv.setBackgroundColor(Color.rgb(100, (int)(Math.random()*255), (int)(Math.random()*255)));
        holder.tv.setLayoutParams(params);

        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null? 0 :data.size();
    }

    public class ElementaryViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public ElementaryViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }
    }

}
