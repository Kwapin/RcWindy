package com.example.mk.rcwindy.Data;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MK on 21.01.2015.
 */
public class WeatherApi {



    double windspeed;
    String ikonkaId;

    public void pobierzDane(String latitude, String longitude)


    {
        String url="http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);


        UrlConnectionHandler sh = new UrlConnectionHandler();
        nameValuePairs.add(new BasicNameValuePair("APPID", "4c89ea24accc0d7c440ca3a92dbadc29"));
        String jsonStr = sh.makeServiceCall(url, UrlConnectionHandler.GET,nameValuePairs);

        if (jsonStr != null) {
            try {

            //Pobranie prędkosci wiatru
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONObject wind = jsonObj.getJSONObject("wind");
                windspeed = wind.getDouble("speed");
            //Pobranie Numeru ikony
                JSONObject  sys = new JSONObject(jsonStr);
                JSONArray weather = sys.getJSONArray("weather");
                JSONObject icon = weather.getJSONObject(0);
                ikonkaId = icon.getString("icon");

              Log.e( "LWindSpeed"+windspeed+" ikonka to: "+ikonkaId , "");



            }
         catch (JSONException e) {
        e.printStackTrace();
    }
    } else {
        Log.e("Błąd", "Couldn't get any data from the url");
    }

}

    public Double podajPrędkość(){
        return windspeed;
    }


    public String podajIdentyfikatorIkonki(){
        return ikonkaId;
    }


}

