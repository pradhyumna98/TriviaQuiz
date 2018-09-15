package com.example.pradhyumna.triviaquiz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView img;
    TextView tvtrivia,tvwelcome;
    Handler mhandler = new Handler();
    Boolean first;
    MediaPlayer player;
    SharedPreferences sp;
    public static final String preference= "Trivia Preferences",booleanKey="key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvtrivia=findViewById(R.id.trivia);
        tvwelcome=findViewById(R.id.welcome);
        player= MediaPlayer.create(this,R.raw.back);
        player.setLooping(true); // Set looping
        player.setVolume(1000,1000);
        player.start();
        sp=getSharedPreferences(preference,MODE_PRIVATE);
        first=sp.getBoolean(booleanKey,false);
        img = findViewById(R.id.logo);
        Animation ani = AnimationUtils.loadAnimation(MainActivity.this , R.anim.logo);
        img.startAnimation(ani);
        tvtrivia.startAnimation(ani);
        tvwelcome.startAnimation(ani);
       if(!first)
       {
           AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
           Intent intent =new Intent(this,MyReceiver.class);
           PendingIntent pendingIntent=PendingIntent.getBroadcast(this,4,intent,0);
           long currentTime=System.currentTimeMillis();
           alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,currentTime+5*1000,86400000,pendingIntent);
           SharedPreferences.Editor editor= sp.edit();
           editor.putBoolean(booleanKey,true);
           editor.commit();
       }
       mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(MainActivity.this , Username.class);
                startActivity(i);
                finish();
            }
        },10000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player!=null)
        {

        player.release();}
    }
}

