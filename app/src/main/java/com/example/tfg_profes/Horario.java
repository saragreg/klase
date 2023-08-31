package com.example.tfg_profes;

import java.util.ArrayList;

public class Horario {
    private String idProfe;
    private String lun;
    private String mar;
    private String mie;
    private String jue;
    private String vie;
    private String sab;
    private String dom;
    private String descr;
    public static Horario horario = new Horario("","","","","","","","","");

    public static Horario getHorario() {
        return horario;
    }

    public static void setHorario(Horario horario) {
        Horario.horario = horario;
    }

    public static ArrayList<Horario> getLisHorariosProfes() {
        return lisHorariosProfes;
    }

    public static void setLisHorariosProfes(ArrayList<Horario> lisHorariosProfes) {
        Horario.lisHorariosProfes = lisHorariosProfes;
    }

    public static ArrayList<Horario> lisHorariosProfes=new ArrayList<>();
    public Horario(String idProfe, String lun, String mar, String mie, String jue, String vie, String sab, String dom, String descr) {
        this.idProfe = idProfe;
        this.lun = lun;
        this.mar = mar;
        this.mie = mie;
        this.jue = jue;
        this.vie = vie;
        this.sab = sab;
        this.dom = dom;
        this.descr = descr;
    }

    public String getIdProfe() {
        return idProfe;
    }

    public void setIdProfe(String idProfe) {
        this.idProfe = idProfe;
    }

    public String getLun() {
        return lun;
    }

    public void setLun(String lun) {
        this.lun = lun;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getMie() {
        return mie;
    }

    public void setMie(String mie) {
        this.mie = mie;
    }

    public String getJue() {
        return jue;
    }

    public void setJue(String jue) {
        this.jue = jue;
    }

    public String getVie() {
        return vie;
    }

    public void setVie(String vie) {
        this.vie = vie;
    }

    public String getSab() {
        return sab;
    }

    public void setSab(String sab) {
        this.sab = sab;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
