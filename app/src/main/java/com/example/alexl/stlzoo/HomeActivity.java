package com.example.alexl.stlzoo;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeActivity extends AppCompatActivity {

    Button btnViewAnimals;

    static final Integer LOCATION = 0x1;
    GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;
    static final Integer GPS_SETTINGS = 0x7;
    private String homeActivity;
    LocationManager locationManager;
    static final int REQUEST_LOCATION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        client = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
        .addApi(LocationServices.API)
        .build();
        /*
        btnViewAnimals = (Button) findViewById(R.id.btnViewAnimals);
        btnViewAnimals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(), AllAnimalsActivity.class);
                startActivity(i);
            }
        });*/


        /*
        Button goToMaps = (Button) findViewById(R.id.location);
        goToMaps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                homeActivity = "home";
                Intent switchToMaps = new Intent(HomeActivity.this, MapsActivity.class);
                switchToMaps.putExtra("coming from home", "home");
                switchToMaps.putExtra("Home Activity", homeActivity);
                startActivity(switchToMaps);
                finish();
            }
        });*/
}

    void getLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                Log.e("Location", "lat: "+latti+" long: "+longi);
            }else{
                Log.e("Loaction","location is null");

            }
        }
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            homeActivity = "home";
            Intent switchToMaps = new Intent(HomeActivity.this, MapsActivity.class);
            switchToMaps.putExtra("coming from home", "home");
            switchToMaps.putExtra("Home Activity", homeActivity);
            startActivity(switchToMaps);
            finish();
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        ask();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

    }

    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    private void askForGPS(){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(HomeActivity.this, GPS_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }


    public void ask(){
        /*switch (v.getId()){
            case R.id.location:*/
                askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
                /*break;
            default:
                break;
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {
                //Location
                case 1:
                    askForGPS();
                    break;
                //Call
                case 2:
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "{This is a telephone number}"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    }
                    break;
                //Write external Storage
                case 3:
                    break;
                //Read External Storage
                case 4:
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, 11);
                    break;
                //Camera
                case 5:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 12);
                    }
                    break;
                //Accounts
                case 6:
                    AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
                    Account[] list = manager.getAccounts();
                    Toast.makeText(this,""+list[0].name,Toast.LENGTH_SHORT).show();
                    for(int i=0; i<list.length;i++){
                        Log.e("Account "+i,""+list[i].name);
                    }
            }

            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            homeActivity = "home";
            Intent switchToMaps = new Intent(HomeActivity.this, MapsActivity.class);
            switchToMaps.putExtra("coming from home", "home");
            switchToMaps.putExtra("Home Activity", homeActivity);
            startActivity(switchToMaps);
            finish();
        }else{
            //Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

}
