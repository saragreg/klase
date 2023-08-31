package com.example.tfg_profes;

import android.content.Context;
import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

public class AdaptadorProfesLista extends RecyclerView.Adapter<ProfesViewHolder> {

    private Context contexto;
    private View elLayoutDeCadaItem;
    private OnItemClickListener listener;
    private ArrayList<Profesor> lista,listaCompleta;
    private LifecycleOwner lifecycleOwner;
    public AdaptadorProfesLista(Context pcontext, ArrayList<Profesor> profesLis, LifecycleOwner viewLifecycleOwner)
    {
        contexto = pcontext;
        lista=profesLis;
        listaCompleta = new ArrayList<>(profesLis);
        lifecycleOwner=viewLifecycleOwner;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void filtrarPorAsignaturas(ArrayList<String> asignaturasSeleccionadas) {
        ArrayList<Profesor> profesFiltrados = new ArrayList<>();

        // Itera sobre cada profesor en la lista original
        for (Profesor profesor : listaCompleta) {
            // Verifica si alguna de las asignaturas del profesor coincide con las asignaturas seleccionadas
            for (String asignatura : asignaturasSeleccionadas) {
                if (profesor.getAsig().contains(asignatura)) {
                    profesFiltrados.add(profesor); // Agrega el profesor a la lista filtrada
                    break; // Rompe el bucle interno si se encuentra una coincidencia
                }
            }
        }

        // Actualiza la lista de profesores con la lista filtrada
        lista = profesFiltrados;
        notifyDataSetChanged();
    }

    public void restaurarListaCompleta() {
        lista = new ArrayList<>(listaCompleta); // Restaura la lista con todos los profesores
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void onItemClick(int position) {
        // Obtén el elemento en la posición seleccionada
        Profesor item = lista.get(position);

        cargarHorario(item.getIdProfe(),item);
    }
    private void cargarHorario(String user,Profesor item) {
        Data inputData = new Data.Builder()
                .putString("tipo", "infoHorario")
                .putString("user", user)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(contexto).getWorkInfoByIdLiveData(otwr.getId())
                .observe(lifecycleOwner, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            // Crea un Intent con el contexto del adaptador y la actividad de destino
                            Intent intent = new Intent(contexto, InfoProfes.class);
                            intent.putExtra("usu", item.getIdProfe());
                            intent.putExtra("precio", item.getPrecio());
                            intent.putExtra("asig", item.getAsig());
                            intent.putExtra("cursos", item.getCursos());
                            intent.putExtra("idiomas", item.getIdiomas());
                            intent.putExtra("exp", item.getExperiencia());
                            intent.putExtra("punt", String.valueOf(item.getVal()));

                            // Inicia la actividad con el Intent
                            contexto.startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(contexto).enqueue(otwr);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position);
            }
        });
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

