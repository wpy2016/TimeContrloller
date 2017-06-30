package com.wpy.faxianbei.sk.ui.clock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.wpy.faxianbei.sk.R;

import java.util.Calendar;

/**
 * Created by wangpeiyu on 2017/6/28.
 */

public class ClockLine extends SurfaceViewTemplate {

    //左边内边距
    private int mPadding;
    //中心点位置
    private int mCenter;
    //可绘制区域的直径
    private int mDiameter;
    //可绘制区域的正方形区域
    private RectF mRectf;
    //绘制的画笔
    private Paint mPaint;

    //时针数值
    private int mHour;
    //分针数值
    private int mMinute;
    //秒针数值
    private int mSeconds;

    //背景图片
    private Bitmap mbmBackground;



    //时针长的那边长度
    private double hourLenght;

    //分针长的那边长度
    private double minuteLenght;

    //秒针长的那边长度
    private double secondLenght;

    private double shortLenght;

    //日历，获取时间
    Calendar calendar;

    //表示一度有多少弧度
    double unit;

    Paint mPaintSecond;


    public ClockLine(Context context) {
        super(context);
    }

    public ClockLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void draw() {
        updateTime();
        mCanvas = mSurfaceHolder.lockCanvas();
        mCanvas.drawColor(Color.parseColor("#ffffff"));
        mCanvas.drawBitmap(mbmBackground, null, mRectf, null);
        drawSecondCircleInit();
        drawHour();
        drawMinute();
        drawSecond();
        drawSecondCircle();
    }


    private void drawSecondCircleInit() {
        for(int i=0;i<60;i++){
            float degree = (float) getDegree(i);
            double radian = getRadian(degree);
            float lenght= (float) (mDiameter*0.5*0.75);
            int x= (int) (mCenter+lenght*Math.sin(radian));
            int y= (int) (mCenter-lenght*Math.cos(radian));
            mCanvas.drawCircle(x, y, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) (1.3+0.03*i), getResources().getDisplayMetrics()), mPaintSecond);
        }
    }

    private void drawSecondCircle() {
        float degree = (float) getDegree(mSeconds);
        double radian = getRadian(degree);
        float lenght= (float) (mDiameter*0.5*0.75);
        int x= (int) (mCenter+lenght*Math.sin(radian));
        int y= (int) (mCenter-lenght*Math.cos(radian));
        mCanvas.drawCircle(x, y, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) (1.3+0.03*mSeconds), getResources().getDisplayMetrics()), mPaint);
        mCanvas.drawCircle(mCenter,mCenter,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4, getResources().getDisplayMetrics()),mPaintSecond);
    }

    private void drawSecond() {
        float degree = (float) getDegree(mSeconds);
        double radian = getRadian(degree);
        float x = (float) (mCenter - shortLenght * Math.sin(radian));
        float y = (float) (mCenter + shortLenght * Math.cos(radian));
        float x1= (float) (mCenter+secondLenght*Math.sin(radian));
        float y1= (float) (mCenter-secondLenght*Math.cos(radian));
        mCanvas.drawLine(x,y,x1,y1,mPaint);
    }

    private void drawMinute() {
        float degree = (float) getDegree(mMinute);
        double radian = getRadian(degree);
        float x = (float) (mCenter - shortLenght * Math.sin(radian));
        float y = (float) (mCenter + shortLenght * Math.cos(radian));
        float x1= (float) (mCenter+minuteLenght*Math.sin(radian));
        float y1= (float) (mCenter-minuteLenght*Math.cos(radian));
        mCanvas.drawLine(x,y,x1,y1,mPaint);
    }

    private void drawHour() {
        float degree = mHour * 30;
        double radian = getRadian(degree)+(mMinute/60.0d)*getRadian(30);
        float x = (float) (mCenter - shortLenght * Math.sin(radian));
        float y = (float) (mCenter + shortLenght * Math.cos(radian));
        float x1= (float) (mCenter+hourLenght*Math.sin(radian));
        float y1= (float) (mCenter-hourLenght*Math.cos(radian));
        mCanvas.drawLine(x,y,x1,y1,mPaint);
    }


    private double getRadian(float degree) {
        return degree * unit;
    }

    private double getDegree(int point) {
        return point * 6;
    }

    private void updateTime() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
        mSeconds = calendar.get(Calendar.SECOND);
    }

    @Override
    protected void create() {

    }

    @Override
    protected void change() {

    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void initVariables() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.parseColor("#458e4d"));
        mbmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.center);
        calendar = Calendar.getInstance();
        unit = Math.PI / 180;
        mPaintSecond = new Paint();
        mPaintSecond.setAntiAlias(true);
        mPaintSecond.setDither(true);
        mPaintSecond.setColor(Color.parseColor("#e6e4e2"));
    }

    private void initLenght() {
        hourLenght = mDiameter*0.5*0.5;
        minuteLenght = mDiameter*0.5*0.6;
        secondLenght = mDiameter*0.5*0.7;
        shortLenght=mDiameter*0.5*0.5*0.15;
    }

    /**
     * 确保当前的大小是正方形
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desireLenght = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(desireLenght, desireLenght);
        mPadding = getPaddingLeft();
        mCenter = desireLenght / 2;
        mDiameter = desireLenght - mPadding * 2;
        mRectf = new RectF(mPadding, mPadding, desireLenght - mPadding, desireLenght - mPadding);
        initLenght();
    }
}
