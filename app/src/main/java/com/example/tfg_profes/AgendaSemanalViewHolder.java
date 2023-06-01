package com.example.tfg_profes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaSemanalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView diaMes;
    private final AdaptadorAgendaSemanal.OnItemListener onItemListener;
    public final View parentView;
    public AgendaSemanalViewHolder(@NonNull View itemView, AdaptadorAgendaSemanal.OnItemListener onItemListener) {
        super(itemView);
        diaMes=itemView.findViewById(R.id.diaCeldaTxt);
        this.onItemListener = onItemListener;
        this.parentView = itemView.findViewById(R.id.parentView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(),(String) diaMes.getText());
    }
}