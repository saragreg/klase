package com.example.tfg_profes;

import java.util.ArrayList;
import java.util.Iterator;

public class Imagenes {
    public static ArrayList<Imagenes> lisimagenes=new ArrayList<>();
    public static ArrayList<Imagenes> lisContactos=new ArrayList<>();

    public static ArrayList<Imagenes> getLisContactos() {
        return lisContactos;
    }

    public static void setLisContactos(ArrayList<Imagenes> lisContactos) {
        Imagenes.lisContactos = lisContactos;
    }


    public static Imagenes perfilusuario = new Imagenes("","");

    public static ArrayList<Imagenes> lisimagenesProfes=new ArrayList<>();

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
    public static ArrayList<Imagenes> getLisimagenesProfes() {
        return lisimagenesProfes;
    }

    public static void setLisimagenesProfes(ArrayList<Imagenes> lisimagenesProfes) {
        Imagenes.lisimagenesProfes = lisimagenesProfes;
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
    public static String obtenerImagen2(String user){
        String res="";
        Iterator<Imagenes> iterator = lisimagenesProfes.iterator();
        boolean salir=false;
        while (iterator.hasNext()&&!salir) {
            Imagenes element = iterator.next();
            if (element.getUser().equals(user)){
                res=element.getImagen();
                salir=true;
            }
        }
        return res;
    }
    public static boolean esContacto(String usuario){
        Iterator<Imagenes> iterator = lisContactos.iterator();
        boolean salir=false;
        while (iterator.hasNext()&&!salir) {
            Imagenes element = iterator.next();
            if (element.getUser().equals(usuario)){
                salir=true;
            }
        }
        return salir;
    }
}
