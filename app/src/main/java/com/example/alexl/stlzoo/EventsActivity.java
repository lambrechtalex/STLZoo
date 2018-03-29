package com.example.alexl.stlzoo;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

public class EventsActivity extends AppCompatActivity {

    CalendarView simpleCalendarView;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);

        nvView = findViewById(R.id.nvView);

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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);
        return true;
    }


}