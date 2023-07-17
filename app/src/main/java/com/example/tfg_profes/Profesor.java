package com.example.tfg_profes;

import java.util.ArrayList;

public class Profesor {
    public static ArrayList<Profesor> lisProfes=new ArrayList<>();
    private String idProfe;
    private String nombre;
    private String asig;
    private Float val;
    private String direccion;
    private String lat;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    private String lng;

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    private String precio;

    public Profesor(String idProfe, String nombre, String asig, Float val, String direccion, String precio, String lat, String lng) {
        this.idProfe = idProfe;
        this.nombre = nombre;
        this.asig = asig;
        this.val = val;
        this.direccion = direccion;
        this.precio = precio;
        this.lat = lat;
        this.lng = lng;
    }

    public static ArrayList<Profesor> getLisProfes() {
        return lisProfes;
    }

    public static void setLisProfes(ArrayList<Profesor> lisProfes) {
        Profesor.lisProfes = lisProfes;
    }

    public String getIdProfe() {
        return idProfe;
    }

    public void setIdProfe(String idProfe) {
        this.idProfe = idProfe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsig() {
        return asig;
    }

    public void setAsig(String asig) {
        this.asig = asig;
    }

    public Float getVal() {
        return val;
    }

    public void setVal(Float val) {
        this.val = val;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
