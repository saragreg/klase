package com.example.tfg_profes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentResennas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResennas extends Fragment {
    private RecyclerView resennasRecyclerView;
    public FragmentResennas() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentResennas newInstance(String param1, String param2) {
        FragmentResennas fragment = new FragmentResennas();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resennas, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String profe=getArguments().getString("user");
        Resenna.resennasLis=new ArrayList<>();

        MutableLiveData<Boolean> workerFinishedLiveData = new MutableLiveData<>();

        Data inputData = new Data.Builder()
                .putString("tipo", "ListaResennas")
                .putString("profe", profe)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                            String idCli = workInfo.getOutputData().getString("idCli");
                            String val = workInfo.getOutputData().getString("val");
                            String comentario = workInfo.getOutputData().getString("comentario");
                            String fecha = workInfo.getOutputData().getString("fecha");

                            String[] arrayi = idCli.split(",");
                            String[] arrayv = val.split(";");
                            String[] arrayc = comentario.split("20011114s");
                            String[] arrayf = fecha.split(",");

                            int i=0;
                            if(!idCli.equals("")) {
                                while (i < arrayi.length) {
                                    if (arrayc[i].equals("nada")){
                                        arrayc[i]="";
                                    }
                                    Resenna resenna = new Resenna(profe, arrayi[i],Float.parseFloat(arrayv[i]),arrayc[i],arrayf[i]);
                                    Resenna.resennasLis.add(resenna);
                                    i++;
                                }
                            }
                            if (i!=0) {
                                RecyclerView lista = view.findViewById(R.id.resennasRecyclerView);
                                AdaptadorResennas eladap = new AdaptadorResennas(Resenna.resennasLis);
                                lista.setAdapter(eladap);
                                LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                lista.setLayoutManager(elLayoutLineal);
                            }else{
                                Toast.makeText(getContext(), "No hay reseñas", Toast.LENGTH_SHORT).show();
                            }

                            workerFinishedLiveData.setValue(true); // Notificar que el worker ha terminado
                        }
                    }
                });

        WorkManager.getInstance(getContext()).enqueue(otwr);
        workerFinishedLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean workerFinished) {
                if (workerFinished) {
                    // Worker ha terminado, se puede devolver la vista
                    // Inflate the layout for this fragment
                }
            }
        });
    }
}