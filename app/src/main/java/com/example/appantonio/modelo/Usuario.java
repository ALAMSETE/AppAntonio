package com.example.appantonio.modelo;
import com.orm.SugarRecord;


// Esta es la clase usuario que hereda de la clase SugarRecord
public class Usuario extends SugarRecord{
    private String usuario;
    private String contrasenia;

    // Creamos el constructor por defecto vacio
    public Usuario(){

    }

    public Usuario(String usuario, String contrasenia){
        this.usuario= usuario;
        this.contrasenia=contrasenia;
    }

    // Creamos getter y setter
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
