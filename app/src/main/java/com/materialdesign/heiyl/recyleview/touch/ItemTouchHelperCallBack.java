package com.materialdesign.heiyl.recyleview.touch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.materialdesign.heiyl.recyleview.R;

public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    ItemTouchMoveListener moveListener;

    public ItemTouchHelperCallBack(ItemTouchMoveListener moveListener) {
        this.moveListener = moveListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
       int dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
       int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
       int flag = makeMovementFlags(dragFlag,swipeFlag);
       return flag;
    }

    /**
     * 是否允许长按拖拽效果
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if(viewHolder.getItemViewType() != target.getItemViewType()){
            return false;
        }
        if(moveListener != null){
            moveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(moveListener != null){
            moveListener.onItemReMove(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorAccent));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            float alpha = 1-Math.abs(dX)/viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);//1~0
            viewHolder.itemView.setScaleX(alpha);//1~0
            viewHolder.itemView.setScaleY(alpha);//1~0
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
