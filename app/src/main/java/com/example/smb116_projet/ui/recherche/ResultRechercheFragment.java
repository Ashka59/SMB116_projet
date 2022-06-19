package com.example.smb116_projet.ui.recherche;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smb116_projet.MainActivity;
import com.example.smb116_projet.R;
import com.example.smb116_projet.databinding.FragmentResultRechercheBinding;
import com.example.smb116_projet.model.Etape;
import com.example.smb116_projet.model.JSFetcher;
import com.example.smb116_projet.model.Jsfetcher2;
import com.example.smb116_projet.model.ProgressDialog;
import com.example.smb116_projet.model.RecyclerViewAdapter;
import com.example.smb116_projet.model.WheatherFetcher;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultRechercheFragment extends Fragment {

    private FragmentResultRechercheBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        String pointDepart = getArguments().getString("pointDepart");
        String pointArrive = getArguments().getString("pointArrive");

        binding = FragmentResultRechercheBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.textView5.setText("Point de depart: " + pointDepart);
        binding.textView6.setText("Point d'arrivé: " + pointArrive);

        ArrayList<Etape> weatherData = null;

        //Geolocalisation
        Jsfetcher2 startO = new Jsfetcher2(pointDepart);
        Jsfetcher2 destO = new Jsfetcher2(pointArrive);
        try {
            startO.execute().get();
            destO.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Point startP = Point.fromLngLat(startO.longi, startO.lat);
        Point destP = Point.fromLngLat(destO.longi, destO.lat);
        Point DummydestP = Point.fromLngLat(destO.longi, destO.lat);

        NavigationRoute.builder(getContext())
                .accessToken(getString(R.string.mapbox_access_token))
                .origin(startP)
                .addWaypoint(DummydestP)
                .destination(destP)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DirectionsResponse> call, @NotNull Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        String Jsonraw = response.body().toJson();
                        //get weather
                        //final ProgressDialog loadingdialog = new ProgressDialog(getActivity());
                        final Handler handler = new Handler();
                        //loadingdialog.startLoading();
                        JSFetcher jsFetcher = new JSFetcher();
                        //handler.postDelayed(loadingdialog::dismissLoading, 10000);

                        try {
                            jsFetcher.execute(Jsonraw).get();
                            WheatherFetcher weatherFetcherss = new WheatherFetcher(getActivity(), jsFetcher.flaggedDuration);
                            weatherFetcherss.execute(jsFetcher.longs, jsFetcher.lats, jsFetcher.flaggedDistance).get();
                            WheatherFetcher weatherFetchers = weatherFetcherss;
                            ArrayList<Etape> weatherData = weatherFetchers.listeEtapes;
                            openWdetails(weatherData);

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            Log.i("info","passe pas dans la boucle");
                        }
                    }

                    private void openWdetails(ArrayList<Etape> weatherData) {

                        ArrayList<Etape> filelist = weatherData;

                        RecyclerView recyclerView = binding.recyclerview;

                        RecyclerViewAdapter wdetailsAdapter = new RecyclerViewAdapter(getContext(), filelist);
                        recyclerView.setAdapter(wdetailsAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onFailure(@NotNull Call<DirectionsResponse> call, @NotNull Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}