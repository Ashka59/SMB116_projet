package com.example.smb116_projet.model;

import android.util.Log;

import com.example.smb116_projet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Meteo extends Api {

    private float lat;
    private float lon;

    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private float pressure;
    private float sea_level;
    private float humidity;

    private int weather_id;
    private String weather_id_string;
    private String weather_main;
    private String weather_description;
    private String weather_icon;

    private float wind_speed;
    private float wind_deg;

    private long sunrise;
    private long sunset;

    public Meteo(JSONObject json) {
        try {
            this.lat = (float) json.getJSONObject("coord").getDouble("lat");
            this.lon = (float) json.getJSONObject("coord").getDouble("lon");

            this.temp = (float) json.getJSONObject("main").getDouble("temp");
            this.feels_like = (float) json.getJSONObject("main").getDouble("feels_like");
            this.temp_min = (float) json.getJSONObject("main").getDouble("temp_min");
            this.temp_max = (float) json.getJSONObject("main").getDouble("temp_max");
            this.pressure = (float) json.getJSONObject("main").getDouble("pressure");
            this.humidity = (float) json.getJSONObject("main").getDouble("humidity");

            this.sunrise = json.getJSONObject("sys").getLong("sunrise");
            this.sunset = json.getJSONObject("sys").getLong("sunset");

            this.weather_id = json.getJSONArray("weather").getJSONObject(0).getInt("id");
            this.weather_id_string = String.valueOf(this.weather_id);

            int currentHH = (int) (System.currentTimeMillis() / 1000L);

            int sunriseHH = WheatherFetcher.fromUnixTimestamp(this.sunrise);
            System.out.println(sunriseHH + "RISE");
            int sunsetHH = WheatherFetcher.fromUnixTimestamp(this.sunset);
            System.out.println(sunsetHH + "SET");

            if (currentHH <= sunriseHH && currentHH >= sunsetHH) {
                this.weather_id_string = this.weather_id_string + "0";
                System.out.println("day tan");
                Log.i("day tan", this.weather_id_string);
            } else {
                this.weather_id_string = this.weather_id_string + "1";
                System.out.println("night tan");
                Log.i("night tan", this.weather_id_string);
            }

            this.weather_main = json.getJSONArray("weather").getJSONObject(0).getString("main");
            this.weather_description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            this.weather_icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");

            this.wind_speed = (float) json.getJSONObject("wind").getDouble("speed");
            this.wind_deg = (float) json.getJSONObject("wind").getDouble("deg");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR: ", e.getMessage() );
        }
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getSea_level() {
        return sea_level;
    }

    public void setSea_level(float sea_level) {
        this.sea_level = sea_level;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public float getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(float wind_deg) {
        this.wind_deg = wind_deg;
    }

    public int getImageMeteo() {

        switch (Integer.parseInt(weather_id_string)) {
            case 2000:
            case 2010:
            case 2020:
            case 2100:
            case 2110:
            case 2120:
            case 2210:
            case 2300:
            case 2310:
            case 2320:
                return R.drawable.ic_005_thunderstorm;
            case 2001:
            case 2011:
            case 2021:
            case 2101:
            case 2111:
            case 2121:
            case 2211:
            case 2301:
            case 2311:
            case 2321:
            case 3001:
            case 3011:
            case 3021:
            case 3101:
            case 3111:
            case 3121:
            case 3131:
            case 3141:
            case 3211:
            case 5001:
            case 5011:
            case 5021:
            case 5031:
            case 5041:
            case 5111:
            case 5201:
            case 5211:
            case 5221:
            case 5311:
            case 7011:
                return R.drawable.ic_011_night_rain;
            case 3000:
            case 3010:
            case 3020:
            case 3100:
            case 3110:
            case 3120:
            case 3130:
            case 3140:
            case 3210:
                return R.drawable.ic_060_rain;
            case 5000:
            case 5010:
            case 5020:
            case 5030:
            case 5040:
            case 5110:
            case 5200:
            case 5210:
            case 5220:
            case 5310:
            case 7010:
                return R.drawable.ic_002_rain;
            case 6000:
            case 6010:
            case 6020:
            case 6110:
            case 6120:
            case 6130:
            case 6150:
            case 6160:
            case 6200:
            case 6210:
            case 6220:
            case 6001:
            case 6011:
            case 6021:
            case 6111:
            case 6121:
            case 6131:
            case 6151:
            case 6161:
            case 6201:
            case 6211:
            case 6221:
                return R.drawable.ic_033_snow;

            case 7210:
            case 7211:
                return R.drawable.ic_044_dawn;

            case 7410:
            case 7411:

                return R.drawable.ic_027_nimbostratus;

            case 8000:
                return R.drawable.ic_sun_1212;
            case 8001:
                return R.drawable.ic_034_moon;
            case 7810:
            case 7811:
                return R.drawable.ic_036_tornado;

            case 8010:
                return R.drawable.ic_011_cloud;
            case 8020:
                return R.drawable.ic_013_cloudy;

            case 8030:
            case 8040:
                return R.drawable.ic_014_cloud;
            case 8011:
            case 8021:
            case 8031:
            case 8041:
                return R.drawable.ic_009_cloud;
            case 7110:
            case 7111:
            case 7310:
            case 7610:
            case 7620:
            case 7311:
            case 7611:
            case 7621:

            case 7710:
            case 7711:

            default:
                return R.drawable.ic_035_cyclone;
        }
    }
}
