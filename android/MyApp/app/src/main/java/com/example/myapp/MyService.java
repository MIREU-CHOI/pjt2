package com.example.myapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }

    NotificationManager manager;
    private final String NOTI_CHANNEL_ID = "";
    private final int NOTI_ID = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    NOTI_CHANNEL_ID
                    , "channel_name"
                    , NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("description");
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            String data = intent.getStringExtra("data");
            Log.d("[myservice]", "data="+data);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForeground(NOTI_ID, getNotification());
        }else {
            manager.notify(NOTI_ID, getNotification());
        }

        // task ...

        return START_REDELIVER_INTENT; // 기존의 딜리버를 사용하겠다.
    }

    private Notification getNotification(){
        Intent intent = new Intent(this, NextActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        return new NotificationCompat.Builder(this, NOTI_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText("서비스 실행 중")
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.cancel(NOTI_ID);
    }




}