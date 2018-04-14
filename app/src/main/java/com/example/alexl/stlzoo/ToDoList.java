package com.example.alexl.stlzoo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samikshasm on 4/5/18.
 */

public class ToDoList extends AppCompatActivity {
    // Array of strings...
    ListView simpleList;
    private ArrayList<String> toDoListItems = new ArrayList<>();
    private ArrayList<String> timeItems = new ArrayList<>();
    private ArrayList<Integer> timeIntItems = new ArrayList<>();
    private ArrayList<String> nameItems = new ArrayList<>();
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;
    private TextView textView;
    private AlarmManager alarmManager;
    private Intent resultIntent;
    private PendingIntent pIntent;
    public static final String CHANNEL_ID = "com.samalex.slucapstone.ANDROID";



    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        final Set<String> selectedItemsSet = getSelectedItemsList();
        final ArrayList<String> selectedItems = new ArrayList<String>();
        if(selectedItemsSet != null){
            for (String str : selectedItemsSet)
                selectedItems.add(str);
        }



        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        for (int i=0; i<selectedItems.size(); i++){
            String[] substr = selectedItems.get(i).split(",");
            Integer substrLength = substr.length;
            for (int j=0; j<substrLength; j++){

                String[] equalsSign = substr[j].split("=");
                if (equalsSign[1].substring(equalsSign[1].length()-1).equals("}")){
                    String separated = equalsSign[1].replace(equalsSign[1].substring(equalsSign[1].length()-1), "");
                    nameItems.add(separated);
                }
                else{
                    timeItems.add(equalsSign[1]);
                    String[] timeSubstr = equalsSign[1].split(":");
                    Integer timeHour = Integer.parseInt(timeSubstr[0]);
                    timeIntItems.add(timeHour);
                }
            }
        }


        for(int x=0;x<nameItems.size();x++){
            toDoListItems.add(""+timeItems.get(x)+": "+nameItems.get(x));
        }

        //startAlarm();

        String broadcastStr = getIntent().getStringExtra("broadcast Int");
        if (broadcastStr != null){
            int broadcastInt = Integer.parseInt(broadcastStr);
            NotificationManager manager = (NotificationManager) ToDoList.this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(broadcastInt);
            manager.deleteNotificationChannel(CHANNEL_ID);
        }


        textView = findViewById(R.id.textView);
        textView.setText("To-Do List");

        simpleList = (ListView)findViewById(R.id.animals_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_animal, R.id.Name, toDoListItems);
        simpleList.setAdapter(arrayAdapter);

        final Button clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> selectedItems = new ArrayList<String>();
                for(int i = 0; i < selectedItemsSet.size(); i++){
                    cancelAlarm(i);
                }
                storeSelectedItemsList(selectedItems);
                textView.setText("No events added - go to Events tab");
                simpleList.setAdapter(null);
            }

        });

        mDrawer = findViewById(R.id.drawer_layout);

        nvView = findViewById(R.id.nvView);
        nvView.setItemIconTintList(null);

        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.rivers_edge){
                    Log.d("Entered case 1", "Entered case1");
                    Intent riverAct = new Intent(getApplicationContext(), RiversEdgeActivity.class);
                    startActivity(riverAct);
                }
                if(menuItem.getItemId() == R.id.the_wild) {
                    Intent wildAct = new Intent(getApplicationContext(), TheWildActivity.class);
                    startActivity(wildAct);
                }
                if(menuItem.getItemId() == R.id.discovery_center) {
                    Intent discoveryAct = new Intent(getApplicationContext(), DiscoveryCenterActivity.class);
                    startActivity(discoveryAct);
                }
                if(menuItem.getItemId() == R.id.historic_hill) {
                    Intent historicAct = new Intent(getApplicationContext(), HistoricHillActivity.class);
                    startActivity(historicAct);
                }
                if(menuItem.getItemId() == R.id.lakeside_crossing) {
                    Intent lakesideAct = new Intent(getApplicationContext(), LakesideCrossingActivity.class);
                    startActivity(lakesideAct);
                }
                if(menuItem.getItemId() == R.id.red_rocks) {
                    Intent redAct = new Intent(getApplicationContext(), RedRocksActivity.class);
                    startActivity(redAct);
                }
                if(menuItem.getItemId() == R.id.dining) {
                    Intent diningAct = new Intent(getApplicationContext(), DiningActivity.class);
                    startActivity(diningAct);
                }
                if(menuItem.getItemId() == R.id.map) {
                    Intent mapAct = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(mapAct);
                }
                if(menuItem.getItemId() == R.id.events) {
                    Intent eventsAct = new Intent(getApplicationContext(), EventsActivity.class);
                    startActivity(eventsAct);
                }
                mDrawer.closeDrawers();
                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Set<String> getSelectedItemsList() {
        SharedPreferences mSharedPreferences = getSharedPreferences("selected items list", MODE_PRIVATE);
        Set<String> stringSet  = mSharedPreferences.getStringSet("animals", null);
        return stringSet;
    }

    public void storeSelectedItemsList (ArrayList<String> selectedItemsList) {
        SharedPreferences mSharedPreferences = getSharedPreferences("selected items list", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        Set<String> stringSet = new HashSet<String>(selectedItemsList);
        mEditor.putStringSet("animals", stringSet);
        mEditor.apply();
    }

    public void startAlarm() {

        AlarmManager mgrAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

        Log.e("timeIntItems",timeIntItems+"");

        ArrayList<Integer> timeIntTest = new ArrayList<Integer>();
        timeIntTest.add(22);

        for(int i = 0; i < timeIntItems.size(); ++i)
        {
            Integer setTimeTo = timeIntItems.get(i);
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int difference = Math.abs(setTimeTo-hour);
            long diffHours = difference % 24;

            Log.e("broadcast int",  ""+i);
            Log.e("event name", toDoListItems.get(i));

            Calendar morningCal = Calendar.getInstance();

            Log.e("morningCal", morningCal.getTimeInMillis()+"");

            Intent intent = new Intent(ToDoList.this, TimerReceiver.class);
            intent.putExtra("broadcast Int", (i+1)+"");
            intent.putExtra("Event Name", toDoListItems.get(i));
            Log.e("index;", i+"");
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ToDoList.this, i+1, intent, 0);
            mgrAlarm.set(AlarmManager.RTC_WAKEUP, morningCal.getTimeInMillis(), pendingIntent);

            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            /*
            mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + (3600000 * diffHours),
                    pendingIntent);

            intentArray.add(pendingIntent);
            */
        }
    }

    public void cancelAlarm(int broadcastID) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        resultIntent = new Intent(ToDoList.this, TimerReceiver.class);
        pIntent = PendingIntent.getBroadcast(ToDoList.this, broadcastID, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pIntent);
        pIntent.cancel();

    }
}