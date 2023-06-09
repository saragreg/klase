package com.example.tfg_profes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mensajeriafcm-ea7c9-default-rtdb.europe-west1.firebasedatabase.app/");
    private String chatKey = "", usuario;
    String userComprobar1 = "";
    String userComprobar2 = "";
    private ArrayList<String> noms = new ArrayList<>();
    private ArrayList<String> imgs = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        FileUtils fileUtils=new FileUtils();
        usuario=fileUtils.readFile(requireContext(), "config.txt");
        lista = view.findViewById(R.id.users_list);
        //obtenerContactos();
        noms=new ArrayList<String>();
        imgs=new ArrayList<String>();
        noms.add("sara");
        noms.add("sofia");
        noms.add("prueba");
        String imagen="https://lapi.com.mx/web/image/product.product/31740/image_1024/Perfil%20Mujer%20%28Balbuena%29?unique=a660b70";
        imgs.add(imagen);
        imgs.add(imagen);
        imgs.add(imagen);
        users.add("sara");
        users.add("sofia");
        users.add("prueba");
        UserListAdapter userListAdapter=new UserListAdapter(getContext(),noms,imgs);
        lista.setAdapter(userListAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerClave(users.get(i), i);
            }
        });

        return view;
    }

    private void obtenerContactos() {
        FileUtils fileUtils=new FileUtils();
        String user=fileUtils.readFile(requireContext(), "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "obtenerContactos")
                .putString("usuario", user)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String nombres = workInfo.getOutputData().getString("nombres");
                            String imagenes = workInfo.getOutputData().getString("imgs");
                            String[] nomsArray=nombres.split(",");
                            String[] imgsArray=imagenes.split(",");
                            noms=new ArrayList<String>(Arrays.asList(nomsArray));
                            imgs=new ArrayList<String>(Arrays.asList(imgsArray));
                            UserListAdapter userListAdapter=new UserListAdapter(getContext(),noms,imgs);
                            lista.setAdapter(userListAdapter);
                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);
    }

    private void obtenerClave(String otroMail, int pos) {
        chatKey = "";
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
                Intent intent = new Intent(requireActivity(), Chat.class);
                intent.putExtra("nombre", noms.get(pos));
                intent.putExtra("mail1",users.get(pos));
                intent.putExtra("fotoPerfil",imgs.get(pos));
                intent.putExtra("mailUser",usuario);
                intent.putExtra("chatKey", chatKey);
                //intent.putExtra("fotoPerfil",imgs.get(i));
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}