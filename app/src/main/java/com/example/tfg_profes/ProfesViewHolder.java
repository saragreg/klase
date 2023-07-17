package com.example.tfg_profes;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProfesViewHolder extends RecyclerView.ViewHolder{
    public TextView nombre,asignaturas,direccion,precio;
    public ImageView perfilprofe;
    private AdaptadorProfesLista.OnItemClickListener listener;

    public RatingBar val;
    public ProfesViewHolder(@NonNull View itemView) {
        super(itemView);
        perfilprofe = (ImageView) itemView.findViewById(R.id.imageView2);
        nombre= (TextView) itemView.findViewById(R.id.nombreusupro);
        val= (RatingBar) itemView.findViewById(R.id.ratingBar4);
        val.setIsIndicator(true);
        asignaturas=(TextView) itemView.findViewById(R.id.asignaturas);
        direccion = (TextView) itemView.findViewById(R.id.direccion);
        precio=(TextView) itemView.findViewById(R.id.preciousulis);
    }
}
