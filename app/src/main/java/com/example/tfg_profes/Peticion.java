package com.example.tfg_profes;

import java.util.ArrayList;

public class Peticion {
    public static ArrayList<Peticion> peticionesLis=new ArrayList<>();
    private String idProfe;
    private String idUsu;
    private String fotoper;
    private String nombre;
    private String asig;
    private String duracion;
    private String fechahora;
    private String fechacreacion;
    private String intensivo;
    private String dias;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Peticion(String idProfe, String idUsu, String fotoper, String nombre, String asig, String duracion, String fechahora, String fechacreacion, String intensivo, String dias, String estado) {
        this.idProfe = idProfe;
        this.idUsu = idUsu;
        this.fotoper = fotoper;
        this.nombre = nombre;
        this.asig = asig;
        this.duracion = duracion;
        this.fechahora = fechahora;
        this.fechacreacion = fechacreacion;
        this.intensivo = intensivo;
        this.dias = dias;
        this.estado = estado;
    }




    public static ArrayList<Peticion> getPeticionesLis() {
        return peticionesLis;
    }

    public static void setPeticionesLis(ArrayList<Peticion> peticionesLis) {
        Peticion.peticionesLis = peticionesLis;
    }

    public String getIdProfe() {
        return idProfe;
    }

    public void setIdProfe(String idProfe) {
        this.idProfe = idProfe;
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getFotoper() {
        return fotoper;
    }

    public void setFotoper(String fotoper) {
        this.fotoper = fotoper;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getIntensivo() {
        return intensivo;
    }

    public void setIntensivo(String intensivo) {
        this.intensivo = intensivo;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }


}
