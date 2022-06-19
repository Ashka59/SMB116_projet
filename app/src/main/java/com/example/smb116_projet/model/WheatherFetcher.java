package com.example.smb116_projet.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WheatherFetcher extends AsyncTask<ArrayList<Double>, ArrayList<Double>, Void> {
    public ArrayList<Etape> listeEtapes = new ArrayList<>();
    ArrayList<Long> durations;

    HashMap<String, Bitmap> imagesMap = new HashMap<>();

    ArrayList<Feature> ICthunder = new ArrayList<>();
    ArrayList<Feature> ICsprinkle = new ArrayList<>();
    ArrayList<Feature> ICrain = new ArrayList<>();
    ArrayList<Feature> ICsnow = new ArrayList<>();
    ArrayList<Feature> ICsmoke = new ArrayList<>();
    ArrayList<Feature> ICdayhaze = new ArrayList<>();
    ArrayList<Feature> ICdust = new ArrayList<>();
    ArrayList<Feature> ICfog = new ArrayList<>();
    ArrayList<Feature> ICclougusts = new ArrayList<>();
    ArrayList<Feature> ICtornado = new ArrayList<>();
    ArrayList<Feature> ICdaysunny = new ArrayList<>();
    ArrayList<Feature> ICcloudy = new ArrayList<>();
    ArrayList<Feature> ICcloudy801 = new ArrayList<>();
    ArrayList<Feature> ICcloudy802 = new ArrayList<>();

    ArrayList<Feature> ICrainynight = new ArrayList<>();
    ArrayList<Feature> ICclearmoon = new ArrayList<>();
    ArrayList<Feature> ICcloudynight = new ArrayList<>();

    String datas = "";

    @SuppressLint("StaticFieldLeak")
    Activity activity_a;

    public WheatherFetcher(Activity _activity, ArrayList<Long> durationsL) {
        this.activity_a = _activity;
        this.durations = durationsL;
        System.out.println(durationsL + "aray ");

    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(ArrayList<Double>... params) {
        ArrayList<Double> longi = params[0];
        ArrayList<Double> lati = params[1];
        ArrayList<Double> distances = params[2];

        for (int i = 0; i < longi.size() - 1; i++) {
            try {
                double latti = lati.get(i);
                double longg = longi.get(i);
                URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?" +
                        "lat=" + latti + "&lon=" + longg +
                        "&%20exclude={part}&appid=df7988b5831e56f5c8d05c9b2fc04df3");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while (line != null) {
                    line = bufferedReader.readLine();
                    datas = datas + line;
                }

                JSONObject allData = new JSONObject(datas);
                JSONArray weather = allData.getJSONArray("hourly");
                JSONObject sunrisesunset = allData.getJSONObject("current");

                long sunrise = (long) sunrisesunset.getDouble("sunrise");
                long sunset = (long) sunrisesunset.getDouble("sunset");
                System.out.println(sunrise + "Isun" + sunset);
                int sunriseHH = fromUnixTimestamp(sunrise);
                System.out.println(sunriseHH + "RISE");
                int sunsetHH = fromUnixTimestamp(sunset);
                System.out.println(sunsetHH + "SET");

                double ddouble = distances.get(i);
                int iint = (int) ddouble;
                String distance = iint + "";

                double tempKelvin = -700.0;
                int conditionId = 781;
                long LatLongtimeList;
                String timeString = "";
                String nameme = "";
                String ConditionString = "";

                for (int j = 0; j < weather.length(); j++) {
                    JSONObject hourlyobjects = weather.getJSONObject(j);
                    long LongSeconds = (long) hourlyobjects.getDouble("dt");
                    LatLongtimeList = durations.get(i);

                    System.out.println(LatLongtimeList + "LatLongtime  " + i);

                    //Time String
                    timeString = fromUnixTimestampString(LatLongtimeList);
                    int currentHH = fromUnixTimestamp(LatLongtimeList);

                    //Distance String
                    Double distanceintdouble = distances.get(i);

                    System.out.println(distanceintdouble + "   distances");

                    if ((LatLongtimeList - LongSeconds) < 0) {
                        tempKelvin = (int) (hourlyobjects.getDouble("temp")) - 273;

                        JSONArray weatherid = hourlyobjects.getJSONArray("weather");
                        JSONObject weatherCondition = weatherid.getJSONObject(0);
                        conditionId = weatherCondition.getInt("id");

                        nameme = "WeatherObject" + i;

                        System.out.println("CurrentHH" + currentHH + "sunrise" + sunriseHH + "sunset" + sunsetHH);

                        if (currentHH <= sunriseHH && currentHH >= sunsetHH) {
                            ConditionString = conditionId + "0";
                            System.out.println("day tan");
                            ArrayfillerDay(conditionId, latti, longg);

                        } else {
                            ConditionString = conditionId + "1";
                            System.out.println("night tan");

                            ArrayfillerNight(conditionId, latti, longg);

                        }

                        System.out.println("Worked fine" + latti + longg + "kelvin " + (tempKelvin - 273.15) + "  " + conditionId);
                        break;

                    }

                }
                listeEtapes.add(new Etape(nameme, tempKelvin, conditionId, latti, longg, timeString, distance, ConditionString, getCitynamefromLatLang(latti, longg)));

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < listeEtapes.size(); i++) {
            System.out.println(listeEtapes.get(i).GetWeatherCode() + "" +
                    listeEtapes.get(i).GetTemperature() + "      " + i);

        }

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println(imagesMap.size());

    }

    public static int fromUnixTimestamp(double itemDouble) {

        long itemLong = (long) itemDouble * 1000;
        Date itemDate = new Date(itemLong);

        @SuppressLint("SimpleDateFormat") String itemDateStr = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
                .format(itemDate)
                .substring(11, 13);
        int finall = Integer.parseInt(itemDateStr);

        System.out.println(itemDateStr + "here time");

        return finall;
    }

    public static String fromUnixTimestampString(double itemDouble) {

        long itemLong = (long) itemDouble * 1000;
        Date itemDate = new Date(itemLong);

        @SuppressLint("SimpleDateFormat") String itemDateStr = new SimpleDateFormat("dd/MM/yyyy hh:mm aa")
                .format(itemDate)
                .substring(11);

        System.out.println(itemDateStr + "here time");

        return itemDateStr;
    }

    public void ArrayfillerDay(int conditionid, double latitude, double longitude) {

        switch (conditionid) {
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                ICthunder.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                ICsprinkle.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
            case 701:
                ICrain.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                ICsnow.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 711:
                ICsmoke.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 721:
                ICdayhaze.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 731:
            case 761:
            case 762:
                ICdust.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 741:
                ICfog.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 771:
                ICclougusts.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 800:
                ICdaysunny.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 781:
                ICtornado.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;

            case 801:
                ICcloudy801.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;
            case 802:
                ICcloudy802.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;

            case 803:
            case 804:
                ICcloudy.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;

            default:
                System.out.println("ok");
        }

    }

    private void ArrayfillerNight(int conditionid, double latitude, double longitude) {

        switch (conditionid) {
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
            case 701:
                ICrainynight.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                ICsnow.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 711:
                ICsmoke.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 721:
                ICdayhaze.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 731:
            case 761:
            case 762:
                ICdust.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 741:
                ICfog.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 771:
                ICclougusts.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;

            case 781:
                ICtornado.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;

            case 800:
                ICclearmoon.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

                break;
            case 801:
            case 802:
            case 803:
            case 804:
                ICcloudynight.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;

            default:
                System.out.println("bjgya");
        }
    }

    public String getCitynamefromLatLang(double latitude, double longitude) {
        StringBuilder allfData = new StringBuilder();
        String finalCityName = "";
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/find?lat=" +
                    latitude + "&lon=" + longitude +
                    "&cnt=1&appid=df7988b5831e56f5c8d05c9b2fc04df3");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while (line != null) {
                line = bufferedReader.readLine();
                allfData.append(line);
            }

            JSONObject allData = new JSONObject(allfData.toString());
            JSONArray list = allData.getJSONArray("list");
            JSONObject listObject = list.getJSONObject(0);
            finalCityName = listObject.getString("name");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return finalCityName;
    }
}