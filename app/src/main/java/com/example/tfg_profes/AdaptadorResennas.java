package com.example.tfg_profes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorResennas extends RecyclerView.Adapter<ResennaViewHolder> {
    private ArrayList<Resenna> lista;
    private View elLayoutDeCadaItem;
    public AdaptadorResennas(ArrayList<Resenna> resennasLis) {
        lista=resennasLis;
    }

    public ResennaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        elLayoutDeCadaItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_resennas,null);
        ResennaViewHolder rvh = new ResennaViewHolder(elLayoutDeCadaItem);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ResennaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Resenna r=lista.get(position);
        holder.nombre.setText(r.getIdUsu());
        holder.val.setRating(r.getValoracion());
        holder.fecha.setText(r.getFechaHora());
        holder.comentario.setText(r.getComentario());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
