package com.example.smb116_projet.ui.accueil;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smb116_projet.MainActivity;
import com.example.smb116_projet.R;
import com.example.smb116_projet.databinding.FragmentAccueilBinding;
import com.example.smb116_projet.model.Api;
import com.example.smb116_projet.model.GPSTracker;
import com.example.smb116_projet.model.Meteo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AccueilFragment extends Fragment {

    private FragmentAccueilBinding binding;
    private String apiKey = "c09dce9e59ce0039c78a0219a28ab1d7";
    private String url = "https://api.openweathermap.org/data/2.5/weather";
    private String urlImage = "http://openweathermap.org/img/wn";
    private String langue = "fr";
    private String unite = "metric";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccueilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textDateAjd = binding.textDateAjd;
        final ImageView imageTemps = binding.imageTemps;
        final TextView textTemps = binding.textTemps;
        final TextView textTemperature = binding.textTemperature;

        double latitude = 0;
        double longitude = 0;

        GPSTracker gps = new GPSTracker(getContext());

        getContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (!(getContext().checkSelfPermission
                (android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED))
        {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }
    //check if GPS enabled
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
    //can't get location
    //GPS or Network is not enabled
    //Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        Log.i("lat: ", String.valueOf(latitude));
        Log.i("lon: ", String.valueOf(longitude));

        String urlApi = this.url + "?appid=" + this.apiKey + "&lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&lang=" + this.langue + "&units=" + this.unite;

        RequestQueue requestQueue = Volley.newRequestQueue(inflater.getContext());
        // Start the queue
        requestQueue.start();
        Log.i("LOG_API", "start ok");

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        Meteo meteo = null;
                        try {
                            meteo = new Meteo(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Calendar calendar = Calendar.getInstance();
                        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                        binding.textDateAjd.setText(currentDate);
                        binding.textTemps.setText(meteo.getWeather_description());
                        binding.textTemperature.setText(String.valueOf(meteo.getTemp()) + " °C");

                        binding.imageTemps.setImageResource(meteo.getImageMeteo());

                      //  String urlIcon = urlImage + "/" + meteo.getWeather_icon() + "@2x.png";
                      //  Log.i("LOG_API", urlIcon);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.i("LOG_API", "nope");

                    }
                });
        Log.i("LOG_API", "test recup = " + stringRequest.getBodyContentType());
        try {
            Log.i("LOG_API", "test recup2 = " + stringRequest.getBody());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}