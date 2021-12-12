package com.example.appantonio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Informacion extends AppCompatActivity {

    private ImageView foto;

    private TextView textNombre;

    private TextView textTitulo;

    private TextView textLore;

    public static int RQ_INFO_ACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        // Enlazamos los objetos a los del XML
        foto = (ImageView) findViewById(R.id.imagen);
        textNombre = (TextView) findViewById(R.id.nombre);
        textTitulo = (TextView) findViewById(R.id.titulo);
        textLore = (TextView) findViewById(R.id.lore);

        Intent i = getIntent();
        // Cogemos los parametros que hemos pasado desde el recyclerView
        String img = i.getStringExtra("foto");
        String name = i.getStringExtra("nombre");
        String title = i.getStringExtra("titulo");
        String lor = i.getStringExtra("lore");
        // Creamos el icono que aparecera mientras carga la imagen
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(this);
        progressDrawable.setStrokeWidth(15f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(35f);
        progressDrawable.start();
        // Usamos el glide para cargar la imagen y para mostrar el icono mientras carga la imagen
        Glide.with(getApplicationContext())
                .load(img)
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher)
                .into(foto);
        // Asignamos los parametros a sus respectivos objetos
        textNombre.setText(name);
        textTitulo.setText(title);
        textLore.setText(lor);

    }

}