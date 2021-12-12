package com.example.appantonio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appantonio.modelo.Usuario;
import com.orm.SugarContext;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button botonInicio;

    private Button botonRegistro;

    private EditText textUsuario;

    private EditText textContrasenia;

    private Usuario usuario;

    private List<Usuario> ListaUsuario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SugarContext.init(this);

        botonInicio = (Button) findViewById(R.id.botonIniciar);
        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        textUsuario = (EditText) findViewById(R.id.usuarioIniciar);
        textContrasenia = (EditText) findViewById(R.id.contraseniaIniciar);

        // Cuando se clicke el boton de inicio, se observara, si los campos estan vacios o si hay usuarios registrados
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comprobación de campos vacios:
                if (textUsuario.getText().toString().trim().isEmpty()||textContrasenia.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                }
                else{
                    // Comprobacion de usuarios registrados
                    ListaUsuario = Usuario.find(Usuario.class, "usuario = ? and contrasenia = ?", textUsuario.getText().toString(), textContrasenia.getText().toString());
                    //System.out.println(ListaUsuario);
                    // Si no hay usuarios registrados aparece el toast
                    if (ListaUsuario.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Este usuario no se encuentra registrado",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent i = new Intent(MainActivity.this, Lista_Champions.class);
                        startActivity(i);
                    }

                }

            }
        });

        //
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textUsuario.getText().toString().trim().isEmpty() || textContrasenia.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("¿Desea realizar el registro?")
                            .setTitle("Registro");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            usuario = new Usuario(textUsuario.getText().toString(), textContrasenia.getText().toString());
                            usuario.save();
                            Toast.makeText(MainActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "Se ha cancelado el registro", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Cuando se cierre la aplicacion, se cerrará la conexion con la base de datos
        SugarContext.terminate();
    }

}