package com.example.smb116_projet.model;

import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smb116_projet.MainActivity;
import com.google.gson.Gson;

public class Api extends AppCompatActivity {

    private String apiKey;
    private String url;
    private String langue;
    private String unite;
    private MainActivity mainActivity;
    private Gson gson;
    private String data = "";


    public Api() {
        this.apiKey = "c09dce9e59ce0039c78a0219a28ab1d7";
        this.url = "https://api.openweathermap.org/data/2.5/weather";
        this.langue = "fr";
        this.unite = "metric";
        this.gson = new Gson();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void getMeteoAjd(double lat, double lon, LayoutInflater inflater) {

        String urlApi = this.url + "?appid=" + this.apiKey + "&lat=" + lat + "&lon=" + lon + "&lang=" + this.langue + "&units=" + this.unite;
        Log.i("LOG_API", "recup start");
        // Instantiate the cache
       // Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
       // Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.


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
                        ajoutData(response);
                        Log.i("LOG_API", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        ajoutData("");
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
        Log.i("LOG_API", "return data=" + stringRequest);
    }

    public void ajoutData(String data) {
        this.data = data;
    }
}
