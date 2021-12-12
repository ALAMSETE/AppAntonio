package com.example.appantonio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appantonio.fragment.Fragmento;

public class Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmento);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container, new Fragmento())   //Es en esta línea donde se reemplaza el contenedor por una instancia de la clase Fragmento
                .commit();                                                  // cuya clase construye las preferencias


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true); //   Si existe (no es nulo) mostramos el botón hacia atrás.
        }


    }

    //Para que tenga efecto al pulsar debemos implementar qué debe hacer cuando el usuario
    // lo pulse.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();                    //  Este sería el acceso al recurso del botón volver
                return true;        //  Este sería el método que tiene Android para volver hacia la ventana anterior.
        }

        return super.onOptionsItemSelected(item);
    }

}
