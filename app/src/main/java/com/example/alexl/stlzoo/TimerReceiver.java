package com.example.alexl.stlzoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AlexL on 4/9/2018.
 */

public class TimerReceiver extends BroadcastReceiver {

    private Integer id;
    private String userIDMA;
    private String initialTimeStr;
    private String counterStr;
    private String broadcastStr;
    private String event_name;
    private Integer broadcastID;

    @Override
    public void onReceive(Context context, Intent intent) {

        broadcastStr = intent.getStringExtra("broadcast Int");
        event_name = intent.getStringExtra("Event Name");
        broadcastID = Integer.parseInt(broadcastStr);


        Intent service_intent = new Intent(context, NotificationService.class);
        //service_intent.putExtra("User ID", userIDMA);
        service_intent.putExtra("broadcast Int", broadcastStr);
        service_intent.putExtra("Event Name", event_name);
        //This is where it crashed
        context.startForegroundService(service_intent);
    }

}
