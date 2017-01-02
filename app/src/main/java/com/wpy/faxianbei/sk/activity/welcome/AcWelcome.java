package com.wpy.faxianbei.sk.activity.welcome;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.avos.avoscloud.AVOSCloud;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.CheckPermissionsActivity;
import com.wpy.faxianbei.sk.entity.Lessions;
import com.wpy.faxianbei.sk.service.LockInBackGroundService1;
import com.wpy.faxianbei.sk.utils.cqu.Crawler;
import com.wpy.faxianbei.sk.utils.lock.LockUtil;

import java.util.List;

/**
 * 欢迎界面
 */
public class AcWelcome extends CheckPermissionsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_welcome);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va","aLETvSFc2y1G2jmBWeBpSX96");
        openService();
        Button button = (Button) findViewById(R.id.lock);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        LockUtil.lock(AcWelcome.this);
                    }
                }.start();
            }
        });
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                Crawler crawler = new Crawler();
                String selType = "STU";
                // 密码账号
                String username = "20141675";
                String userPassword = "hw465520";
                // String schoolId = "10611";
                String result="";
                try{
                    // 登录账号
                    crawler.stuLogin(selType, username, userPassword);
                    // 获取本学期的课程，取完后才能进行查询
                    String lesson = crawler.getLessonsHtml("2016", "0");
                    // 获取个人信息
                    String stuInfomation = crawler.getStuInforHtml();
                    // 按周查询课程
                    String weekLesson = crawler.queryLessons(6);
                    // 按week与day查询课程
                    String weekAndDayLesson = crawler.queryLessons(11,2);


                    List<Lessions> lessionses = JSONArray.parseArray(weekLesson, Lessions.class);
                    result = lessionses.get(1).getLesson();

                }catch(Exception e){
                    result="失败";
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(AcWelcome.this,s,Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
    private void openService() {
        Intent intent = new Intent(AcWelcome.this, LockInBackGroundService1.class);
        startService(intent);
    }
}
