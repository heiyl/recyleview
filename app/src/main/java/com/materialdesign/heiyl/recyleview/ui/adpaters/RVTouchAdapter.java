package com.materialdesign.heiyl.recyleview.ui.adpaters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.materialdesign.heiyl.recyleview.R;
import com.materialdesign.heiyl.recyleview.touch.ItemTouchMoveListener;
import com.materialdesign.heiyl.recyleview.touch.StartDragListener;

import java.util.Collections;
import java.util.List;

public class RVTouchAdapter extends RecyclerView.Adapter<RVTouchAdapter.ElementaryViewHolder> implements ItemTouchMoveListener{

    //数据源
    private List<String> data;
    private StartDragListener dragListener;

    //构造方法
    public RVTouchAdapter(List<String> data, StartDragListener dragListener){
        this.data = data;
        this.dragListener = dragListener;
    }

    @NonNull
    @Override
    public ElementaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_touch, parent,false);
        ElementaryViewHolder elementaryViewHolder = new ElementaryViewHolder(itemView);
        return elementaryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVTouchAdapter.ElementaryViewHolder holder, int position) {
        //绑定数据

        holder.tvTitle.setText("条目标题"+data.get(position));
        holder.tvContent.setText("这里是条目的内容"+data.get(position));
        holder.ivHead.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(dragListener != null){
                        dragListener.startDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onItemMove(int startPosition, int tagetPosition) {
        Collections.swap(data,startPosition,tagetPosition);
        this.notifyItemMoved(startPosition,tagetPosition);
    }

    @Override
    public void onItemReMove(int adapterPosition) {
        data.remove(adapterPosition);
        this.notifyItemRemoved(adapterPosition);
    }

    @Override
    public int getItemCount() {
        return data == null? 0 :data.size();
    }


    public class ElementaryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvContent;
        private ImageView ivHead;
        public ElementaryViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivHead = itemView.findViewById(R.id.iv_head);
        }
    }
}
