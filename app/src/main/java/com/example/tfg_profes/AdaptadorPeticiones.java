package com.example.tfg_profes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;
import com.google.android.material.internal.FlowLayout;

import java.util.ArrayList;

public class AdaptadorPeticiones extends RecyclerView.Adapter<ElViewHolder> {

    private Context contexto;
    /*private ArrayList<String> asig;
    private ArrayList<String> perfil;
    private ArrayList<String> nombres;
    private ArrayList<String> duracion;
    private ArrayList<String> fechahora;
    private ArrayList<String> intensivos;
    private ArrayList<String> dias;*/
    private ArrayList<Peticion> lista;
    private LifecycleOwner lifecycleOwner;
    private String estado;
    private View elLayoutDeCadaItem;
    /*public AdaptadorPeticiones(Context pcontext, ArrayList<String> pasig, ArrayList<String> pperfil, ArrayList<String> pnombres, ArrayList<String> pduracion,ArrayList<String> pfechahora,ArrayList<String> pintensivo,ArrayList<String> pdias)
    {
        contexto = pcontext;
        asig = pasig;
        perfil=pperfil;
        nombres=pnombres;
        duracion=pduracion;
        fechahora=pfechahora;
        intensivos=pintensivo;
        dias=pdias;
    }*/

    public AdaptadorPeticiones(Context pcontext, ArrayList<Peticion> peticionesLis, LifecycleOwner viewLifecycleOwner) {
        contexto = pcontext;
        lista=peticionesLis;
        lifecycleOwner=viewLifecycleOwner;
    }

    public ElViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        elLayoutDeCadaItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_peticiones,null);
        ElViewHolder evh = new ElViewHolder(elLayoutDeCadaItem);
        evh.estado=estado;
        modificarEstado(estado);
        return evh;
    }

    private void modificarEstado(String estado) {
    }

    @Override
    public void onBindViewHolder(@NonNull ElViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Peticion p=lista.get(position);
        if (!p.getFotoper().equals("null")) {
            String image64 = p.getFotoper();
            byte[] b = Base64.decode(image64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,
                    b.length);
            Bitmap rescaledImage = adjustImageSize(bitmap);
            holder.userfoto.setImageBitmap(rescaledImage);
        }
        holder.nombre.setText(p.getNombre());
        holder.dur.setText(p.getDuracion());
        holder.fechaHoraPet.setText(p.getFechahora());
        holder.intensivo.setText(p.getIntensivo());
        if (p.getIntensivo().equals("intensivo")){
            holder.diasPet.setVisibility(View.VISIBLE);
            holder.diasPet.setText(p.getDias());
        }else{
            holder.diasPet.setVisibility(View.GONE);
        }
        String[] asigind=p.getAsig().split(",");
        int j=0;
        while (j<asigind.length){
            TextView textView = new TextView(contexto);
            textView.setText(asigind[j]);
            textView.setBackground(elLayoutDeCadaItem.getResources().getDrawable(R.drawable.btn_asig_pulsada, null));
            textView.setPadding(10, 0, 10, 0);
            textView.setTextSize(24f);

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);

            holder.flowLayout.addView(textView);
            j++;
        }
        holder.aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle("Reserva confirmada");
                builder.setMessage("La klase se ha añadido a la agenda, te enviaré una notificación para que no se te olvide 24 horas antes");
                builder.show();
                estado="c";
                updateEstado(estado,position);
                eliminarElemento(position);
            }
        });
        holder.rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle("Rechazar Petición");
                builder.setMessage("¿Deseas rechazar esta petición?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        estado="r";
                        updateEstado(estado,position);
                        eliminarElemento(position);
                    }
                });
                builder.show();
            }
        });
    }
    private void updateEstado(String estado,int pos) {
        lista.get(pos).setEstado(estado);
        FileUtils fileUtils=new FileUtils();
        String user=fileUtils.readFile(contexto, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "updateEstadoPeticion")
                .putString("user", user)
                .putString("idUsu",lista.get(pos).getIdUsu())
                .putString("idFecha",lista.get(pos).getFechacreacion())
                .putString("estado",estado)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(contexto).getWorkInfoByIdLiveData(otwr.getId())
                .observe(lifecycleOwner, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });

        WorkManager.getInstance(contexto).enqueue(otwr);
    }
    public void eliminarElemento(int posicion) {
        lista.remove(posicion);
        notifyItemRemoved(posicion);
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }


   /* @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //ArrayList<String> asigindiv=new ArrayList<String>();
        view=inflater.inflate(R.layout.adaptador_peticiones,null);
        TextView nombre= (TextView) view.findViewById(R.id.username);
        ImageView userfoto=(ImageView) view.findViewById(R.id.fotoPerfil);
        TextView dur = (TextView) view.findViewById(R.id.duracion);
        TextView fechaHoraPet = (TextView) view.findViewById(R.id.fechaHoraPet);
        TextView intensivo = (TextView) view.findViewById(R.id.intensivo);
        TextView diasPet = (TextView) view.findViewById(R.id.diasPet);
        Flow flowLayout = (Flow) view.findViewById(R.id.flowlayout);



        if (!perfil.get(i).equals("default")) {
            String image64 = perfil.get(i);
            byte[] b = Base64.decode(image64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,
                    b.length);
            Bitmap rescaledImage = adjustImageSize(bitmap);
            userfoto.setImageBitmap(rescaledImage);
        }
        nombre.setText(nombres.get(i));
        dur.setText(duracion.get(i)+'h');
        fechaHoraPet.setText(fechahora.get(i));
        intensivo.setText(intensivos.get(i));
        if (intensivos.get(i).equals("intensivo")){
            diasPet.setVisibility(View.VISIBLE);
            diasPet.setText(dias.get(i));
        }else{
            diasPet.setVisibility(View.GONE);
        }
        String[] asigind=asig.get(i).split(",");
        int j=0;
        while (j<asigind.length){
            TextView textView = new TextView(contexto);
            textView.setText(asigind[j]);
            textView.setBackground(view.getResources().getDrawable(R.drawable.btn_asig_pulsada, null));
            textView.setPadding(10, 0, 10, 0);
            textView.setTextSize(24f);

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);

            flowLayout.addView(textView);
        }
        return view;
    }*/
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