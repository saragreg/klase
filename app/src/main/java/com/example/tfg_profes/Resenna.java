package com.example.tfg_profes;

import java.util.ArrayList;

public class Resenna {
    public static ArrayList<Resenna> resennasLis=new ArrayList<>();
    private String idProfe;
    private String idUsu;
    private Float valoracion;
    private String comentario;
    private String fechaHora;

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Resenna(String idProfe, String idUsu, Float valoracion, String comentario, String fecha) {
        this.idProfe = idProfe;
        this.idUsu = idUsu;
        this.valoracion = valoracion;
        this.comentario = comentario;
        this.fechaHora = fecha;
    }

    public static ArrayList<Resenna> getResennasLis() {
        return resennasLis;
    }

    public static void setResennasLis(ArrayList<Resenna> resennasLis) {
        Resenna.resennasLis = resennasLis;
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

    public Float getValoracion() {
        return valoracion;
    }

    public void setValoracion(Float valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
