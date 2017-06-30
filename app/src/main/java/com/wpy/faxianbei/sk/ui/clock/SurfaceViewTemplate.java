package com.wpy.faxianbei.sk.ui.clock;

import android.content.Context;
import android.graphics.Canvas;  
import android.util.AttributeSet;  
import android.view.SurfaceHolder;  
import android.view.SurfaceView;  
  
/** 
 *surfaceView的模板 
 * Created by wangpeiyu on 2016/11/21. 
 */  
public abstract class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {  
    protected SurfaceHolder mSurfaceHolder;  
    //surfaceView的画布，进行view的绘制  
    protected Canvas mCanvas;  
    //绘制的子线程  
    protected Thread mThread;  
    //子线程运行的Runnable  
    protected Runnable mRunnable;  
    //决定线程运行和结束  
    boolean isStart = false;  
  
    public SurfaceViewTemplate(Context context) {  
        this(context, null);  
    }  
  
    public SurfaceViewTemplate(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initNecessaryVariables();  
        initVariables();//调用子类的初始化变量方法  
    }  
    //初始化必要的变量  
    private void initNecessaryVariables() {  
        mRunnable = this;  
        mSurfaceHolder = getHolder();  
        mSurfaceHolder.addCallback(this);  
        setFocusable(true);  
        setFocusableInTouchMode(true);  
        setKeepScreenOn(false);
        mThread = new Thread(mRunnable);  
    }  
  
  
    /** 
     * 当view创建的时候进行回调 
     * 一般在该方法中开启绘制view的子线程 
     * 该方法在ui线程执行 
     * @param holder 该SurfaceView的SurfaceViewHolder对象 
     */  
    @Override  
    public void surfaceCreated(SurfaceHolder holder) {  
        isStart = true;  
        mThread=new Thread(this);
        mThread.start();
        create();  
    }  
  
    /** 
     * 该方法在ui线程执行 
     * 当SurfaceView的尺寸发生变化的时候进行回调（比如竖屏切换到横屏的时候） 
     * @param holder SurfaceView的SurfaceViewHolder对象 
     * @param format 格式 
     * @param width 宽度 
     * @param height 高度 
     */  
    @Override  
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {  
        change();  
    }  
  
    /** 
     * 该方法中ui线程执行 
     * 当SurfaceView被销毁的时候执行 
     * @param holder 
     */  
    @Override  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        isStart = false;  
        destroy();  
    }  
  
    /** 
     * 进行view的绘制 
     */  
    @Override  
    public void run() {  
        while (isStart) {  
            try {  
                draw();  
            } catch (Exception e) {  
            } finally {  
                if (mCanvas != null) {  
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);  
                }  
            }  
        }  
    }  
    protected abstract void draw();  
    protected abstract void create();  
    protected abstract void change();  
    protected abstract void destroy();  
    protected abstract void initVariables();  
}  