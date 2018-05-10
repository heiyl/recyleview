package com.materialdesign.heiyl.recyleview;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RVElementaryAdapter extends RecyclerView.Adapter<RVElementaryAdapter.ElementaryViewHolder> {

    //数据源
    private List<String> data;
    private List<Integer> heights;
    private int viewType = Constnats.VIEW_TYPE_LISTVIEW;

    //构造方法
    public RVElementaryAdapter(List<String> data){
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
    public void onBindViewHolder(@NonNull RVElementaryAdapter.ElementaryViewHolder holder, int position) {
        //绑定数据

        if(viewType == Constnats.VIEW_TYPE_STAGGEREDGRIDVIEW){
            ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
            params.height = heights.get(position);
        }else{
            ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        holder.tv.setText(data.get(position));
        holder.tv.setBackgroundColor(Color.rgb(100, (int)(Math.random()*255), (int)(Math.random()*255)));
        if(itemClickListener != null) {
            holder.tv.setOnClickListener(new MyClickListener(position));
        }
        if(itemLongClickListener != null) {
            holder.tv.setOnLongClickListener(new MyLongClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return data == null? 0 :data.size();
    }

    /**
     * 设置视图模式
     * @param viewType
     */
    public void setViewType(int viewType) {
        this.viewType = viewType;
        notifyDataSetChanged();
    }

    public class ElementaryViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public ElementaryViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }
    }

    /**
     * 添加条目
     * @param position
     */
    public void addData(int position){
        data.add(position,"additem"+position);
        heights.add(position,(int) Math.max(200,Math.random()*600));
        notifyItemInserted(position);

        // 加入如下代码保证position的位置正确性
        if (position != data.size() - 1) {
            //列表从positionStart位置到itemCount数量的列表项进行数据刷新
            notifyItemRangeChanged(position, data.size() - position);
        }
//		notifyDataSetChanged();
    }

    /**
     * 移除条目
     * @param position
     */
    public void removeData(int position){
        data.remove(position);
        heights.remove(position);
        notifyItemRemoved(position);

        // 加入如下代码保证position的位置正确性
        if (position != data.size() - 1) {
            //列表从positionStart位置到itemCount数量的列表项进行数据刷新
            notifyItemRangeChanged(position, data.size() - position);
        }
//        notifyDataSetChanged();
    }


    private ItemClickListener itemClickListener;
    private ItemClickListener itemLongClickListener;
    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    /**
     * 设置长按点击事件
     */
    public void setOnItemLongClickListener(ItemClickListener itemLongClickListener){
        this.itemLongClickListener = itemLongClickListener;
    }
    public interface ItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
    /**
     * 条目的点击事件
     */
    public class MyClickListener implements View.OnClickListener{
        int position;
        MyClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(position);
        }
    }

    /**
     * 条目的长按事件
     */
    public class MyLongClickListener implements View.OnLongClickListener{
        int position;
        MyLongClickListener(int position){
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            itemLongClickListener.onItemLongClick(position);
            return true;
        }
    }
}
