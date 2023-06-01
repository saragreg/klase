package com.example.tfg_profes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAgenda extends RecyclerView.Adapter<AgendaViewHolder>{
    private final ArrayList<String> diasMes;
    private final OnItemListener onItemListener;

    public AdaptadorAgenda(ArrayList<String> diasMes, OnItemListener onItemListener) {
        this.diasMes = diasMes;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.celda_calendario,parent,false);
        ViewGroup.LayoutParams layoutParams= view.getLayoutParams();
        layoutParams.height=(int) (parent.getHeight()*0.16666666);
        return new AgendaViewHolder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        holder.diaMes.setText(diasMes.get(position));
    }

    @Override
    public int getItemCount() {
        return diasMes.size();
    }

    public interface OnItemListener{
        void onItemClick(int position,String diaTxt);
    }
}
