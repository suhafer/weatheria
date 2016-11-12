package com.betalogika.weatheria;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Forecast {
    public Weather weather;
    public String dt_txt;
    int dt;
    Main main;

    public Forecast(Weather weather, Main main, int dt, String dt_txt){
        this.weather    = weather;
        this.main       = main;
        this.dt         = dt;
        this.dt_txt     = dt_txt;
    }

    private String getCelcius(double temp){
        NumberFormat numberFormat = DecimalFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(temp - 273.15)+" C";
    }

    public String getTempMax(){
        return getCelcius(main.temp_max);
    }

    public String getTempMin(){
        return getCelcius(main.temp_min);
    }

    public String getPressure(){
        return Double.toString(main.pressure) + " hpa";
    }

}

class Main {
    double temp;
    double temp_min;
    double temp_max;
    double pressure;
    double humidity;

    public Main(double temp, double pressure, double humidity, double temp_min, double temp_max){
        this.temp       = temp;
        this.pressure   = pressure;
        this.humidity   = humidity;
        this.temp_min   = temp_min;
        this.temp_max   = temp_max;
    }
}
