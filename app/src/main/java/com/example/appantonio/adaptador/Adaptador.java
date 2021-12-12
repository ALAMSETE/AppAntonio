package com.example.appantonio.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.appantonio.Champion;
import com.example.appantonio.R;


import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.RecyclerHolder> implements View.OnClickListener {
    // Creamos el arraylist para almacenar a los campeones de la API en el
    ArrayList<Champion> listaCampeones;
    // Creamos el sensor
    private View.OnClickListener sensor;
    private Activity activity;

    public Adaptador(ArrayList<Champion> listaCampeones, Activity activity){
        this.listaCampeones = listaCampeones;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if(sensor!=null){
            sensor.onClick(view);
        }
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adaptador, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        view.setOnClickListener(this);
        return recyclerHolder;
    }

    @Override
    public int getItemCount() {
        return listaCampeones.size();
    }

    public void setOnClickListener(View.OnClickListener sensor){
        this.sensor=sensor;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Champion campeones = listaCampeones.get(position);
        holder.nombreChamp.setText(campeones.getNombre());

        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(holder.itemView.getContext());
        progressDrawable.setStrokeWidth(15f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(35f);
        progressDrawable.start();

        Glide.with(activity)
                .load(campeones.getAvatar())
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher)
                .into(holder.avatarChamp);
    }

    public class RecyclerHolder extends ViewHolder{
        ImageView avatarChamp;
        TextView nombreChamp;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            avatarChamp = (ImageView)itemView.findViewById(R.id.avatarChamp);
            nombreChamp = (TextView) itemView.findViewById(R.id.nombreChamp);


        }
    }
}
