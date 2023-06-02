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

public class AdaptadorAgenda extends RecyclerView.Adapter<AgendaViewHolder>{
    private final ArrayList<LocalDate> dias;
    private final OnItemListener onItemListener;

    public AdaptadorAgenda(ArrayList<LocalDate> dias, OnItemListener onItemListener) {
        this.dias = dias;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.celda_calendario,parent,false);
        ViewGroup.LayoutParams layoutParams= view.getLayoutParams();
        if (dias.size()>15) {
            layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        }else{
            layoutParams.height = (int) parent.getHeight();
        }
        return new AgendaViewHolder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        final LocalDate date=dias.get(position);
        if (date==null){
            holder.diaMes.setText("");
        }else {
            holder.diaMes.setText(String.valueOf(date.getDayOfMonth()));
            if (date.equals(AgendaUtils.selectedDate)){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dias.size();
    }

    public interface OnItemListener{
        void onItemClick(int position,String diaTxt);
    }
}
