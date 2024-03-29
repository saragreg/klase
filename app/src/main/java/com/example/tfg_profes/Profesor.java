package com.example.tfg_profes;

import java.util.ArrayList;

public class Profesor {
    public static ArrayList<Profesor> lisProfes=new ArrayList<>();
    public static Profesor profesor = new Profesor("","","",0.0F,"","","","","","","");
    private String idProfe;
    private String nombre;
    private String asig;
    private Float val;
    private String direccion;
    private String lat;

    public static void updateval(String profe, float rating) {
        for (Profesor profesor : lisProfes){
            if (profesor.idProfe.equals(profe))
                profesor.setVal(rating);
        }
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getCursos() {
        return cursos;
    }

    public void setCursos(String cursos) {
        this.cursos = cursos;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    private String experiencia;
    private String cursos;

    public Profesor(String idProfe, String nombre, String asig, Float val, String direccion, String lat, String lng, String precio, String experiencia, String idiomas, String cursos) {
        this.idProfe = idProfe;
        this.nombre = nombre;
        this.asig = asig;
        this.val = val;
        this.direccion = direccion;
        this.lat = lat;
        this.experiencia = experiencia;
        this.cursos = cursos;
        this.idiomas = idiomas;
        this.lng = lng;
        this.precio = precio;
    }

    private String idiomas;

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
