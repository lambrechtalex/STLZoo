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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.alexl.stlzoo.R;

public class AllAnimalsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> animalsList;

    // url to get all products list
    private static String url_all_animals = "http://franzcars.mynetgear.com:443/sec6Animals.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANIMALS = "animals";
    private static final String TAG_ANIMAL_ID = "Animal_ID";
    private static final String TAG_NAME = "Name";

    // products JSONArray
    JSONArray animals = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_animals);

        // Hashmap for ListView
        animalsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllAnimals().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

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
        });

    }

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
            pDialog = new ProgressDialog(AllAnimalsActivity.this);
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
            JSONObject json = jParser.makeHttpRequest(url_all_animals, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("Product: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // animals found
                    // Getting Array of Animals
                    animals = json.getJSONArray("animal");

                    // looping through All Products
                    for (int i = 0; i < animals.length(); i++) {
                        JSONObject c = animals.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("Animal_ID");
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
                            AllAnimalsActivity.this, animalsList,
                            R.layout.list_animal, new String[] { TAG_ANIMAL_ID,
                            TAG_NAME},
                            new int[] { R.id.Animal_ID, R.id.Name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}