package com.example.appantonio;

public class Champion {
    private String nombre, titulo, lore, id, foto="http://ddragon.leagueoflegends.com/cdn/img/champion/splash/", avatar="http://ddragon.leagueoflegends.com/cdn/11.24.1/img/champion/";

    public Champion(String nombre, String titulo, String lore, String id){
        this.nombre = nombre;
        this.titulo = titulo;
        this.lore = lore;
        this.id = id;
        this.foto = this.foto+id+"_0.jpg";
        this.avatar = this.avatar+id+".png";
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
