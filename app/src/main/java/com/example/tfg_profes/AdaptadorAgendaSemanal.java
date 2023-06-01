package com.example.tfg_profes;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_profes.utils.AgendaUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class AdaptadorAgendaSemanal extends RecyclerView.Adapter<AgendaSemanalViewHolder>{
    private final ArrayList<LocalDate> diasSem;
    private final OnItemListener onItemListener;

    public AdaptadorAgendaSemanal(ArrayList<LocalDate> diasSem, OnItemListener onItemListener) {
        this.diasSem = diasSem;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AgendaSemanalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.celda_calendario,parent,false);
        ViewGroup.LayoutParams layoutParams= view.getLayoutParams();
        layoutParams.height=(int) parent.getHeight();
        return new AgendaSemanalViewHolder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaSemanalViewHolder holder, int position) {
        if (diasSem.get(position)==null){
            holder.diaMes.setText("");
        }else {
            holder.diaMes.setText(diasSem.get(position).getDayOfMonth());
            if (diasSem.get(position).equals(AgendaUtils.selectedDate)){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return diasSem.size();
    }

    public interface OnItemListener{
        void onItemClick(int position,String diaTxt);
    }
}

