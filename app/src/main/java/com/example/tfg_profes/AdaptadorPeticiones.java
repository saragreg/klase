package com.example.tfg_profes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

public class AdaptadorPeticiones extends RecyclerView.Adapter<ElViewHolder> {

    private Context contexto;
    private ArrayList<Peticion> lista;
    private LifecycleOwner lifecycleOwner;
    private String estado;
    private View elLayoutDeCadaItem;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mensajeriafcm-ea7c9-default-rtdb.europe-west1.firebasedatabase.app/");
    private String chatKey = "", usuario;
    String userComprobar1 = "";
    String userComprobar2 = "";

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
        if (!p.getFotoper().equals("imagen")) {
            if (p.getFotoper().length()<100){
                Uri imageUri = Uri.parse(p.getFotoper());
                holder.userfoto.setImageURI(imageUri);
            }else {
                String image64 = p.getFotoper();
                byte[] b = Base64.decode(image64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
                        b.length);
                Bitmap rescaledImage = adjustImageSize(bitmap);
                holder.userfoto.setImageBitmap(rescaledImage);
            }
        }
        holder.nombre.setText(p.getIdUsu());
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
        int j=asigind.length;
        if (1<=j){
            holder.asig1.setVisibility(View.VISIBLE);
            holder.asig1.setText(asigind[0]);
        }
        if (2<=j){
            holder.asig2.setVisibility(View.VISIBLE);
            holder.asig2.setText(asigind[1]);
        }
        if (3<=j){
            holder.asig3.setVisibility(View.VISIBLE);
            holder.asig3.setText(asigind[2]);
        }
        if (4<=j){
            holder.asig4.setVisibility(View.VISIBLE);
            holder.asig4.setText(asigind[3]);
        }
        if (5<=j){
            holder.asig5.setVisibility(View.VISIBLE);
            holder.asig5.setText(asigind[4]);
        }
        if (6<=j){
            holder.asig6.setVisibility(View.VISIBLE);
            holder.asig6.setText(asigind[5]);
        }
        if (7<=j){
            holder.asig7.setVisibility(View.VISIBLE);
            holder.asig7.setText(asigind[6]);
        }
        if (8<=j){
            holder.asig8.setVisibility(View.VISIBLE);
            holder.asig8.setText(asigind[7]);
        }

        holder.aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enviar notificacion al usuario
                enviarnotificacion(p.getIdUsu());
                FileUtils fileUtils=new FileUtils();
                String user=fileUtils.readFile(contexto,"config.txt");
                //añadir eventos a la agenda
                String[] fechahora=p.getFechahora().split(" ");
                String fecha=fechahora[0];
                String descr="",descr2="";
                if (fechahora.length>1){
                    String hora=fechahora[1];
                    descr="Klase con "+p.getNombre()+" a las "+hora;
                    descr2="Klase con "+user+" a las "+hora;
                }else{
                    descr="Klase con "+p.getNombre();
                    descr2="Klase con "+user;
                }
                Evento evento=new Evento(descr, LocalDate.parse(fecha));
                Evento.eventosLis.add(evento);
                addEvento(user,descr,fecha);
                addEvento(p.getIdUsu(),descr2,fecha);
                //añadir el contacto a la lista de contactos del chat
                if (!Imagenes.esContacto(p.getIdUsu())) {
                    addContacto(user, p.getIdUsu());
                }
                //mostrar dialogo de reserva confirmada
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
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerClave(holder.nombre.getText().toString(),holder.userfoto.toString());
            }
        });
    }
    private void obtenerClave(String otroMail, String foto) {
        chatKey = "";
        FileUtils fileUtils= new FileUtils();
        usuario=fileUtils.readFile(contexto,"config.txt");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("chat")) {
                    for (DataSnapshot messagesnapshot : snapshot.child("chat").getChildren()) {
                        if (messagesnapshot.hasChild("user1") && messagesnapshot.hasChild("user2")) {
                            userComprobar1 = messagesnapshot.child("user1").getValue(String.class);
                            userComprobar2 = messagesnapshot.child("user2").getValue(String.class);
                            if ((userComprobar1.equals(otroMail) || userComprobar1.equals(usuario)) && (userComprobar2.equals(otroMail) || userComprobar2.equals(usuario))) {
                                //se asume que no existen mensajes contigo mismo
                                chatKey = messagesnapshot.getKey();
                            }
                        }
                    }
                }
                //se abre la ventana de chat
                Intent intent = new Intent(contexto, Chat.class);
                intent.putExtra("nombre",otroMail);
                intent.putExtra("mail1",otroMail);
                intent.putExtra("fotoPerfil",foto);
                intent.putExtra("mailUser",usuario);
                intent.putExtra("chatKey", chatKey);
                //intent.putExtra("fotoPerfil",imgs.get(i));
                contexto.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addContacto(String user1mail, String usermail) {
        Data inputData = new Data.Builder()
                .putString("tipo", "addContacto")
                .putString("usu1",user1mail)
                .putString("usu2",usermail)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(contexto).getWorkInfoByIdLiveData(otwr.getId())
                .observe(lifecycleOwner, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            cargarFotoPerfil(usermail);
                        }
                    }
                });
        WorkManager.getInstance(contexto).enqueue(otwr);
    }
    private void cargarFotoPerfil(String usu) {
        FileUtils fileUtils=new FileUtils();
        String user = fileUtils.readFile(contexto, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "cargarFotoPerfil")
                .putString("user",usu)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(contexto).getWorkInfoByIdLiveData(otwr.getId())
                .observe(lifecycleOwner, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String fotoPer=workInfo.getOutputData().getString("img");
                            if (usu.equals(user)) {
                                Usuario.usuariosLis.get(0).setImagen("");
                            }
                        }
                    }
                });
        WorkManager.getInstance(contexto).enqueue(otwr);
    }

    private void addEvento(String user, String descr, String fecha) {
        Data inputData = new Data.Builder()
                .putString("tipo", "addEvento")
                .putString("usu", user)
                .putString("fecha", fecha)
                .putString("descr", descr)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(contexto).getWorkInfoByIdLiveData(otwr.getId())
                .observe(lifecycleOwner, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                            Toast.makeText(contexto, "Se ha añadido correctamente", Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    }
                });
        WorkManager.getInstance(contexto).enqueue(otwr);
    }

    private void updateEstado(String estado,int pos) {
        if (lista.size()>0) {
            lista.get(pos).setEstado(estado);
            FileUtils fileUtils = new FileUtils();
            String user = fileUtils.readFile(contexto, "config.txt");
            Data inputData = new Data.Builder()
                    .putString("tipo", "updateEstadoPeticion")
                    .putString("user", user)
                    .putString("idUsu", lista.get(pos).getIdUsu())
                    .putString("idFecha", lista.get(pos).getFechacreacion())
                    .putString("estado", estado)
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
    }
    public void eliminarElemento(int posicion) {
        if(posicion==lista.size()){
            lista=new ArrayList<>();
        }else {
            lista.remove(posicion);
            notifyItemRemoved(posicion);
        }
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
    private void enviarnotificacion(String usuIntro) {
        Data inputData = new Data.Builder()
                .putString("usuario",usuIntro)
                .putString("descr","su solicitud ha sido aceptada")
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDmensajes.class).setInputData(inputData).build();
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