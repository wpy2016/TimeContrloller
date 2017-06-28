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

public class ClockCopy extends SurfaceViewTemplate {


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

    //时针的图片
    private Bitmap mbmHour;
    //分针的图片
    private Bitmap mbmMinute;

    //秒针的图片
    private Bitmap mbmSeconds;


    //时针短的那边长度
    private double hourLenght;

    //分针短的那边长度
    private double minuteLenght;

    //秒针短的那边长度
    private double secondLenght;

    //日历，获取时间
    Calendar calendar;

    //表示一度有多少弧度
    double unit;

    Matrix matrix;

    Paint mPaintSecond;


    public ClockCopy(Context context) {
        super(context);
    }

    public ClockCopy(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void draw() {
        updateTime();
        mCanvas = mSurfaceHolder.lockCanvas();
        mCanvas.drawColor(Color.parseColor("#f6f6f6"));
        mCanvas.drawBitmap(mbmBackground, null, mRectf, null);
        drawSecondCircleInit();
        drawHour();
        drawMinute();
        drawSecondCircle();
    }

    private void drawSecondCircleInit() {
        mPaintSecond.setColor(Color.parseColor("#e6e4e2"));
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
        mPaintSecond.setColor(Color.parseColor("#458e4d"));
            float degree = (float) getDegree(mSeconds);
            double radian = getRadian(degree);
            float lenght= (float) (mDiameter*0.5*0.75);
            int x= (int) (mCenter+lenght*Math.sin(radian));
            int y= (int) (mCenter-lenght*Math.cos(radian));
            mCanvas.drawCircle(x, y, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) (1.3+0.03*mSeconds), getResources().getDisplayMetrics()), mPaintSecond);
    }

    private void drawSecond() {
        matrix.reset();
        float degree = (float) getDegree(mSeconds);
        double radian = getRadian(degree);
        float x = (float) (mCenter - secondLenght * Math.sin(radian));
        float y = (float) (mCenter + secondLenght * Math.cos(radian));
        mCanvas.translate(x, y);
        matrix.postRotate(degree - 180);
        mCanvas.drawBitmap(mbmSeconds, matrix, mPaint);
        mCanvas.translate(-x,-y);
    }

    private void drawMinute() {
    //    matrix.reset();
        float degree = (float) getDegree(mMinute);
        double radian = getRadian(degree);
        float x = (float) (mCenter - minuteLenght * Math.sin(radian));
        float y = (float) (mCenter + minuteLenght * Math.cos(radian));
        mCanvas.translate(x, y);
      //  matrix.postRotate(degree - 180);
       // mCanvas.drawBitmap(mbmMinute, matrix, mPaint);
        mCanvas.translate(-x,-y);
    }

    private void drawHour() {
      //  matrix.reset();
        float degree = mHour * 30;
        double radian = getRadian(degree);
        float x = (float) (mCenter - hourLenght * Math.sin(radian));
        float y = (float) (mCenter + hourLenght * Math.cos(radian));
        mCanvas.translate(x, y);
     //   matrix.postRotate(degree-180);
      //  mCanvas.drawBitmap(mbmHour, matrix, mPaint);

        mCanvas.translate(-x,-y);
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
        mPaint.setDither(true);
        mbmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.clock_bg);
        mbmHour = BitmapFactory.decodeResource(getResources(), R.drawable.clock_hour);
        mbmMinute = BitmapFactory.decodeResource(getResources(), R.drawable.clock_minute);
        mbmSeconds = BitmapFactory.decodeResource(getResources(), R.drawable.clock_seconds);
        calendar = Calendar.getInstance();
        unit = Math.PI / 180;
        hourLenght = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        minuteLenght = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        secondLenght = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics());
        matrix = new Matrix();
        mPaintSecond = new Paint();
        mPaintSecond.setAntiAlias(true);
        mPaintSecond.setDither(true);
        mPaintSecond.setColor(Color.parseColor("#e6e4e2"));
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
    }
}
