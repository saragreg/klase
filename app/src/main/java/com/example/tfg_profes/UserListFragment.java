package com.example.tfg_profes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tfg_profes.utils.FileUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        TextView noContacts=view.findViewById(R.id.nocontacts);
        noContacts.setVisibility(View.GONE);
        UserListAdapter userListAdapter=new UserListAdapter(getContext(),Imagenes.lisContactos);
        lista.setAdapter(userListAdapter);
        if (Imagenes.lisContactos.size()==0){
            noContacts.setVisibility(View.VISIBLE);
        }
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerClave(Imagenes.lisContactos.get(i).getUser(), i);
            }
        });

        return view;
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
                intent.putExtra("nombre", Imagenes.lisContactos.get(pos).getUser());
                intent.putExtra("mail1",Imagenes.lisContactos.get(pos).getUser());
                intent.putExtra("fotoPerfil",Imagenes.lisContactos.get(pos).getImagen());
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