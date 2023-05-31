package com.example.tfg_profes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.recyclerview.widget.RecyclerView;

public class ElViewHolder extends RecyclerView.ViewHolder {
    public TextView nombre,dur,fechaHoraPet,intensivo,diasPet;
    public ImageView userfoto;
    public Flow flowLayout;
    public ImageView aceptar,rechazar,info;
    public String estado="p";

    public ElViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre= (TextView) itemView.findViewById(R.id.username);
        userfoto=(ImageView) itemView.findViewById(R.id.fotoPerfil);
        dur = (TextView) itemView.findViewById(R.id.duracion);
        fechaHoraPet = (TextView) itemView.findViewById(R.id.fechaHoraPet);
        intensivo = (TextView) itemView.findViewById(R.id.intensivo);
        diasPet = (TextView) itemView.findViewById(R.id.diasPet);
        flowLayout = (Flow) itemView.findViewById(R.id.flowlayout);
        aceptar=(ImageView) itemView.findViewById(R.id.aceptar_btn);
        rechazar=(ImageView) itemView.findViewById(R.id.rechazar_btn);
        info=(ImageView) itemView.findViewById(R.id.info_btn);

    }

}
