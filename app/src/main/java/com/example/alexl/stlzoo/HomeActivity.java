package com.example.alexl.stlzoo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    static final Integer LOCATION = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
}


    public void ask(View v){
        switch (v.getId()){
            case R.id.location:
                askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
                break;
            case R.id.call:
                askForPermission(Manifest.permission.CALL_PHONE,CALL);
                break;
            case R.id.write:
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
                break;
            case R.id.read:
                askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
                break;
            case R.id.camera:
                askForPermission(Manifest.permission.CAMERA,CAMERA);
                break;
            case R.id.accounts:
                askForPermission(Manifest.permission.GET_ACCOUNTS,ACCOUNTS);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
