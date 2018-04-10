package com.example.alexl.stlzoo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by AlexL on 4/9/2018.
 */

public class NotificationService extends Service {


    private Integer counterInt=0;
    public static final String CHANNEL_ID = "com.samalex.slucapstone.ANDROID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        int NOTIFICATION_ID;
        String broadcastId = intent.getStringExtra("broadcast Int");
        String event_name = intent.getStringExtra("Event Name");
        NOTIFICATION_ID = Integer.parseInt(broadcastId);

            Intent toDoIntent = new Intent(this, ToDoList.class);
            toDoIntent.putExtra("broadcast Int", broadcastId);
            PendingIntent mainPI = PendingIntent.getActivity(this, 0, toDoIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = getString(R.string.channel_name);
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                channel.enableLights(true);
                channel.enableVibration(true);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon_small))
                    .setContentTitle("Saint Louis Zoo")
                    .setContentText(event_name+" is starting soon!")
                    //.setFullScreenIntent(yesIntent1, true)
                    .setFullScreenIntent(mainPI, true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            builder.setDefaults(Notification.DEFAULT_SOUND);


            //builder.setContentIntent(mainPI);
            //NotificationManagerCompat nManager = NotificationManagerCompat.from(this);
            //nManager.notify(NOTIFICATION_ID, builder.build());
            startForeground(NOTIFICATION_ID, builder.build());
        return START_NOT_STICKY;

    }
}

