package com.example.tfg_profes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdaptadorEventos extends ArrayAdapter<Evento> {
    public AdaptadorEventos(@NonNull Context context, List<Evento> events) {
        super(context, 0,events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Evento evento=getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.celda_evento,parent,false);
            TextView eventoCelda=convertView.findViewById(R.id.eventoCeldaTV);
            eventoCelda.setText(evento.getDesc());
        }
        return convertView;
    }
}
