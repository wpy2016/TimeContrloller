package com.wpy.faxianbei.sk.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.widget.RemoteViews;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.welcome.AcWelcome;
import com.wpy.faxianbei.sk.entity.db.CourseTable;

public class myRunnable implements Runnable {

    CourseTable courseTable;

    Service service;

    public myRunnable(CourseTable courseTable, Service service) {
        this.courseTable = courseTable;
        this.service = service;
    }

    @Override
    public void run() {
        Notification.Builder builder = new Notification.Builder(service.getApplicationContext());
        Intent intent = new Intent(service, AcWelcome.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(service.getApplicationContext(), 110, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(service.getPackageName(), R.layout.notification_always);
        remoteViews.setTextViewText(R.id.notification_tv_lesson, courseTable.getCourse());
        remoteViews.setTextViewText(R.id.notification_tv_classroom, courseTable.getClassroom());
        remoteViews.setTextViewText(R.id.notification_tv_time, courseTable.getTime());
        remoteViews.setTextViewText(R.id.notification_tv_grade, "" );
        remoteViews.setImageViewResource(R.id.notification_iv_img, R.drawable.logo_new);
        Notification notification = builder.setContent(remoteViews)
                .setSmallIcon(R.drawable.logo_new)
                .setContentIntent(pendingIntent).build();
        service.startForeground(1, notification);
    }
}