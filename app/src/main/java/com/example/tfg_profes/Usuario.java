package com.example.tfg_profes;

import java.util.ArrayList;

public class Usuario {
    public static ArrayList<Usuario> usuariosLis=new ArrayList<>();

    public Usuario(String user,String nombre,String tel, String loc,String imagen) {
        this.user = user;
        this.nombre = nombre;
        this.tel=tel;
        this.loc=loc;
        this.imagen=imagen;
    }

    private String user,nombre,tel,loc,imagen;

    public static ArrayList<Usuario> getUsuariosLis() {
        return usuariosLis;
    }

    public static void setUsuariosLis(ArrayList<Usuario> usuariosLis) {
        Usuario.usuariosLis = usuariosLis;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void guardarEvento() {
        //llamar a la base de datos y guardarlo
    }
    /*public static ArrayList<Evento> eventosdeldia(LocalDate date){
        ArrayList<Evento> eventos=new ArrayList<>();
        for (Evento evento : eventosLis){
            if (evento.getDate().equals(date))
                eventos.add(evento);
        }
        return eventos;
    }*/
}
