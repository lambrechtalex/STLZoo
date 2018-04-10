package com.example.alexl.stlzoo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by samikshasm on 4/9/18.
 */

//class that receives input from no button in notification
public class ButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        String broadcastID = intent.getStringExtra("broadcast Int");
        int notificationId = Integer.parseInt(broadcastID);

        //cancels notification with given broadcast id
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }
}
