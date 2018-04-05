package com.example.alexl.stlzoo;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samikshasm on 3/28/18.
 */


import android.widget.Toast;


public class EventsActivity extends AppCompatActivity {


    // Progress Dialog
    private ProgressDialog pDialog;

    CalendarView simpleCalendarView;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;
    private TextView header;
    private String currentDate;
    private ArrayList<String> selectedItems = new ArrayList<>();
    private ListView lv;


    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> diningList;

    // url to get all products list
    private static String url_all_animals = "http://franzcars.mynetgear.com:443/allEvents.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANIMALS = "animals";
    private static final String TAG_ANIMAL_ID = "Animal_ID";
    private static final String TAG_NAME = "Name";

    // products JSONArray
    JSONArray dining = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_animals);

        // Hashmap for ListView
        diningList = new ArrayList<HashMap<String, String>>();
        final Button toDoList = findViewById(R.id.toDoList);
        toDoList.setText("Add to Personal To-Do List");
        toDoList.setVisibility(View.GONE);


        // Loading products in Background Thread
        new LoadAllAnimals().execute();

       // Get listview
        lv = (ListView) findViewById(R.id.animals_list);
        //final ListView lv = getListView();
        lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);


        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String name = lv.getItemAtPosition(position).toString();
                selectedItems.add(name);
                TextView textView = view.findViewById(R.id.Name);
                textView.setTextColor(getResources().getColor(R.color.colorAccent));


                if (selectedItems.size()!=0) {
                    toDoList.setVisibility(View.VISIBLE); //To set visible
                    toDoList.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            Toast.makeText(EventsActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            /*
                            Intent i = new Intent(getApplicationContext(), ToDoList.class);
                            i.putStringArrayListExtra("To Do List Items", selectedItems);
                            startActivity(i);
                            */
                        }
                    });
                }
            }
        });



        // Set a Toolbar to replace the ActionBar.


        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);

        nvView = findViewById(R.id.nvView);
        nvView.setItemIconTintList(null);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        header = findViewById(R.id.header);

        Date d = new Date();
        CharSequence s  = DateFormat.format("yyyy-MM-dd ", d.getTime());
        CharSequence headerText = DateFormat.format("MMM dd, yyyy", d.getTime());
        currentDate = s.toString();

        header.setText("Events for: " + headerText);


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

    /*public void onListItemClick(ListView parent, View v,int position,long id){
        CheckedTextView item = (CheckedTextView) v;
        Toast.makeText(this, " checked : " +
                item.isChecked(), Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);
        return true;
    }*/

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllAnimals extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EventsActivity.this);
            pDialog.setMessage("Loading all locations. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_animals, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("Product: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // animals found
                    // Getting Array of Animals
                    dining = json.getJSONArray("events");

                    // looping through All Products
                    for (int i = 0; i < dining.length(); i++) {
                        JSONObject c = dining.getJSONObject(i);

                        // Storing each json item in variable
                        //String id = c.getString("Name");
                        String name = c.getString(TAG_NAME);
                        String date = c.getString("Date");


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //map.put("Animal_ID", id);

                        Log.d("Today's date: ", currentDate);
                        if (date.equals("2018-12-27") | date.equals("2018-12-03") | date.equals("2019-02-09")) {
                          //  map.put("Date",date);
                            map.put(TAG_NAME, name);
                            diningList.add(map);

                        }
                        // adding HashList to ArrayList
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            EventsActivity.this, diningList,
                            R.layout.list_animal, new String[] {TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name });
                    // updating listview
                    lv.setAdapter(adapter);
                }
            });

        }

    }
}


