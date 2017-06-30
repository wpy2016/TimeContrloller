package com.wpy.faxianbei.sk.activity.coursetable2.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.view.AcAddCourse;
import com.wpy.faxianbei.sk.activity.addevent.view.AcAddEvent;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.coursetable.presenter.PresenterCourseTable;
import com.wpy.faxianbei.sk.activity.coursetable.view.IViewCourseTable;
import com.wpy.faxianbei.sk.activity.coursetable2.presenter.PresenterCourseTable2;
import com.wpy.faxianbei.sk.adapter.CommonAdapterArray;
import com.wpy.faxianbei.sk.adapter.ViewHolder;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.utils.dateUtil.DateUtil;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@ContentView(R.layout.ac_course_table_new)
public class AcCourseTable2 extends MvpBaseActivity<IViewCourstTable2, PresenterCourseTable2> implements IViewCourseTable, IViewCourstTable2 {
    @ViewInject(R.id.id_ac_coursetable2_tv_semester)
    TextView mtvSemester;
    @ViewInject(R.id.id_ac_coursetable2_iv_add_semester)
    ImageView mivAddSemester;
    @ViewInject(R.id.id_ac_coursetable2_tv_week)
    TextView mtvWeek;
    @ViewInject(R.id.id_ac_coursetable2_tv_1)
    TextView mtvDay1;
    @ViewInject(R.id.id_ac_coursetable2_tv_2)
    TextView mtvDay2;
    @ViewInject(R.id.id_ac_coursetable2_tv_3)
    TextView mtvDay3;
    @ViewInject(R.id.id_ac_coursetable2_tv_4)
    TextView mtvDay4;
    @ViewInject(R.id.id_ac_coursetable2_tv_5)
    TextView mtvDay5;
    @ViewInject(R.id.id_ac_coursetable2_tv_6)
    TextView mtvDay6;
    @ViewInject(R.id.id_ac_coursetable2_tv_7)
    TextView mtvDay7;
    @ViewInject(R.id.id_ac_coursetable2_month)
    TextView mtvMonth;
    @ViewInject(R.id.id_ac_coursetable2_gv_timeitem)
    GridView mGvTable;
    @ViewInject(R.id.id_ac_coursetable2_iv_semester_down)
    ImageView mivModifySemester;
    @ViewInject(R.id.id_ac_coursetable2_iv_week_down)
    ImageView mivModifyWeek;

    private Context mContext;
    private int year = 2016;
    private int semester = 1;
    int currentweek = 1;
    int week = currentweek;//选中的周次
    int[] bgid = {R.drawable.surround1, R.drawable.surround2, R.drawable.surround3,
            R.drawable.surround4, R.drawable.surround5,
            R.drawable.surround6, R.drawable.surround7,
            R.drawable.surround8, R.drawable.surround9,
            R.drawable.surround10, R.drawable.surround11,
            R.drawable.surround12};

    private PresenterCourseTable mPresenterCourseTable;

    private String[] arrayCourstTable;

    private CommonAdapterArray<String> adapter;

    private CourseTableTogether[] set;

    private int curPos = 200;

