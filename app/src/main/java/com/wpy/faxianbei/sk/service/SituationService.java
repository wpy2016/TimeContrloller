package com.wpy.faxianbei.sk.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;

public class SituationService extends Service {

    public static final int NORMAL=0;
    public static  final int SLIENT=1;
    public static final int SHAKE=2;
    public SituationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       int situation= intent.getIntExtra("situation",0);
        AudioManager manager= (AudioManager) SituationService.this.getSystemService(AUDIO_SERVICE);
        switch (situation)
        {
            case NORMAL:
                manager.setRingerMode(RINGER_MODE_NORMAL);
                break;
            case SLIENT:
                manager.setRingerMode(RINGER_MODE_SILENT);
                break;
            case SHAKE:
                manager.setRingerMode(RINGER_MODE_NORMAL);
                break;
            default:
                break;
        }
        manager.setRingerMode(RINGER_MODE_SILENT);
        return super.onStartCommand(intent, flags, startId);
    }
}
