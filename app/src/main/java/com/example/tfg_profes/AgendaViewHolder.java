package com.example.tfg_profes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class AgendaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final View parentView;
    public final TextView diaMes;
    public final TextView bolaNaranja;
    private final ArrayList<LocalDate> dias;
    private final AdaptadorAgenda.OnItemListener onItemListener;
    public AgendaViewHolder(@NonNull View itemView, ArrayList<LocalDate> dias, AdaptadorAgenda.OnItemListener onItemListener) {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        this.bolaNaranja = itemView.findViewById(R.id.bolaEvento);
        this.dias = dias;
        diaMes=itemView.findViewById(R.id.diaCeldaTxt);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(),dias.get(getAdapterPosition()));
    }
}
