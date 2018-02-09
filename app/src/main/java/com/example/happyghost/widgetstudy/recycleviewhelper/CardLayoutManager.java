package com.example.happyghost.widgetstudy.recycleviewhelper;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Zhao Chenping
 * @creat 2017/7/13.
 * @description
 */

public class CardLayoutManager extends RecyclerView.LayoutManager{
    private final ItemTouchHelper mItemTouchHelper;
    private final RecyclerView mRecyclerView;
    public static int DEFAULT_SHOW_ITEM = 3;
    /**
     * 默认缩放的比例
     */
    public static final float DEFAULT_SCALE = 0.1f;
    /**
     * 卡片Y轴偏移量时按照14等分计算
     */
    public static final int DEFAULT_TRANSLATE_Y = 14;
    /**
     * 卡片滑动时默认倾斜的角度
     */
    public static final float DEFAULT_ROTATE_DEGREE = 15f;
    /**
     * 卡片滑动时不偏左也不偏右
     */
    public static final int SWIPING_NONE = 1;
    /**
     * 卡片向左滑动时
     */
    public static final int SWIPING_LEFT = 1 << 2;
    /**
     * 卡片向右滑动时
     */
    public static final int SWIPING_RIGHT = 1 << 3;
    /**
     * 卡片从左边滑出
     */
    public static final int SWIPED_LEFT = 1;
    /**
     * 卡片从右边滑出
     */
    public static final int SWIPED_RIGHT = 1 << 2;

    public CardLayoutManager(@NonNull RecyclerView recycle, @NonNull ItemTouchHelper itemTouchHelper) {
         this.mRecyclerView=checkIsNull(recycle);
        this.mItemTouchHelper = checkIsNull(itemTouchHelper);
    }
    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        removeAllViews();
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if(itemCount>DEFAULT_SHOW_ITEM){
            for(int position = DEFAULT_SHOW_ITEM;position>=0;position--){
                View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view,0,0);
                int heightSpace = getHeight()-getDecoratedMeasuredHeight(view);
                int widthSpace = getWidth()-getDecoratedMeasuredWidth(view);
                layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2,widthSpace/2+getDecoratedMeasuredWidth(view),
                        heightSpace/2+getDecoratedMeasuredHeight(view));
                if(position==DEFAULT_SHOW_ITEM){
                    view.setScaleX(1-(position-1)*DEFAULT_SCALE);
                    view.setScaleY(1-(position-1)*DEFAULT_SCALE);
                    view.setTranslationY((position-1)*view.getMeasuredHeight()/DEFAULT_TRANSLATE_Y);
                }else if(position>0){
                    view.setScaleX(1-position*DEFAULT_SCALE);
                    view.setScaleY(1-position*DEFAULT_SCALE);
                    view.setTranslationY(position*view.getMeasuredHeight()/DEFAULT_TRANSLATE_Y);
                }else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            } 
        }else {
            for(int position = itemCount-1;position>=0;position--) {
                View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2, widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));
                if (position > 0) {
                    view.setScaleX(1 - position * DEFAULT_SCALE);
                    view.setScaleY(1 - position * DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }

        }

    }
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if(MotionEventCompat.getActionMasked(event)==MotionEvent.ACTION_DOWN){
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };

}
