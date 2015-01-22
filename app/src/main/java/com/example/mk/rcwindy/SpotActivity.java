package com.example.mk.rcwindy;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mk.rcwindy.Data.DatabaseHandler;
import com.example.mk.rcwindy.Data.Spot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SpotActivity extends ActionBarActivity {


    int loggedUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

       DatabaseHandler db = new DatabaseHandler(this);
       List<Spot> spots = db.getUserSpotList(loggedUserId);
        for (Spot s : spots){
            String log = "hej"+s.getSpotName()+s.getWindSpeed();
            Log.d("Name:",log);

        }

      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item_autocomplete,ArrayofSpots);





        Intent intent = getIntent();

        if (null != intent) {
            loggedUserId = intent.getIntExtra("logged", 0);
        }

        String text = "logged user id = "+loggedUserId;
        Toast t1 = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        t1.show();




    }

    public int userIdSender(){
        return loggedUserId;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spot, menu);
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
        if (id== R.id.action_new){
            Intent intent = new Intent(this, SpotAddActivity.class);
            intent.putExtra("logged",loggedUserId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
