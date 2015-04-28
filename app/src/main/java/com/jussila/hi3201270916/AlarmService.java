package com.jussila.hi3201270916;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {

    private AlarmManager alarmMng;
    private Timer updateTimer;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmMng = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        updateTimer = new Timer();

        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, new Intent("alarmDone"), 0);
        final int s = intent.getIntExtra("amountOfSeconds", 0);
        long timeFromNow = System.currentTimeMillis() + (s*1000);

        alarmMng.set(AlarmManager.RTC_WAKEUP, timeFromNow, pIntent);

        updateTimer.schedule(new TimerTask() {
            int countDown = s;

            @Override
            public void run() {
                countDown--;

                if(countDown <= 0){
                    updateTimer.cancel();
                }

                Intent progressIntent = new Intent("alarmProgress");
                progressIntent.putExtra("secondsRemaining", countDown);
                sendBroadcast(progressIntent);
            }
        }, 1000, 1000);

        return super.onStartCommand(intent, flags, startId);
    }
}
