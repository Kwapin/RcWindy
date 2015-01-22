package com.example.mk.rcwindy;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mk.rcwindy.Adapter.PlaceHolder;
import com.example.mk.rcwindy.Data.DatabaseHandler;
import com.example.mk.rcwindy.Data.GPSTracker;
import com.example.mk.rcwindy.Data.Spot;
import com.example.mk.rcwindy.Data.WeatherApi;

import java.io.IOException;
import java.util.List;


public class SpotAddActivity extends ActionBarActivity implements LocationListener,OnClickListener{



    EditText SpotName;
    AutoCompleteTextView autoCompleteTextView;
    int loggedUserId;
    double windSpeed;
    double longi;
    double lati;
    String ikonkaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_add);




        SpotName = (EditText) findViewById(R.id.SpotName);
        Intent intent = getIntent();


        if (null != intent) {
            loggedUserId = intent.getIntExtra("logged", 0);
        }

        String text = "logged user id = "+loggedUserId;
        Toast t1 = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        t1.show();

        //Autouzupełnianie Adresu


        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.SearchSpot);
        autoCompleteTextView.setOnClickListener(this);
        autoCompleteTextView.requestFocus();
        autoCompleteTextView.setAdapter(new PlaceHolder(SpotAddActivity.this,
        R.layout.list_item_autocomplete));

        //Zamiast Wykonywać w tle polaczenie do internetu usuwam ograniczenia.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }




//Pobieranie Informacji z Autocomplete list żeby przesłać dalej i pobrać Współrzędne GPS
    public void showResult(){                       //Usuniete (View view) -mozna usunąć guzik powiazany z show Result
        String s = this.autoCompleteTextView.getEditableText().toString();
        getLocationFromAddress(s);
    }



//Pobieranie współrzędnych GPS miejsca wybranego w Autocomplete!
    public void getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;


        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address != null) {
                Address location = address.get(0);
                lati = location.getLatitude();
                longi = location.getLongitude();
              //  Toast t2 = Toast.makeText(getApplicationContext(), "Lattitude: "+lati , Toast.LENGTH_SHORT);
              //   t2.show();
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Pobietanie Nazwy miejsca na podstawie lokalizacji
    public void getLocationName(View view) {



        //Getting GPS coordinates Latitude Longitude
        GPSTracker gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        Log.d("latitude: " + latitude, "longitude:" + longitude);
        try {
            Geocoder gCoder = new Geocoder(this);
            List<Address> addresses = gCoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {


                //TODO: Dodać aby odnaleziona lokalizacja była wpisywana w pole autocomplete text view

                autoCompleteTextView.setText(addresses.get(0).getLocality()+" "+addresses.get(0).getThoroughfare());
                lati = latitude;
                longi = longitude;


               // Toast do wyswietlenia lokalizacji
               // String info = "Witaj w : " + addresses.get(0).getThoroughfare();
               // Context context = getApplicationContext();
               // int duration = Toast.LENGTH_LONG;
               // Toast toast = Toast.makeText(context, info, duration);
               //toast.show();
            }
        }
            catch(IOException e) {
                e.printStackTrace();
            }

    }


    public void pobierzPogode(){

        String latitude = Double.toString(lati);
        String longitude = Double.toString(longi);

        WeatherApi pobieranie =  new WeatherApi();

        pobieranie.pobierzDane(latitude,longitude);

        windSpeed = pobieranie.podajPrędkość();
        ikonkaId = pobieranie.podajIdentyfikatorIkonki();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spot_add, menu);
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

        if (id== R.id.Dodaj_Spot){

            showResult();
            pobierzPogode();
            String nazwaSpotu = SpotName.getText().toString();

            if(longi != 0 & lati != 0){

                String wysokosc,szerokosc;
                wysokosc = Double.toString(lati);
                szerokosc = Double.toString(longi);

                DatabaseHandler db = new DatabaseHandler(this);
                db.addSpot(new Spot(loggedUserId,nazwaSpotu,wysokosc,szerokosc,windSpeed,ikonkaId));


                Intent spoty = new Intent(this, SpotActivity.class);
                spoty.putExtra("logged",loggedUserId);
                startActivity(spoty);

            }



                    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {

    }
}
