package com.example.mk.rcwindy.Data;

/**
 * Created by MK on 11.01.2015.
 */
public class Spot {
    int spot_id;
    int user_id_fk;
    String spotName;
    String spotLongitude;
    String spotLatitude;
    Double windSpeed;
    String spotWeatherIcon; // numerek ikonki pogody

    public Spot(){

    }

    public Spot(int user_id_fk, String spotName, String spotLatitude, String spotLongitude, Double windSpeed, String spotWeatherIcon){
        this.user_id_fk = user_id_fk;
        this.spotName = spotName;
        this.spotLongitude = spotLongitude;
        this.spotLatitude = spotLatitude;
        this.windSpeed = windSpeed;
        this.spotWeatherIcon = spotWeatherIcon;
    }


    //GET SET
    public int getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(int spot_id) {
        this.spot_id = spot_id;
    }

    public int getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(int user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotLongitude() {
        return spotLongitude;
    }

    public void setSpotLongitude(String spotLongitude) {
        this.spotLongitude = spotLongitude;
    }

    public String getSpotLatitude() {
        return spotLatitude;
    }

    public void setSpotLatitude(String spotLatitude) {
        this.spotLatitude = spotLatitude;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getSpotWeatherIcon() {
        return spotWeatherIcon;
    }

    public void setSpotWeatherIcon(String spotWeatherIcon) {
        this.spotWeatherIcon = spotWeatherIcon;
    }
}
