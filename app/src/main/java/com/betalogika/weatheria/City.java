package com.betalogika.weatheria;

public class City {
    public String id;
    public String name;
    public String lon;
    public String lat;

    public City(double lon, double lat, String id, String name){
        this.lon    = Double.toString(lon) + " ]";
        this.lat    = "[ " + Double.toString(lat);
        this.id     = id;
        this.name   = name;
    }
}