package com.example.tfg_profes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {
    public static ArrayList<Evento> eventosLis=new ArrayList<>();

    public Evento(String desc, LocalDate date) {
        this.desc = desc;
        this.date = date;
    }

    private String desc;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void guardarEvento() {
        //llamar a la base de datos y guardarlo
    }
    public static ArrayList<Evento> eventosdeldia(LocalDate date){
        ArrayList<Evento> eventos=new ArrayList<>();
        for (Evento evento : eventosLis){
            if (evento.getDate().equals(date))
                eventos.add(evento);
        }
        return eventos;
    }
}
