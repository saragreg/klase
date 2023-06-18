package com.example.tfg_profes;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResennaViewHolder extends RecyclerView.ViewHolder{
    public TextView nombre,comentario,fecha;
    public RatingBar val;
    public ResennaViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre= (TextView) itemView.findViewById(R.id.nombreusures);
        val= (RatingBar) itemView.findViewById(R.id.ratingBar3);
        val.setIsIndicator(true);
        comentario=(TextView) itemView.findViewById(R.id.comentario);
        fecha = (TextView) itemView.findViewById(R.id.fechaResenna);
    }
}
