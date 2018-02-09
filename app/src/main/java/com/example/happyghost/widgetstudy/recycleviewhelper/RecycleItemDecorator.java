package com.example.happyghost.widgetstudy.recycleviewhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.happyghost.widgetstudy.R;

/**
 * @author Zhao Chenping
 * @creat 2017/7/8.
 * @description
 */

public class RecycleItemDecorator  extends RecyclerView.ItemDecoration{

    private Context context;
    private final Paint paint;
    private final Bitmap bitmap;


    /**
     * 构造方法 传一些 你可能需要的参数进来
     *
     * @param context
     */
    public RecycleItemDecorator(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }


    /**
     * 此方法发生在 画条目之前， 所以会被条目所遮挡
     *
     * @param c      画布
     * @param parent 整个RecyclerView
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        //RecyclerView 左边画了一条线
//        c.drawRect( 1, 0,  8, parent.getBottom(), paint);
//
//        //然后分别给显示在界面的条目画 装饰
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            //计算他们是在整个RecyclerView的左边还是右边，得出 支线应该怎么画(parent.getWidth() / 2-child.getWidth()-child.getHeight()/4-6)
////            if (child.getLeft() < parent.getWidth() / 2) {
////            Log.e("parent.getWidth()",String.valueOf(parent.getWidth()));
////            Log.e("child.getWidth()",String.valueOf(child.getWidth()));
//                c.drawRect(1,
//                        child.getBottom() - (child.getBottom() - child.getTop())/ 2 - 5,
//                        (parent.getWidth() / 2-child.getWidth()-child.getHeight()/4-6),
//                        child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
//            System.out.println((parent.getWidth() -child.getWidth()-child.getHeight()));
////            } else {
////                c.drawRect(1,
////                        child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
////                        child.getRight() - (child.getRight() - child.getLeft()) / 2,
////                        child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
////            }
//
//        }

    }

    /**
     * 此方法发生在 画条目之后， 所以这里所绘画的东西会正当住条目内容
     *
     * @param c      画布
     * @param parent 整个RecyclerView
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
//        //给每个条目之后画上一个 小图标 也可以增加一些不同的东西
//        Paint paintCircle = new Paint();
//        paintCircle.setAntiAlias(true);
//        paintCircle.setColor(Color.RED);
//        paintCircle.setStyle(Paint.Style.STROKE);
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
////            float left = bitmap.getWidth() / 2;
////            float top = child.getTop() + child.getHeight() / 2 - bitmap.getHeight() / 2;
////            c.drawBitmap(bitmap, left, top, paint);parent.getWidth()/2-child.getWidth()-
//            c.drawCircle(child.getLeft(),(child.getTop()+child.getHeight()/2),child.getHeight()/4,paintCircle);
//        }
    }

    /**
     * 这个方法用来设置 RecyclerView 条目的偏移量的
     *
     * @param outRect 矩阵
     * @param view    当前的Item View
     * @param parent  指的是RecyclerView
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //用这个方法来这是 条目周围边距便宜量，分别为 左上右下
        //比如当前这个，就是左边空出50px距离，上面不空，右边空出50px，下面不空
        //outRect.left = 50; 或者这种方式来设置
        //outRect.top = 0;
        //outRect.right = 50;
        //outRect.bottom = 0;
//        outRect.set(50, 20, 0, 20);
    }
}
