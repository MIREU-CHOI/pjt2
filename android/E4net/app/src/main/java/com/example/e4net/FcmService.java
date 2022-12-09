package com.example.e4net;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
//        super.onMessageReceived(message);
        if (message.getData() != null){
            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel
                        = new NotificationChannel(
                                "channel_id01", "fcmservice", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("description...");
                manager.createNotificationChannel(channel);
            }
            Intent intent = new Intent(this, FcmWebviewActivity.class);
            PendingIntent pendingIntent = PendingIntent
                                            .getActivity(this, 15,
                                                        intent, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id01")
                    .setContentTitle(message.getData().get("title"))
                    .setContentText(message.getData().get("content"))
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
            manager.notify(16, builder.build());

        }
    }
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("[fcmservice]", "token="+token);
    }
}