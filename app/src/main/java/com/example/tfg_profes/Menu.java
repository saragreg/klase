package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends AppCompatActivity {
    String usus;
    String noms;
    String precios;
    String punt;
    String usuario;
    ImageButton lisprofes,perfilbtn,mapabtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        usuario=getIntent().getExtras().getString("usuario");

        lisprofes=findViewById(R.id.listabtn);
        lisprofes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDatosProfes(usuario);
            }
        });
        perfilbtn=findViewById(R.id.perfilbtn);
        perfilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Perfil.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });
        mapabtn=findViewById(R.id.mapa);
        mapabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Mapa.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });
    }

    public void obtenerDatosProfes(String usuInt) {
        Data inputData = new Data.Builder()
                .putString("tipo", "infoLista")
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String usuarios = workInfo.getOutputData().getString("usu");
                            String nombre = workInfo.getOutputData().getString("nombre");
                            String precio = workInfo.getOutputData().getString("precio");
                            String punts = workInfo.getOutputData().getString("punt");

                            System.out.println("punt: "+punts);

                            Intent intent = new Intent(Menu.this, ListaProfesores.class);
                            intent.putExtra("usuario", usuInt);
                            intent.putExtra("usus",usuarios);
                            intent.putExtra("noms",nombre);
                            intent.putExtra("precios",precio);
                            intent.putExtra("punt",punts);
                            startActivity(intent);


                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);


    }


}