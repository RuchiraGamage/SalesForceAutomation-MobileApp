package com.example.salinda.salseforseautomation.mapActivity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationModel implements Serializable {
    @SerializedName("Name")
    private String Name;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Latitude")
    private double Latitude;

    public LocationModel(){

    }

    public LocationModel(double Longitude, double Latitude, String Name){
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public double getLatitude() {
        return Latitude;
    }
    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {return Longitude;}
    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}
