package com.example.tfg_profes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
    private String usermail;
    private String userExtmail = "";
    private String chatKey = "";
    String mailComprobar1 = "";
    String mailComprobar2 = "";
    private ArrayList<String> noms = new ArrayList<>();
    private ArrayList<String> imgs = new ArrayList<>();
    private ArrayList<String> mails = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        ListView lista = view.findViewById(R.id.users_list);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerClave(mails.get(i), i);
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
                            mailComprobar1 = messagesnapshot.child("user1").getValue(String.class);
                            mailComprobar2 = messagesnapshot.child("user2").getValue(String.class);
                            if ((mailComprobar1.equals(otroMail) || mailComprobar1.equals(usermail)) && (mailComprobar2.equals(otroMail) || mailComprobar2.equals(usermail))) {
                                //se asume que no existen mensajes contigo mismo
                                chatKey = messagesnapshot.getKey();
                            }
                        }
                    }
                }
                //se abre la ventana de chat
                /*Intent intent = new Intent(requireActivity(), Chat.class);
                intent.putExtra("nombre", noms.get(pos));
                intent.putExtra("mail1",mails.get(pos));
                intent.putExtra("fotoPerfil",imgs.get(pos));
                intent.putExtra("mailUser",usermail);
                intent.putExtra("chatKey", chatKey);
                //intent.putExtra("fotoPerfil",imgs.get(i));
                startActivity(intent);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}