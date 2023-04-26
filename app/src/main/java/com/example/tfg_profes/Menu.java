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
    ArrayList<String> usus;
    ArrayList<String> noms;
    ArrayList<String> precios;
    ArrayList<String> punt;
    String usuario;
    ImageButton lisprofes,perfilbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        usuario=getIntent().getExtras().getString("usuario");
        obtenerDatosProfes();
        lisprofes=findViewById(R.id.listabtn);
        lisprofes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ListaProfesores.class);
                intent.putStringArrayListExtra("usus",usus);
                intent.putStringArrayListExtra("noms",noms);
                intent.putStringArrayListExtra("precios",precios);
                intent.putStringArrayListExtra("punt",punt);
                startActivity(intent);
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



    }

    public void obtenerDatosProfes() {
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

                            System.out.println("nombres: "+nombre);

                            String[] arrayu = usuarios.split(",");
                            String[] arrayn = nombre.split(",");
                            String[] arrayp = precio.split(",");
                            String[] arraypp = punts.split(",");

                            usus = new ArrayList<String>(Arrays.asList(arrayu));
                            noms = new ArrayList<String>(Arrays.asList(arrayn));
                            precios = new ArrayList<String>(Arrays.asList(arrayp));
                            punt = new ArrayList<String>(Arrays.asList(arraypp));

                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);


    }
}