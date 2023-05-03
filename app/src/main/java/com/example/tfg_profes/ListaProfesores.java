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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaProfesores extends AppCompatActivity {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_profesores);
        String usuarios = getIntent().getExtras().getString("usus");
        String nombre = getIntent().getExtras().getString("noms");
        String precio = getIntent().getExtras().getString("precios");
        String punts = getIntent().getExtras().getString("punt");
        String[] arrayu = usuarios.split(",");
        String[] arrayn = nombre.split(",");
        String[] arrayp = precio.split(",");
        String[] arraypp = punts.split(",");

        usus = new ArrayList<String>(Arrays.asList(arrayu));
        noms = new ArrayList<String>(Arrays.asList(arrayn));
        precios = new ArrayList<String>(Arrays.asList(arrayp));
        punt = new ArrayList<String>(Arrays.asList(arraypp));

        ListView lisprofes = findViewById(R.id.listView);
        AdaptadorProfesLista eladap = new AdaptadorProfesLista(getApplicationContext(), noms, precios, punt);
        lisprofes.setAdapter(eladap);
        lisprofes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                noms.remove(i);
                precios.remove(i);
                eladap.notifyDataSetChanged();
                return true;
            }
        });
        lisprofes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerInfoProfe(usus.get(i));
            }
        });
    }

    private void obtenerInfoProfe(String usu) {
        Data inputData = new Data.Builder()
                .putString("tipo", "infoProfe")
                .putString("usuario", usu)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String usuario = workInfo.getOutputData().getString("usu");
                            String nombre = workInfo.getOutputData().getString("nombre");
                            String precio = workInfo.getOutputData().getString("precio");
                            String asig = workInfo.getOutputData().getString("asig");
                            String cursos = workInfo.getOutputData().getString("cursos");
                            String idiomas = workInfo.getOutputData().getString("idiomas");
                            String exp = workInfo.getOutputData().getString("exp");
                            String punt = workInfo.getOutputData().getString("punt");

                            System.out.println("cursos: "+cursos);
                            Intent intent = new Intent(ListaProfesores.this, InfoProfes.class);
                            intent.putExtra("usus",usuario);
                            intent.putExtra("noms",nombre);
                            intent.putExtra("precios",precio);
                            intent.putExtra("asig",asig);
                            intent.putExtra("cursos",cursos);
                            intent.putExtra("idiomas",idiomas);
                            intent.putExtra("exp",exp);
                            intent.putExtra("punt",punt);
                            startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }



    private void enviarnotificacion(String usuIntro) {
        Data inputData = new Data.Builder()
                .putString("usuario",usuIntro)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDmensajes.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
}