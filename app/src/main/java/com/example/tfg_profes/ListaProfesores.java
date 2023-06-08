package com.example.tfg_profes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

public class ListaProfesores extends Fragment {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> locs= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();
    String usuario;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_profesores);
        usuario=getIntent().getExtras().getString("usuario");
        usus = getIntent().getExtras().getStringArrayList("usus");
        noms = getIntent().getExtras().getStringArrayList("noms");
        precios = getIntent().getExtras().getStringArrayList("precios");
        punt = getIntent().getExtras().getStringArrayList("punt");
        locs = getIntent().getExtras().getStringArrayList("locs");

        ListView lisprofes = findViewById(R.id.listView);
        AdaptadorProfesLista eladap = new AdaptadorProfesLista(getApplicationContext(), usus, precios, punt,locs);
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
                obtenerInfoProfe(usus.get(i),precios.get(i),punt.get(i));
            }
        });
    }*/
    public ListaProfesores() {
        // Required empty public constructor
    }
    public static ListaProfesores newInstance(String param1, String param2) {
        ListaProfesores fragment = new ListaProfesores();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lista_profesores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuario = getArguments().getString("usuario");
        usus = getArguments().getStringArrayList("usus");
        noms = getArguments().getStringArrayList("noms");
        precios = getArguments().getStringArrayList("precios");
        punt = getArguments().getStringArrayList("punt");
        locs = getArguments().getStringArrayList("locs");

        ListView lisprofes = view.findViewById(R.id.listView);
        AdaptadorProfesLista eladap = new AdaptadorProfesLista(requireContext(), usus, precios, punt, locs);
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
                obtenerInfoProfe(usus.get(i), precios.get(i), punt.get(i));
            }
        });
    }

    private void obtenerInfoProfe(String usu,String prec, String puntuacion) {
        Data inputData = new Data.Builder()
                .putString("tipo", "infoProfe")
                .putString("usuario", usu)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String asig = workInfo.getOutputData().getString("asig");
                            String cursos = workInfo.getOutputData().getString("cursos");
                            String idiomas = workInfo.getOutputData().getString("idiomas");
                            String exp = workInfo.getOutputData().getString("exp");

                            System.out.println("cursos: "+cursos);
                            Intent intent = new Intent(getContext(), InfoProfes.class);
                            intent.putExtra("usus",usuario);
                            intent.putExtra("precio",prec);
                            intent.putExtra("asig",asig);
                            intent.putExtra("cursos",cursos);
                            intent.putExtra("idiomas",idiomas);
                            intent.putExtra("exp",exp);
                            intent.putExtra("punt",puntuacion);
                            startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);
    }




}