package com.example.smb116_projet.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smb116_projet.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Etape> etapeArrayList;

    public RecyclerViewAdapter(Context ct, ArrayList<Etape> etapeArrayList) {
        context = ct;
        this.etapeArrayList = etapeArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_card_meteo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Celsius.setText(etapeArrayList.get(position).GetTemperature());
        holder.Distance.setText(etapeArrayList.get(position).GetDistance());
        holder.Time.setText(etapeArrayList.get(position).GetTime());
        holder.Wicon.setImageResource(etapeArrayList.get(position).GetImageID());
        holder.CityName.setText(etapeArrayList.get(position).GetCityName());

    }

    @Override
    public int getItemCount() {

        return etapeArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Celsius, Distance, Time, CityName;

        ImageView Wicon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Celsius = itemView.findViewById(R.id.Celciusview);
            Distance = itemView.findViewById(R.id.Distance);
            Time = itemView.findViewById(R.id.timeview);
            Wicon = itemView.findViewById(R.id.Wicon);
            CityName = itemView.findViewById(R.id.textView4);

        }
    }
}