package com.example.alexl.stlzoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Created by samikshasm on 3/28/18.
 */

public class RiversEdgeActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> animalsList;
    ArrayList<String> species_list;
    ArrayList<String> species_id;
    ArrayList<String> sc_name;
    ArrayList<String> list_names;
    ArrayList<String> end_status_list;
    ArrayList<String> region_list;
    ArrayList<String> diet_list;



    // url to get all products list
    private static String url_sec3_animals = "http://franzcars.mynetgear.com:443/sec1Animals.php";
    private static String url_animal_info = "http://franzcars.mynetgear.com:443/allSpecies.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANIMALS = "animals";
    private static final String TAG_ANIMAL_ID = "Animal_ID";
    private static final String TAG_NAME = "Common_name";

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvView;

    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;

    private ListView lv;


    // products JSONArray
    JSONArray animals = null;
    JSONArray animal_info = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_animals);
        final View view = findViewById(R.id.drawer_layout);
        view.setBackgroundColor(getResources().getColor(R.color.riversEdge));


        // Hashmap for ListView
        animalsList = new ArrayList<HashMap<String, String>>();
        species_list = new ArrayList<String>();
        species_id = new ArrayList<String>();
        sc_name = new ArrayList<String>();
        list_names = new ArrayList<String>();
        end_status_list = new ArrayList<String>();
        region_list = new ArrayList<String>();
        diet_list = new ArrayList<String>();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);




        // Loading products in Background Thread
        new RiversEdgeActivity.LoadAllAnimals().execute();

        // Get listview
        lv = (ListView) findViewById(R.id.animals_list);
        lv.setBackground(new ColorDrawable(getResources().getColor(R.color.riversEdge)));


        TextView header = findViewById(R.id.header);
        int height = 0; //your textview height
        header.getLayoutParams().height = height;

        Button toDoList = findViewById(R.id.toDoList);
        toDoList.getLayoutParams().height = height;

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String Animal_ID = ((TextView) view.findViewById(R.id.Name)).getText()
                        .toString();
                //Toast.makeText(DiscoveryCenterActivity.this, Animal_ID, Toast.LENGTH_SHORT).show();
                showAnimalInfo(Animal_ID,position);


                // Starting new intent
                //Intent in = new Intent(getApplicationContext(),
                //EditProductActivity.class);
                // sending pid to next activity
                //in.putExtra(TAG_ANIMAL_ID, Animal_ID);

                // starting new activity and expecting some response back
                //startActivityForResult(in, 100);
            }
        });

        // Set a Toolbar to replace the ActionBar.
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.riversEdgeDark)));

        // Find our drawer view
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

    public void showAnimalInfo(String Animal_ID, int position){
        Log.d("species_id", species_id+"");
        for(int i = 0; i < species_id.size(); i++){
            for(int j = 0; j < species_list.size(); j++){
                if (species_id.get(i).equals(species_list.get(j))){
                    list_names.add(sc_name.get(j));
                }
            }
        }
        String scientific_name = list_names.get(position);
        String region = region_list.get(position);
        String end_status = end_status_list.get(position);
        String diet = diet_list.get(position);
        Log.d("animal_info", scientific_name+","+region+","+end_status+","+diet);
        //Log.d("scientific_name", sc_name+"");

        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) RiversEdgeActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);


        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.pop_up_window,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.
                        The popup does not provide any background. This should be handled by
                        the content view.
                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        TextView animal_name_tv = (TextView) customView.findViewById(R.id.animal_name);
        animal_name_tv.setTextColor(getResources().getColor(R.color.riversEdgeDark));
        animal_name_tv.setText(Animal_ID);
        TextView species_name_tv = (TextView) customView.findViewById(R.id.species_name);
        species_name_tv.setText(scientific_name);
        species_name_tv.setTextColor(getResources().getColor(R.color.riversEdgeDark));
        TextView region_name_tv = (TextView) customView.findViewById(R.id.region_name);
        region_name_tv.setText(region);
        region_name_tv.setTextColor(getResources().getColor(R.color.riversEdgeDark));
        TextView diet_name_tv = (TextView) customView.findViewById(R.id.diet_name);
        diet_name_tv.setText(diet);
        diet_name_tv.setTextColor(getResources().getColor(R.color.riversEdgeDark));
        TextView status_name_tv = (TextView) customView.findViewById(R.id.status_name);
        status_name_tv.setText(end_status);
        status_name_tv.setTextColor(getResources().getColor(R.color.riversEdgeDark));
        RelativeLayout relLayout = (RelativeLayout) customView.findViewById(R.id.rl_custom_layout);
        relLayout.setBackgroundColor(getResources().getColor(R.color.white));

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.
                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);

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
    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.drawer, menu);
            return true;
        }
    */
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
            pDialog = new ProgressDialog(RiversEdgeActivity.this);
            pDialog.setMessage("Loading animals. Please wait...");
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
            JSONObject json = jParser.makeHttpRequest(url_sec3_animals, "GET", params);
            JSONObject animal_json = jParser.makeHttpRequest(url_animal_info, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("Product: ", animal_json.toString());


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // animals found
                    // Getting Array of Animals
                    animals = json.getJSONArray("animal");
                    animal_info = animal_json.getJSONArray("section");

                    for(int i = 0; i < animal_info.length(); i++){
                        JSONObject c = animal_info.getJSONObject(i);
                        String id = c.getString("Species_ID");
                        String sci_name = c.getString("Scientific_name");
                        String end_status = c.getString("Endangered_Status");
                        String region = c.getString("Region");
                        String diet = c.getString("Diet");
                        species_list.add(id);
                        sc_name.add(sci_name);
                        end_status_list.add(end_status);
                        region_list.add(region);
                        diet_list.add(diet);
                    }

                    // looping through All Products
                    for (int i = 0; i < animals.length(); i++) {
                        JSONObject c = animals.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("Animal_ID");
                        String species_id_str = c.getString("Species_ID");
                        species_id.add(species_id_str);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put("Animal_ID", id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        animalsList.add(map);
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
                            RiversEdgeActivity.this, animalsList,
                            R.layout.list_animal, new String[] { TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name }){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(R.id.Name);
                            textView.setTextColor(getResources().getColor(R.color.white));

                            return textView;

                        }
                    };
                    // updating listview
                    lv.setAdapter(adapter);
                }
            });

        }

    }
}