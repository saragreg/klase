package com.example.tfg_profes;

import java.util.ArrayList;
import java.util.Iterator;

public class Imagenes {
    public static ArrayList<Imagenes> lisimagenes=new ArrayList<>();

    public Imagenes(String user,String imagen) {
        this.user = user;
        this.imagen=imagen;
    }

    private String user,imagen;

    public static ArrayList<Imagenes> getimagenesLis() {
        return lisimagenes;
    }

    public static void setimagenesLis(ArrayList<Imagenes> lisimagenes) {
        Imagenes.lisimagenes = lisimagenes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public static String obtenerImagen(String user){
        String res="";
        Iterator<Imagenes> iterator = lisimagenes.iterator();
        boolean salir=false;
        while (iterator.hasNext()||!salir) {
            Imagenes element = iterator.next();
            if (element.getUser().equals(user)){
                res=element.getImagen();
                salir=true;
            }
        }
        return res;
    }
}
