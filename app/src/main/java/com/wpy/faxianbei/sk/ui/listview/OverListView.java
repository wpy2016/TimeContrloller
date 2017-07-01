package com.wpy.faxianbei.sk.ui.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListView;

import com.wpy.faxianbei.sk.R;

/**
 * 仿QQ空间可拉伸头部特效
 * <p>
 * 作者：余天然 on 16/9/14 下午10:25
 */
public class OverListView extends ListView {

    private View headerView;//头部视图
    private View scaleView;//可拉伸视图
    private View rotateView;//可旋转视图
    private int defaultScaleHeight = 0;//可拉伸视图的默认高度

    public OverListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        headerView = LayoutInflater.from(context).inflate(R.layout.view_header, null);
        scaleView = headerView.findViewById(R.id.iv_head_list);
        rotateView = headerView.findViewById(R.id.iv_user_head);
        addHeaderView(headerView);

        //手动调用测量方法
        scaleView.measure(0, 0);
        //调用measure方法之后才能获取宽高,否则的话getMeasuredHeight()=0
        defaultScaleHeight = scaleView.getMeasuredHeight();
    }

    /**
     * 过度滑动
     * <p>
     * 注意：deltaX与deltaY与坐标系方向相反！！！
     *
     * @param deltaX         水平增量（-右拉过度，+左拉过度）
     * @param deltaY         竖直增量（-下拉过度，+上拉过度）
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //下拉过度时
        if (deltaY < 0) {
            //放大scaleView
            scaleView.getLayoutParams().height = scaleView.getHeight() - deltaY;
            scaleView.requestLayout();
            rotateView.setRotation(rotateView.getRotation() - deltaY);
        }

        //一种特殊情况：当放大后的scaleView.height+listview.height=屏幕高度时，再往上推时，会触发上拉过度，而不会调用onScrollChanged中的缩小方法
        if (deltaY > 0 && scaleView.getHeight() > defaultScaleHeight) {
            //缩小scaleView
            scaleView.getLayoutParams().height = scaleView.getHeight() - deltaY;
            scaleView.requestLayout();
            rotateView.setRotation(rotateView.getRotation() - deltaY);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    /**
     * 滑动中
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View header = (View) scaleView.getParent();
        //scleView被放大了，且正在往上推时
        if (scaleView.getHeight() > defaultScaleHeight && header.getTop() < 0) {
            rotateView.setRotation(rotateView.getRotation() + header.getTop());
            //缩小scleView
            scaleView.getLayoutParams().height = scaleView.getHeight() + header.getTop();
            //同时，将header.top设置为0,(之前其实有一部分跑到屏幕上面了)
            header.layout(scaleView.getLeft(), 0, scaleView.getRight(), scaleView.getBottom());
            scaleView.requestLayout();

        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //监听松开手
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            ResetAnimation animation = new ResetAnimation();
            animation.setDuration(300);
            scaleView.startAnimation(animation);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 松手后恢复默认状态的动画
     */
    class ResetAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //interpolatedTime:[0,1],表示执行百分比
            //缩小scleView
            scaleView.getLayoutParams().height = calcBetweenValue(scaleView.getHeight(), defaultScaleHeight, interpolatedTime);
            scaleView.requestLayout();
            rotateView.setRotation(calcBetweenValue((int) rotateView.getRotation(), 0, interpolatedTime));
        }
    }

    /**
     * 计算中间值
     *
     * @param src      起始值
     * @param dest     终止值
     * @param progress 进度[0,1]
     * @return
     */
    private int calcBetweenValue(int src, int dest, float progress) {
        return (int) (src + (dest - src) * progress);
    }

}