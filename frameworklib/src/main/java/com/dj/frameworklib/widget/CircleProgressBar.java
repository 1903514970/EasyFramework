package com.dj.frameworklib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dj.frameworklib.R;

/**
 * Created by dengjun on 2019/4/11.
 */

public class CircleProgressBar extends View {

    private static int defaultBgColor = Color.parseColor("#f3f3f3");
    private static int defaultProgressColor = Color.parseColor("#aaaaaa");
    private Paint mBgPaint;
    private Paint mProgressPaint;
    private float strokeWidth = 10;
    private int mBgColor = defaultBgColor;
    private int mProgressColor = defaultProgressColor;

    private float centerX = 0;
    private float centerY = 0;
    private float radius = 0;
    /**
     * 起始角度
     */
    private float startAngle = -90;

    private RectF arcRectF;

    /**
     * 进度
     */
    private int progress = 0;
    /**
     * 最大进度
     */
    private int maxProgress = 100;

    public CircleProgressBar(Context context) {
        super(context);
        init();
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CircleProgressBar);
        mBgColor = array.getColor(R.styleable.CircleProgressBar_bgColor,defaultBgColor);
        mProgressColor = array.getColor(R.styleable.CircleProgressBar_progressColor,defaultProgressColor);
        strokeWidth =  array.getDimension(R.styleable.CircleProgressBar_strokeWidth,10f);
        startAngle = array.getFloat(R.styleable.CircleProgressBar_startAngle,-90);
        array.recycle();

        init();
    }


    private void init(){
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStrokeWidth(strokeWidth);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeWidth(strokeWidth);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        centerX = width/2;
        centerY = height/2;
        radius = Math.min(width,height)/2-strokeWidth;

        arcRectF = new RectF(centerX-radius,centerY-radius,centerX+radius,centerY+radius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawProgress(canvas);
    }

    private void drawBg(Canvas canvas){
        canvas.drawCircle(centerX,centerY,radius,mBgPaint);
    }

    private void drawProgress(Canvas canvas){
        float swapAngle = 360*(progress*1.0f/maxProgress);
        canvas.drawArc(arcRectF,startAngle,swapAngle,false,mProgressPaint);
    }

    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(int progress){
        this.progress = progress >= maxProgress ? maxProgress : progress;
        invalidate();
    }
}
