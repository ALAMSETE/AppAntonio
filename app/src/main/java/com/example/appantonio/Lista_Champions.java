package com.example.appantonio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appantonio.adaptador.*;
import com.example.appantonio.io.*;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Lista_Champions extends AppCompatActivity {

    private RecyclerView lista;
    private ArrayList<Champion> listaCampeones = new ArrayList<Champion>();

    private Adaptador adaptador;


    public boolean onCreateOptionsMenu(Menu menu) {
        //Se usa un inflater para construir la vista y se pasa el menu por defecto para
        // que Android se encargue de colocarlo en la vista
        getMenuInflater().inflate(R.menu.menu_simple,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
            Intent i = new Intent(Lista_Champions.this, Preferencias.class);
            startActivity(i);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_champions);

        lista = (RecyclerView) findViewById(R.id.recyView);

        adaptador = new Adaptador(listaCampeones, this);

        loadPreferences();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        // Añadimos los elementos al recyclerView
        lista.setAdapter(adaptador);
        lista.setLayoutManager(gridLayoutManager);

        //El segundo campo se encuentra vacio ya que lo que queremos es sacar todos los campeones de la api
        // si se desea ver la informacion de uno en particular, se debe rellenar con una "/" y despues el nombre
        // de dicho campeon. (ej: "/Aatrox")
        new taskConexiones().execute("GET", "");

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Lista_Champions.this, Informacion.class);
                i.putExtra("foto",listaCampeones.get(lista.getChildAdapterPosition(v)).getFoto());
                i.putExtra("nombre",listaCampeones.get(lista.getChildAdapterPosition(v)).getNombre());
                i.putExtra("titulo",listaCampeones.get(lista.getChildAdapterPosition(v)).getTitulo());
                i.putExtra("lore",listaCampeones.get(lista.getChildAdapterPosition(v)).getLore());
                startActivity(i);
            }
        });

    }

    private class taskConexiones extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String resultado = null;
            switch (strings[0]){
                case "GET":
                    resultado = HttpLoLConnect.getUrlChampInfo(strings[1]);
                    break;
                case "POST":
                    resultado = Integer.toString(HttpLoLConnect.postUrlChampInfo(strings[1],strings[2]));
                    break;
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String s){
            try {
                if (s!=null){
                    Log.d("D", "DATOS: "+s);
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject objeto2 = jsonObject.getJSONObject("data");
                    // Nuestra api esta compuesta por un objeto con objetos dentro, por lo que necesitamos
                    // un iterador para obtener todos los campeones del objeto.
                    Iterator iterador = objeto2.keys();
                    //Creamos un JSONArray para almacenar todos los campeones que tiene el objeto "data"
                    JSONArray arrayCampeones = new JSONArray();

                    // Recorremos el iterador y guardamos los objetos en un JSONArray
                    while(iterador.hasNext()){
                        String llave = (String) iterador.next();
                        //Log.d("key", llave);
                        arrayCampeones.put(objeto2.get(llave));
                    }
                    // Recorremos el JSONArray donde se encuentran todos los campeones para crear objetos
                    // por cada campeon en la lista
                    for(int i=0; i<arrayCampeones.length();i++){
                        String nombre = arrayCampeones.getJSONObject(i).getString("name");
                        String titulo = arrayCampeones.getJSONObject(i).getString("title");
                        String lore = arrayCampeones.getJSONObject(i).getString("blurb");
                        String id = arrayCampeones.getJSONObject(i).getString("id");
                        Champion campeon = new Champion(nombre, titulo, lore, id);
                        //Metemos los objetos en el array creado anteriormente "listaCampeones"
                        listaCampeones.add(campeon);
                        //System.out.println(campeon);
                    }
                    //Cuando se obtienen todos los campeones, debemos avisar al adaptador para informar
                    // de que debe actualizarse
                    adaptador.notifyDataSetChanged();
                    Log.d("DEB", String.valueOf(arrayCampeones));
                }
            }
            catch (JSONException e){

            }
        }

    }

    public void loadPreferences(){

        //Todo 4. Una vez creado todo, solo debemos de preocuparnos de acceder a la información,
        // ya  que Android se encarga del almacenamiento de los datos que introduce el usuario en
        // la ventana de preferencias.

        //Todo 4.1 Utilizamos PreferenceManager para obtener las preferencias compartidas de nuestra
        // aplicación. TENEIS QUE TENER EN CUENTA QUE ESTE ES EL MISMO PARA TODA LA APP (PATRÓN SINGLETON)


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Todo 4.2 Una vez tenemos acceso a las preferencias compartidas, solo debemos acceder mediante la clave para obtener su valor
        String nombreUsuario = sharedPreferences.getString("nombre_usuario","Usuario");
        Toast.makeText(this, "¡Hola "+nombreUsuario+"!", Toast.LENGTH_SHORT).show();
    }

}