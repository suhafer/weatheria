package com.betalogika.weatheria;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForecastWeatherAPIClient {
    private static final String URL     = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "6f744904778fd118471810734f822336";
    HomeActivityCallback homeActivityCallback;

    public ForecastWeatherAPIClient(final String city_name, final HomeActivityCallback homeActivityCallback) {
        this.homeActivityCallback   = homeActivityCallback;
        AsyncHttpClient client      = new AsyncHttpClient();
        RequestParams params        = new RequestParams();
        params.put("q", city_name);
        params.put("APPID", API_KEY);

        client.get(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject responseBody) {
                try {
                    JSONObject city_data    = responseBody.getJSONObject("city");
                    JSONObject coord        = city_data.getJSONObject("coord");
                    City city               = new City(
                            coord.getDouble("lon"), coord.getDouble("lat"),
                            city_data.getString("id"), city_data.getString("name"));

                    ArrayList<Forecast> listOfForecast  = new ArrayList<>();
                    JSONArray forecast_data             = responseBody.getJSONArray("list");
                    for (int i=0; i < forecast_data.length(); i++) {
                        try {
                            JSONObject data         = forecast_data.getJSONObject(i);
                            JSONObject main         = data.getJSONObject("main");

                            JSONArray weather_data  = data.getJSONArray("weather");
                            JSONObject weather      = weather_data.getJSONObject(0);

                            Main main1              = new Main(
                                    main.getDouble("temp"), main.getDouble("pressure"),
                                    main.getDouble("humidity"), main.getDouble("temp_min"), main.getDouble("temp_max"));

                            Weather weather1        = new Weather(
                                    weather.getString("id"), weather.getString("main"),
                                    weather.getString("description"), weather.getString("icon"));

                            Forecast forecast       = new Forecast(weather1, main1, data.getInt("dt"), data.getString("dt_txt"));
                            listOfForecast.add(forecast);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    homeActivityCallback.updateResource(listOfForecast, city);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
