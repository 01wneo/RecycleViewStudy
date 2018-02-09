package com.example.happyghost.widgetstudy.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.happyghost.widgetstudy.R;

/**
 * @author Zhao Chenping
 * @creat 2017/7/12.
 * @description
 */

public class ProgressCustomView extends View {

    private int mDownTime;
    private String mText;
    private Paint mArcPaint;
    private Paint mCirPaint;
    private Paint mTextPaint;
    private int mRadius;
    private int mWidth;
    private int mHeight;
    private float mDynamicAngle;
    private PCviewOnClickListener pcOnClickListener;
    private Rect mTextRect;



    private int defaultSize;


    private int mBackGroundColor;
    private int mArcColor;
    private int mTextColor;
    private float mTextSize;
    private ObjectAnimator mAnimation;
    private float mArcSize;

    public ProgressCustomView(Context context) {
        this(context,null);
    }

    public ProgressCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ProgressCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //对控件进行自定义属性的设置，提高控件的扩展性，可以自由控制控件的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressCustomView);
        mDownTime = typedArray.getInt(R.styleable.ProgressCustomView_pc_time, 5);
        mText = typedArray.getString(R.styleable.ProgressCustomView_pc_text);
        mBackGroundColor = typedArray.getColor(R.styleable.ProgressCustomView_pc_background, Color.GRAY);
        mArcColor = typedArray.getColor(R.styleable.ProgressCustomView_pc_arccolor, Color.BLUE);
        mTextColor = typedArray.getColor(R.styleable.ProgressCustomView_pc_textcolor, Color.BLACK);
        mTextSize = typedArray.getDimension(R.styleable.ProgressCustomView_pc_textsize, 20);
        mArcSize = typedArray.getDimension(R.styleable.ProgressCustomView_pc_arc_size, 3);
        typedArray.recycle();
        //初始化画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcSize);

        mCirPaint = new Paint();
        mCirPaint.setAntiAlias(true);
        mCirPaint.setColor(mBackGroundColor);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        //对控件设置点击监听
        initClickListener();
    }

    private void initClickListener() {
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pcOnClickListener!=null){
                    pcOnClickListener.onClick();
                    startAnimation();
                }
            }
        };
        setOnClickListener(clickListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        defaultSize = 100;

        if(widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultSize, defaultSize);
        }else if(widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultSize,heightSize);
        }else if(heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, defaultSize);
        }else {
            setMeasuredDimension(widthSize,heightSize);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //获取控件的宽高
        mWidth = getWidth();
        mHeight = getHeight();
        //获取圆的半径
        mRadius = Math.min(mWidth, mHeight)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景圆
        drawCir(canvas);
        //绘制圆环
        drawArc(canvas);
        //绘制文字
        drawText(canvas);
    }

    private void drawArc(Canvas canvas) {
        RectF rectF = new RectF(mWidth/2-mRadius+mArcSize,mHeight/2-mRadius+mArcSize,mWidth/2+mRadius-mArcSize,mHeight/2+mRadius-mArcSize );
        mTextRect = new Rect();
//        mDynamicAngle = 360/mDownTime;
        canvas.drawArc(rectF,0,mDynamicAngle,false,mArcPaint);
    }

    private void drawText(Canvas canvas) {
        mTextPaint.getTextBounds(mText,0,mText.length(),mTextRect);
        canvas.drawText(mText,mWidth/2-mTextRect.width()/2,mHeight/2+mTextRect.height()/2,mTextPaint);
    }

    private void drawCir(Canvas canvas) {
        canvas.drawCircle(mWidth/2,mHeight/2,mRadius,mCirPaint);
    }
    //view销毁的时候执行该方法
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimation();
    }

    /**
     * 开始动画
     */
    public void startAnimation(){
        if(mAnimation==null){
            mAnimation = ObjectAnimator.ofFloat(this,"mDynamicAngle",0,360);
        }
        mAnimation.setDuration(mDownTime*1000);
        mAnimation.start();

    }

    private void cancelAnimation() {
        if(mAnimation!=null&&mAnimation.isRunning()){
            mAnimation.removeAllListeners();
            mAnimation.cancel();
            mAnimation=null;
        }
    }
    public void setMDynamicAngle(float downTime){
        this.mDynamicAngle = downTime;
        invalidate();
    }
    public void setmText(String text){
        this.mText=text;
    }
    public void setmDownTime(int time){
        this.mDownTime = time;
        invalidate();
    }
    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
        invalidate();
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setmBackGroundColor(int mBackGroundColor) {
        this.mBackGroundColor = mBackGroundColor;
        invalidate();
    }

    public void setmArcColor(int mArcColor) {
        this.mArcColor = mArcColor;
        invalidate();
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    public int getmDownTime() {
        return mDownTime;
    }

    public String getmText() {
        return mText;
    }

    public int getmBackGroundColor() {
        return mBackGroundColor;
    }

    public int getmArcColor() {
        return mArcColor;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setPCviewOnClickListener(PCviewOnClickListener pCviewOnClickListener){
        this.pcOnClickListener = pCviewOnClickListener;
    }
    public interface PCviewOnClickListener{
        void onClick();
    }
}