    public final long weekTIime = 7 * 24 * 60 * 60 * 1000l;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化listCourstTable
        arrayCourstTable = new String[200];
        initListData();
        set = new CourseTableTogether[200];
        mPresenterCourseTable.getDateFormInternet(year, semester);
        mPresenterCourseTable.initDate(mContext);
        mPresenterCourseTable.initDay(System.currentTimeMillis());
        initEvent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcCourseTable2.this;
        //赋值presenter
        mPresenterCourseTable = new PresenterCourseTable();
        mPresenterCourseTable.attachView(this);
    }

    private void initEvent() {
        mGvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (0 != position % 8) {
                    if (set[position] != null) {
                        if(set[position].isCourse)
                        {
                            Toast.makeText(AcCourseTable2.this, "当前有课，不能添加自定义任务，专心上课哦", Toast.LENGTH_LONG).show();
                        }else{
                            Intent intent = new Intent(AcCourseTable2.this, AcAddEvent.class);
                            intent.putExtra("has",true);
                            intent.putExtra("id",set[position].id);
                            AcCourseTable2.this.startActivity(intent);
                        }
                    } else {
                        if (position == curPos) {//说明两次点击这个,需要跳转到添加时间段界面，此时只能编辑事件，不能编辑时间了
                            curPos = 200;
                            adapter.setSelectPos(200);
                            adapter.notifyDataSetChanged();
                            /******************做其他操作***********************/
                            String date = getDateString(position);
                            String start = getStartOfPos(position);
                            String end = getEndOfPos(position);
                            int day = getDayOfPos(position);
                            Intent intent = new Intent(AcCourseTable2.this, AcAddEvent.class);
                            intent.putExtra("has",false);
                            intent.putExtra("start", start);
                            intent.putExtra("end", end);
                            intent.putExtra("date", date);
                            intent.putExtra("day", day);
                            AcCourseTable2.this.startActivity(intent);
                        } else {
                            TextView textView = (TextView) view.findViewById(R.id.id_ac_coursetable2_gv_item);
                            textView.setBackgroundResource(R.drawable.add_new);
                            curPos = position;
                            adapter.setSelectPos(curPos);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            }


        });
    }

    private int getDayOfPos(int position) {
        return position % 8;//1表示星期一，二表示星期二
    }

    private String getEndOfPos(int position) {
        int offset = position / 8;
        return offset + ":" + 59;
    }

    private String getStartOfPos(int position) {
        int offset = position / 8;
        return offset + ":00";
    }

    private String getDateString(int position) {
        int offset = position % 8 - 1;
        int dayInt = DateUtil.parseIntFormDayString(DateUtil.getDay(System.currentTimeMillis()));
        //获取到选中的时间
        long time = (offset - dayInt) * 24 * 60 * 60 * 1000l + (week - currentweek) * weekTIime + System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    private void initListData() {
        int time = 0;
        for (int i = 0; i < 200; i++) {
            if (i % 8 == 0) {
                arrayCourstTable[i] = time + ":00";
                time++;
            }
        }
    }

    /**
     * 使用GridView来显示课程数据
     *
     * @param week
     */
    private void setData(int week) {
        mtvWeek.setText("第 " + week + " 周");
        arrayCourstTable = new String[200];
        initListData();
        long current = (week - currentweek) * weekTIime + System.currentTimeMillis();
        mPresenterCourseTable.initDay(current);
        mPresenterCourseTable.initMonth(current);
        set = new CourseTableTogether[200];
        /**
         * 循环获取课程信息
         */
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 7; j++) {
                String lesson = mPresenterCourseTable.getLesson(i, j, week, year, semester);
                if (lesson == null || lesson.equals("")) {//说明当前没有课

                } else {
                    int row[] = mPresenter.getPos(j);
                    int size = row.length;
                    String lessonSub[] = mPresenter.getSubStringByParts(size, lesson);
                    int color = ((int) (Math.random() * 1000)) % 12;
                    for (int k = 0; k < size; k++) {
                        int pos = row[k] * 8 + i;
                        arrayCourstTable[pos] = lessonSub[k];
                        set[pos] = new CourseTableTogether();
                        set[pos].isCourse = true;
                        set[pos].together = row;
                        set[pos].colorPos = bgid[color];
                    }
                }
            }
        }
        try {
            List<TimeItem> timeItems = SKApplication.getDbManager().findAll(TimeItem.class);
            if (timeItems != null && !timeItems.isEmpty()) {
                /**
                 * 遍历所有的item
                 */
                for (TimeItem item : timeItems) {
                    //当前item的类型是normal类型的
                    if (item.getType() == AcAddEvent.EVENTNORMAL) {
                        //表示当前的item是不重复的
                        if (0 == item.getIsRecycle()) {
                            handlerNormalItemNoRecycle(item, week);
                        } else {//表示当前的item是重复的
                            handlerNormalItemRecycle(item, week);
                        }
                    } else {//表示item是课程类型
                        //表示当前的item是不重复的
                        if (0 == item.getIsRecycle()) {
                            handlerCourseItemNoRecycle(item, week);
                        } else {//表示当前的item是重复的
                            handlerCourseItemRecycle(item, week);
                        }
                    }
                }
            }
        } catch (ParseException | DbException e) {
        }
        adapter = new CommonAdapterArray<String>(this, arrayCourstTable, R.layout.coursetable_item_layout) {
            @Override
            public void convert(ViewHolder helper, String item, int pos, int selectPos) {
                TextView tvItem = (TextView) helper.getView(R.id.id_ac_coursetable2_gv_item);
                tvItem.setBackgroundColor(Color.WHITE);
                if (item != null) {
                    tvItem.setText(item);
                    int offset = pos % 8;
                    if (offset != 0) {
                        if (set[pos] != null)
                            tvItem.setBackgroundResource(set[pos].colorPos);
                    }
                } else {
                    tvItem.setText("");
                }
                if (pos == selectPos) {
                    if (set[pos] == null)
                        tvItem.setBackgroundResource(R.drawable.add_new);
                    else {
                        curPos = 200;
                    }
                }
            }
        };
        adapter.setSelectPos(curPos);
        mGvTable.setAdapter(adapter);
    }

    private void handlerCourseItemRecycle(TimeItem item, int week) {
        handlerCourseItemRecycleByDay(item, week, 1, item.getMonday());
        handlerCourseItemRecycleByDay(item, week, 2, item.getTuesday());
        handlerCourseItemRecycleByDay(item, week, 3, item.getWednesday());
        handlerCourseItemRecycleByDay(item, week, 4, item.getThursDay());
        handlerCourseItemRecycleByDay(item, week, 5, item.getFriday());
        handlerCourseItemRecycleByDay(item, week, 6, item.getSaturday());
        handlerCourseItemRecycleByDay(item, week, 7, item.getSunday());
    }

    private void handlerCourseItemNoRecycle(TimeItem item, int week) throws ParseException {
        //课程的开始时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = format.parse(item.getStart());
        long timeMills = date.getTime();
        //所选周的最早时间，也就是周一早上00：00点的时间戳
        long early = getEarlyTimeMillsOfWeek(week, format);
        long last = getLastTimeMillsOfWeek(week, format);
        if (timeMills >= early && timeMills <=last) {//表示当前的item在所选的周中
            int day = DateUtil.parseIntFormDayString(DateUtil.getDay(timeMills)) + 1;
            int rowstart = mPresenter.getRowIntByString(item.getStart().substring(item.getStart().indexOf(" ") + 1));
            int rowend = mPresenter.getRowIntByString(item.getEnd().substring(item.getEnd().indexOf(" ") + 1));
            int offset = rowend - rowstart;
            String[] strParts = mPresenter.getSubStringByParts(offset + 1, item.getContent());
            int[] together = createIntArrayByTwoInt(rowstart, rowend);
            int color = ((int) (Math.random() * 1000)) % 12;
            for (int i = 0; i <= offset; i++) {
                int pos = (rowstart + i) * 8 + day;
                if (set[pos] != null && set[pos].isCourse) {
                    //当前已经有课程，不显示
                } else {
                    arrayCourstTable[pos] = strParts[i];
                    set[pos] = new CourseTableTogether();
                    set[pos].together = together;
                    set[pos].colorPos = bgid[color];
                    set[pos].isCourse = false;
                    set[pos].id = item.getId();
                }
            }
        }
    }

    public int[] createIntArrayByTwoInt(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        int[] result = new int[max - min + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + min;
        }
        return result;
    }

    private long getLastTimeMillsOfWeek(int week, SimpleDateFormat format) throws ParseException {
        long current = (week - currentweek) * weekTIime + System.currentTimeMillis();
        int weekday = DateUtil.parseIntFormDayString(DateUtil.getDay(current)) + 1;//此时1表示星期一，2表示星期二
        long last = (7 - weekday) * 24 * 60 * 60 * 1000l + current;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String strLast = dateFormat.format(new Date(last)) + " 23:59";
        return format.parse(strLast).getTime();
    }

    /**
     * @param week   所选的周
     * @param format yyyy.MM.dd HH:mm
     * @throws ParseException
     */
    private long getEarlyTimeMillsOfWeek(int week, SimpleDateFormat format) throws ParseException {
        long current = (week - currentweek) * weekTIime + System.currentTimeMillis();
        int weekday = DateUtil.parseIntFormDayString(DateUtil.getDay(current)) + 1;//此时1表示星期一，2表示星期二
        long early = (1 - weekday) * 24 * 60 * 60 * 1000l + current;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String strEarly = dateFormat.format(new Date(early)) + " 00:00";
        return format.parse(strEarly).getTime();
    }

    private void handlerNormalItemRecycle(TimeItem item, int week) {
        handlerNormalItemRecycleByDay(item, week, 1, item.getMonday());
        handlerNormalItemRecycleByDay(item, week, 2, item.getTuesday());
        handlerNormalItemRecycleByDay(item, week, 3, item.getWednesday());
        handlerNormalItemRecycleByDay(item, week, 4, item.getThursDay());
        handlerNormalItemRecycleByDay(item, week, 5, item.getFriday());
        handlerNormalItemRecycleByDay(item, week, 6, item.getSaturday());
        handlerNormalItemRecycleByDay(item, week, 7, item.getSunday());

    }

    private void handlerNormalItemRecycleByDay(TimeItem item, int week, int day, int ishave) {
        if (0 == ishave) {//表示当天不重复
            return;
        }
        int rowstart = mPresenter.getRowIntByString(item.getStart());
        int rowend = mPresenter.getRowIntByString(item.getEnd());
        int offset = rowend - rowstart;
        String[] strParts = mPresenter.getSubStringByParts(offset + 1, item.getContent());
        int[] together = createIntArrayByTwoInt(rowstart, rowend);
        int color = ((int) (Math.random() * 1000)) % 12;
        for (int i = 0; i <= offset; i++) {
            int pos = (rowstart + i) * 8 + day;
            if (set[pos] != null && set[pos].isCourse) {
                //当前已经有课程，不显示
            } else {
                arrayCourstTable[pos] = strParts[i];
                set[pos] = new CourseTableTogether();
                set[pos].together = together;
                set[pos].colorPos = bgid[color];
                set[pos].isCourse = false;
                set[pos].id = item.getId();
            }
        }
    }

    private void handlerCourseItemRecycleByDay(TimeItem item, int week, int day, int ishave) {
        if (0 == ishave) {//表示当天不重复
            return;
        }
        if (week >= item.getStartWeek() && week <= item.getEndWeek()) {
            //此时和日常事件一样了
            handlerNormalItemRecycleByDay(item, week, day, ishave);
        }
    }

    private void handlerNormalItemNoRecycle(TimeItem item, int week) throws ParseException {
        //这种情况下，和不重复的课程是一样的
        handlerCourseItemNoRecycle(item, week);
    }

    @Event(value = {R.id.id_ac_coursetable2_tv_semester, R.id.id_ac_coursetable2_iv_semester_down,
            R.id.id_ac_coursetable2_iv_week_down, R.id.id_ac_coursetable2_iv_add_semester, R.id.id_ac_coursetable2_tv_week})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_coursetable2_iv_semester_down:
            case R.id.id_ac_coursetable2_tv_semester:
                mPresenterCourseTable.showSemester(mContext, mtvWeek);
                break;
            case R.id.id_ac_coursetable2_iv_add_semester:
                toNext(AcAddCourse.class);
                break;
            case R.id.id_ac_coursetable2_iv_week_down:
            case R.id.id_ac_coursetable2_tv_week:
                mPresenterCourseTable.showPup(mContext, mtvWeek);
                break;
        }
    }

    public void toNext(Class<?> next) {
        Intent intent = new Intent(mContext, next);
        startActivity(intent);
    }


    @Override
    public PresenterCourseTable2 createPresenter() {
        return new PresenterCourseTable2();
    }


    @Override
    public void setWeek(int week,int currentweek) {
        this.week = week;
        this.currentweek=currentweek;
        setData(week);
    }

    @Override
    public void setYeayAndSemester(int year, int semester, String message) {
        mtvSemester.setText(message);
        SharePreferenceUtil.instantiation.saveCurrentSemester(mContext, message);
        this.year = year;
        this.semester = semester;
        mPresenterCourseTable.getDateFormInternet(year, semester);
        week = currentweek = mPresenterCourseTable.getCurrentWeek(mContext);
        setWeek(week,currentweek);
    }

    @Override
    public void setDate(String[] date) {
        for (int i = 1; i < 8; i++) {
            TextView textView = mPresenterCourseTable.getDayTextView(i, this);
            textView.setText(date[i - 1]);
        }
    }

    public void setMonth(String month) {
        mtvMonth.setText(month);
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

    private class CourseTableTogether {
        int[] together;
        int colorPos;
        boolean isCourse;
        long id;
    }
}
