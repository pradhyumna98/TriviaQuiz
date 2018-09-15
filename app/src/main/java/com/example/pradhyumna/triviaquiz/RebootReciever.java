package com.example.pradhyumna.triviaquiz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent1 =new Intent(context,MyReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,4,intent1,0);
        long currentTime=System.currentTimeMillis();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,currentTime+5*1000,86400000,pendingIntent);
    }
}
