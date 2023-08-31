package com.example.tfg_profes;

public class Alumno {
    private String idCliente;
    private int numHijos;
    public static Alumno alumno = new Alumno("",0,"");

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getNumHijos() {
        return numHijos;
    }

    public void setNumHijos(int numHijos) {
        this.numHijos = numHijos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    private String nombres;

    public Alumno(String idCliente, int numHijos, String nombres) {
        this.idCliente = idCliente;
        this.numHijos = numHijos;
        this.nombres = nombres;
    }
}
