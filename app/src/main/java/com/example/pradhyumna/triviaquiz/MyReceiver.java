package com.example.pradhyumna.triviaquiz;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) { //check android build level
            NotificationChannel channel = new NotificationChannel("MyChannel", "todoChannel", NotificationManager.IMPORTANCE_HIGH);//creating a new notification channel for api greater than 26
            manager.createNotificationChannel(channel);// actually creating a notification channel in manager
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"MyChannel");//creating a notification builder with a context and channel
        builder.setContentTitle("Trivia Quiz");//set title
        builder.setContentText("Time For Trivia Quiz! Gear up for some mind bogglers \uD83D\uDE00");//set text
        Intent intent1= new Intent(context.getApplicationContext(),MainActivity.class); //creating intent to be performed when notification is clicked
        PendingIntent pendingIntent= PendingIntent.getActivity(context.getApplicationContext(),1,intent1,0);//creating a pending intent for given intent
        builder.setContentIntent(pendingIntent);//set the pending content to  notification
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);//set the icon of notification
        Notification notification=builder.build();//actually building the notification
        manager.notify(1,notification);//showing the notification

    }
}
