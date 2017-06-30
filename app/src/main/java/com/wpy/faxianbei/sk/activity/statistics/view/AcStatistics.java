package com.wpy.faxianbei.sk.activity.statistics.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jn.chart.charts.LineChart;
import com.jn.chart.components.Legend;
import com.jn.chart.components.XAxis;
import com.jn.chart.components.YAxis;
import com.jn.chart.data.Entry;
import com.jn.chart.manager.LineChartManager;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.share.view.AcShare;
import com.wpy.faxianbei.sk.activity.statistics.presenter.StatisticsPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.ac_statistics)
public class AcStatistics extends MvpBaseActivity<IViewStatistics, StatisticsPresenter> implements IViewStatistics {

    @ViewInject(R.id.id_ac_statistics_tv_needtolock)
    TextView mtvNeedtolock;
    @ViewInject(R.id.id_ac_statistics_tv_lock)
    TextView mtvLock;
    @ViewInject(R.id.id_ac_statistics_tv_effciency)
    TextView mtvEffciency;
    @ViewInject(R.id.lineChart)
    private LineChart mLineChart;
    @ViewInject(R.id.id_ac_statistics_tv_time)
    private TextView mtvTime;
    @ViewInject(R.id.id_ac_statistics_iv_screenshot)
    private ImageView mivScreenShot;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcStatistics.this;
        mPresenter.setTime();
        initMychart();
        initEvent();
        mPresenter.loadDate(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setNeedtoLock(mContext);
        mPresenter.setLock(mContext);
        mPresenter.setEffiency(mContext);
    }

    private void initEvent() {
        mivScreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.screenShot(mContext);
            }
        });
    }

    @Override
    public StatisticsPresenter createPresenter() {
        return new StatisticsPresenter();
    }


    private void initMychart() {
        //设置图表的描述
        mLineChart.setDescription("");
        //XY轴描述
        mLineChart.setXYDesc("天数(day)", "累积时间(min)", 10, Color.rgb(150, 150, 150));
        //图例
        Legend l = mLineChart.getLegend();
        l.setTextSize(8);
        l.setTextColor(Color.rgb(150, 150, 150));
        //x轴
        XAxis xa = mLineChart.getXAxis();
        xa.setTextColor(Color.rgb(150, 150, 150));
        //左y轴
        YAxis ya = mLineChart.getAxisLeft();
        ya.setTextColor(Color.rgb(150, 150, 150));
        //设置折线的名称
        LineChartManager.setLineName("应锁屏");
        LineChartManager.setLineName1("实际锁屏");

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dimissProgress() {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void loadSuccess(ArrayList<String> x, ArrayList<Entry> y1, ArrayList<Entry> y2) {
        //创建两条折线的图表
        LineChartManager.initDoubleLineChart(this, mLineChart, x, y1, y2);
    }

    @Override
    public void screenShot(String path) {
        if (path != null && !path.isEmpty()) {
            Intent intent = new Intent(mContext, AcShare.class);
            intent.putExtra("imgpath", path);
            startActivity(intent);
        } else {
            Toast.makeText(mContext, "截屏出错", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTime(String time) {
        mtvTime.setText(time);
    }

    @Override
    public void setNeedToLock(float needtolock) {
        if((needtolock+"").length()>3)
        {
            mtvNeedtolock.setText((""+needtolock).substring(0,4)+"h");
        }else{
            mtvNeedtolock.setText((""+needtolock)+"h");
        }
    }

    @Override
    public void setLock(float lock) {
        if((lock+"").length()>3)
        {
            mtvLock.setText((""+lock).substring(0,4)+"h");
        }else {
            mtvLock.setText((""+lock)+"h");
        }

    }

    @Override
    public void setEffiency(float effiency) {
        String streffiency=""+effiency;
        if(streffiency.length()>3)
        {
            mtvEffciency.setText((effiency+"").substring(0,4)+"%");
        }else {
            mtvEffciency.setText((effiency+"")+"%");
        }

    }
}
