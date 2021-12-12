package com.example.appantonio.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpLoLConnect {

    private static String urlChampInfo = "http://ddragon.leagueoflegends.com/cdn/11.23.1/data/en_US/champion";

    // El siguiente metodo será el encargado de realizar la consulta a la API
    public static String getUrlChampInfo(String champ){
        HttpURLConnection http = null;
        String contenido = null;
        try{
            // Terminamos de crear el enlace, ya que la URL base necesita que se le pase algun valor al final para saber a donde apuntar.
            URL url = new URL(urlChampInfo + champ + ".json");
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            // En el siguiente metodo controlaremos que la conexion a la API se haya realizado correctamente.
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                // Si se ha realizado correctamente hará lo siguiente:
                StringBuilder sb = new StringBuilder();
                // Creamos el lector de la información
                BufferedReader lector = new BufferedReader( new InputStreamReader(http.getInputStream()));
                //Creamos un string llamado linea para saber cuanto hay que leer
                String line;
                // Hacemos que recorra el JSON hasta que no haya líneas por recorrer
                while((line = lector.readLine())!= null){
                    // Añadimos la linea leida al constructor de string
                    sb.append(line);
                }
                // Añadimos a la variable contenido el contenido todo lo guardado en el constructor de string
                contenido = sb.toString();
                // Cerramos el lector
                lector.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Desconectamos la conexion a la API
            if (http!=null){
                http.disconnect();
            }
        }
        return contenido;
    }

    // Creamos el metodo para escribir los datos
    public static int postUrlChampInfo(String champ, String datos){
        HttpURLConnection http = null;
        int respuestaCodigo = -1;
        try{
            // Creamos la URL con el valor pasado por parametro (champ)
            URL url = new URL(champ);
            // Abrimos la conexion con la API
            http = (HttpURLConnection)url.openConnection();
            // Establecemos el modo de conexion, en este caso, POST
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type","application/json");
            http.setRequestProperty("Accept","application/json");
            http.setDoOutput(true);

            // Creamos el escritor, para mostrar los datos por pantalla
            PrintWriter escritor = new PrintWriter(http.getOutputStream());
            // Escribo los datos leidos en el layout
            escritor.print(datos);
            escritor.flush();
            // Comprobamos si los datos han sido leidos correctamente
            respuestaCodigo = http.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (http != null){
                http.disconnect();
            }
        }
        return respuestaCodigo;
    }

}
