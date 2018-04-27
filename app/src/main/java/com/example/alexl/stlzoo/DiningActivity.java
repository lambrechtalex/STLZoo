package com.example.alexl.stlzoo;

/**
 * Created by samikshasm on 3/28/18.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.alexl.stlzoo.R;

public class DiningActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;
    private ArrayList<Integer> section_IDs = new ArrayList<Integer>();
    private ArrayList<Integer> section_IDs2 = new ArrayList<Integer>();
    private ArrayList<Integer> section_IDs3 = new ArrayList<Integer>();
    private ArrayList<Integer> section_IDs5 = new ArrayList<Integer>();
    private ArrayList<Integer> section_IDs6 = new ArrayList<Integer>();





    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> diningList;
    ArrayList<HashMap<String, String>> diningList2;
    ArrayList<HashMap<String, String>> diningList3;
    ArrayList<HashMap<String, String>> diningList5;
    ArrayList<HashMap<String, String>> diningList6;

    // url to get all products list
    private static String url_all_animals = "http://franzcars.mynetgear.com:443/allDining.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANIMALS = "animals";
    private static final String TAG_ANIMAL_ID = "Animal_ID";
    private static final String TAG_NAME = "Name";
    private static ListView lv1;
    private static ListView lv2;
    private static ListView lv3;
    private static ListView lv5;
    private static ListView lv6;


    // products JSONArray
    JSONArray dining = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

        // Hashmap for ListView
        diningList = new ArrayList<HashMap<String, String>>();
        diningList2 = new ArrayList<HashMap<String, String>>();
        diningList3 = new ArrayList<HashMap<String, String>>();
        diningList5 = new ArrayList<HashMap<String, String>>();
        diningList6 = new ArrayList<HashMap<String, String>>();


        lv1 = (ListView) findViewById(R.id.list_1);
        lv1.setBackground(new ColorDrawable(getResources().getColor(R.color.riversEdge)));

        lv2 = (ListView) findViewById(R.id.list_2);
        lv2.setBackground(new ColorDrawable(getResources().getColor(R.color.theWild)));
        lv3 = (ListView) findViewById(R.id.list_3);
        lv3.setBackground(new ColorDrawable(getResources().getColor(R.color.discoveryCenter)));

        lv5 = (ListView) findViewById(R.id.list_5);
        lv5.setBackground(new ColorDrawable(getResources().getColor(R.color.lakesideCrossing)));

        lv6 = (ListView) findViewById(R.id.list_6);
        lv6.setBackground(new ColorDrawable(getResources().getColor(R.color.redRocks)));

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);



        // Loading products in Background Thread
        new LoadAllAnimals().execute();

        // Get listview
        //ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        /*lv1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String Animal_ID = ((TextView) view.findViewById(R.id.Animal_ID)).getText()
                        .toString();

                // Starting new intent
                //Intent in = new Intent(getApplicationContext(),
                //EditProductActivity.class);
                // sending pid to next activity
                //in.putExtra(TAG_ANIMAL_ID, Animal_ID);

                // starting new activity and expecting some response back
                //startActivityForResult(in, 100);
            }
        });*/
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
                if(menuItem.getItemId() == R.id.toDoList) {
                    Intent todoAct = new Intent(getApplicationContext(), ToDoList.class);
                    startActivity(todoAct);
                }
                if(menuItem.getItemId() == R.id.donate) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.stlzoo.org/give"));
                    startActivity(browserIntent);
                }
                if(menuItem.getItemId() == R.id.adopt) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.stlzoo.org/give/zooparentsprogram/animaladoptionlist/"));
                    startActivity(browserIntent);
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
            pDialog = new ProgressDialog(DiningActivity.this);
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
                    dining = json.getJSONArray("dining");

                    // looping through All Products
                    for (int i = 0; i < dining.length(); i++) {
                        JSONObject c = dining.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("dining_id");
                        String section = c.getString("section_id");
                        //Log.d("dining_list", id);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        HashMap<String, String> map2 = new HashMap<String, String>();
                        HashMap<String, String> map3 = new HashMap<String, String>();
                        HashMap<String, String> map5 = new HashMap<String, String>();
                        HashMap<String, String> map6 = new HashMap<String, String>();


                        // adding each child node to HashMap key => value
                        if(Integer.parseInt(section) == 1){
                            map.put("dining_id", id);
                            map.put(TAG_NAME, name);
                            map.put("section_id", section);
                            section_IDs.add(Integer.parseInt(section));
                            // adding HashList to ArrayList
                            diningList.add(map);
                        }
                        if(Integer.parseInt(section) == 2){
                            map2.put("dining_id", id);
                            map2.put(TAG_NAME, name);
                            Log.d("name", name);
                            map2.put("section_id", section);
                            section_IDs2.add(Integer.parseInt(section));
                            // adding HashList to ArrayList
                            diningList2.add(map2);
                        }
                        if(Integer.parseInt(section) == 3){
                            map3.put("dining_id", id);
                            map3.put(TAG_NAME, name);
                            map3.put("section_id", section);
                            section_IDs3.add(Integer.parseInt(section));
                            // adding HashList to ArrayList
                            diningList3.add(map3);
                        }
                        if(Integer.parseInt(section) == 5){
                            map5.put("dining_id", id);
                            map5.put(TAG_NAME, name);
                            map5.put("section_id", section);
                            section_IDs5.add(Integer.parseInt(section));
                            // adding HashList to ArrayList
                            diningList5.add(map5);
                        }
                        if(Integer.parseInt(section) == 6){
                            map6.put("dining_id", id);
                            map6.put(TAG_NAME, name);
                            map6.put("section_id", section);
                            section_IDs6.add(Integer.parseInt(section));
                            // adding HashList to ArrayList
                            diningList6.add(map6);
                        }


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
                            DiningActivity.this, diningList,
                            R.layout.list_animal, new String[] {TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name }){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(R.id.Name);
                            Log.d("testing", position+"");
                            int section_ids = section_IDs.get(position);
                            Log.d("section_ids", section_ids+"");
                            textView.setTextColor(getResources().getColor(R.color.white));

                            return textView;

                        }
                    };

                    ListAdapter adapter2 = new SimpleAdapter(
                            DiningActivity.this, diningList2,
                            R.layout.list_animal, new String[] {TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name }){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(R.id.Name);
                            Log.d("testing", position+"");
                            int section_ids = section_IDs2.get(position);
                            Log.d("section_ids", section_ids+"");
                            textView.setTextColor(getResources().getColor(R.color.white));
                            return textView;

                        }
                    };
                    ListAdapter adapter3 = new SimpleAdapter(
                            DiningActivity.this, diningList3,
                            R.layout.list_animal, new String[] {TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name }){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(R.id.Name);
                            Log.d("testing", position+"");
                            int section_ids = section_IDs3.get(position);
                            Log.d("section_ids", section_ids+"");
                            textView.setTextColor(getResources().getColor(R.color.white));
                            return textView;

                        }

                    };
                    ListAdapter adapter5 = new SimpleAdapter(
                            DiningActivity.this, diningList5,
                            R.layout.list_animal, new String[] {TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name }){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(R.id.Name);
                            Log.d("testing", position+"");
                            int section_ids = section_IDs5.get(position);
                            Log.d("section_ids", section_ids+"");
                            textView.setTextColor(getResources().getColor(R.color.white));
                            return textView;

                        }
                    };
                    ListAdapter adapter6 = new SimpleAdapter(
                            DiningActivity.this, diningList6,
                            R.layout.list_animal, new String[] {TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name }){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(R.id.Name);
                            Log.d("testing", position+"");
                            int section_ids = section_IDs6.get(position);
                            Log.d("section_ids", section_ids+"");
                            textView.setTextColor(getResources().getColor(R.color.white));
                            return textView;

                        }
                    };
                    // updating listview
                    lv1.setAdapter(adapter);
                    setDynamicHeight(lv1);
                    lv2.setAdapter(adapter2);
                    setDynamicHeight(lv2);
                    lv3.setAdapter(adapter3);
                    setDynamicHeight(lv3);
                    lv5.setAdapter(adapter5);
                    setDynamicHeight(lv5);
                    lv6.setAdapter(adapter6);
                    setDynamicHeight(lv6);
                }
            });

        }

    }

    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }
}
