package com.example.tfg_profes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView diaMes;
    private final AdaptadorAgenda.OnItemListener onItemListener;
    public AgendaViewHolder(@NonNull View itemView, AdaptadorAgenda.OnItemListener onItemListener) {
        super(itemView);
        diaMes=itemView.findViewById(R.id.diaCeldaTxt);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(),(String) diaMes.getText());
    }
}
