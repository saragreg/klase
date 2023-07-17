package com.example.tfg_profes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorProfesLista extends RecyclerView.Adapter<ProfesViewHolder> {

    private Context contexto;
    private LifecycleOwner lifecycleOwner;
    private View elLayoutDeCadaItem;
    private ArrayList<Profesor> lista;
    private OnItemClickListener listener;
    public AdaptadorProfesLista(Context pcontext, ArrayList<Profesor> profesLis, LifecycleOwner viewLifecycleOwner)
    {
        contexto = pcontext;
        lista=profesLis;
        lifecycleOwner=viewLifecycleOwner;
    }

    @NonNull
    @Override
    public ProfesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        elLayoutDeCadaItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_profes,null);
        ProfesViewHolder pvh = new ProfesViewHolder(elLayoutDeCadaItem);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesViewHolder holder, int position) {
        Profesor p=lista.get(position);
        String foto=Imagenes.obtenerImagen2(p.getIdProfe());
        if (!foto.equals("imagen")) {
            if (foto.length()<100){
                Uri imageUri = Uri.parse(foto);
                holder.perfilprofe.setImageURI(imageUri);
            }else {
                String image64 = foto;
                byte[] b = Base64.decode(image64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
                        b.length);
                Bitmap rescaledImage = adjustImageSize(bitmap);
                holder.perfilprofe.setImageBitmap(rescaledImage);
            }
        }
        holder.nombre.setText(p.getNombre());
        holder.asignaturas.setText(p.getAsig());
        holder.direccion.setText(p.getDireccion());
        holder.val.setRating(p.getVal());
        holder.precio.setText(p.getPrecio()+"€/h");
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private Bitmap adjustImageSize(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int length = bitmap.getHeight();

        int newSize = 800;
        float scaleWidth = ((float) newSize/width);
        float scaleLength = ((float) newSize/length);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleLength);

        return Bitmap.createBitmap(bitmap, 0,0, width, length, matrix, true);
    }
}

