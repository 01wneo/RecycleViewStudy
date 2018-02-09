package com.example.happyghost.widgetstudy.recycleviewhelper;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.happyghost.widgetstudy.ipl.OnSwipListener;

import java.util.List;

/**
 * @author Zhao Chenping
 * @creat 2017/7/14.
 * @description
 */

public class CardItemTouchHelperCallBack<T> extends ItemTouchHelper.Callback {
    private final List<T> mDataList;
    private final RecyclerView.Adapter mAdapter;
    private OnSwipListener<T> mOnSwipListener;

    public CardItemTouchHelperCallBack(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList) {
        this.mAdapter = checkIsNull(adapter);
        this.mDataList = checkIsNull(dataList);
    }
    public CardItemTouchHelperCallBack(@NonNull RecyclerView.Adapter mAdapter, @NonNull List<T> mDataList, @NonNull OnSwipListener<T> onSwipListener) {
        this.mDataList = checkIsNull(mDataList);
        this.mAdapter = checkIsNull(mAdapter);
        mOnSwipListener =checkIsNull( onSwipListener);
    }
    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public void setOnSwipListener(OnSwipListener<T> listener){
        this.mOnSwipListener=listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags =0;
        int swipFlags=0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof CardLayoutManager){
            swipFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags,swipFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.itemView.setOnTouchListener(null);
        int layoutPosition = viewHolder.getLayoutPosition();
        T remove = mDataList.remove(layoutPosition);
        mAdapter.notifyDataSetChanged();
        if(mOnSwipListener!=null){
            mOnSwipListener.onSwiped(viewHolder,remove,direction==ItemTouchHelper.LEFT?CardLayoutManager.SWIPED_LEFT:
                    CardLayoutManager.SWIPED_RIGHT);
        }
        if(mAdapter.getItemCount()==0){
            if (mOnSwipListener!=null){
                mOnSwipListener.onSwipClear();
            }
        }
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            if(ratio>1){
                ratio=1;
            }else if(ratio<-1){
                ratio=-1;
            }
            itemView.setRotation(ratio*CardLayoutManager.DEFAULT_ROTATE_DEGREE);
            int childCount = recyclerView.getChildCount();
            if(childCount> CardLayoutManager.DEFAULT_SHOW_ITEM){
                for(int position=1;position<childCount;position++){
                    View childAt = recyclerView.getChildAt(position);
                    int index=childCount-position-1;
                    childAt.setScaleX(1-index*CardLayoutManager.DEFAULT_SCALE+Math.abs(ratio)*CardLayoutManager.DEFAULT_SCALE);
                    childAt.setScaleY(1-index*CardLayoutManager.DEFAULT_SCALE+Math.abs(ratio)*CardLayoutManager.DEFAULT_SCALE);
                    childAt.setTranslationY(index-Math.abs(ratio)*itemView.getMeasuredHeight()/CardLayoutManager.DEFAULT_TRANSLATE_Y);
                }
            }else {
                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * CardLayoutManager.DEFAULT_SCALE + Math.abs(ratio) * CardLayoutManager.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardLayoutManager.DEFAULT_SCALE + Math.abs(ratio) * CardLayoutManager.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardLayoutManager.DEFAULT_TRANSLATE_Y);
                }
            }
            if(mOnSwipListener!=null){
                if(ratio!=0){
                    mOnSwipListener.onSwiping(viewHolder,ratio,ratio < 0 ? CardLayoutManager.SWIPING_LEFT : CardLayoutManager.SWIPING_RIGHT);
                }else {
                    mOnSwipListener.onSwiping(viewHolder,ratio,CardLayoutManager.SWIPING_NONE);
                }
            }
        }

    }
    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

}
