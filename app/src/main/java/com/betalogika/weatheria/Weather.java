package com.betalogika.weatheria;

public class Weather {
    public String id;
    public String main;
    public String description;
    public String icon;

    public Weather(String id, String main, String description, String icon){
        this.id             = id;
        this.main           = main;
        this.description    = description;
        this.icon           = icon;
    }
}
