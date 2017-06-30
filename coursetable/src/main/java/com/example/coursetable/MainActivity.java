package com.example.coursetable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    GridView mGridView;

    List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_course_table_new);
        mGridView=(GridView) findViewById(R.id.id_ac_coursetable_gv_timeitem);
        list=new ArrayList();
        int time=0;
        for(int i=0;i<200;i++)
        {
            if(i%8==0){
                list.add(time+":00");
                time++;
            }else{
                //内容初始化部分
                list.add("项目"+i);
            }
        }
        MyListAdapter listAdapter=new MyListAdapter(this,list);
        mGridView.setAdapter(listAdapter);
    }
}
