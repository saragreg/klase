package com.example.tfg_profes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Arrays;

public class FragmentPeticiones extends Fragment {

    private ArrayList<String> asignaturas=new ArrayList<>(),noms=new ArrayList<>(),fotos=new ArrayList<>(),duraciones=new ArrayList<>(),fechas=new ArrayList<>(),intens=new ArrayList<>(),diasSem=new ArrayList<>();
    private View view;
    public FragmentPeticiones() {
        // Required empty public constructor
    }
    public static FragmentPeticiones newInstance(String param1, String param2) {
        FragmentPeticiones fragment = new FragmentPeticiones();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_peticiones, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    /*FileUtils fileUtils = new FileUtils();
    String user = fileUtils.readFile(requireContext(), "config.txt");*/
        String user = "monica";

        MutableLiveData<Boolean> workerFinishedLiveData = new MutableLiveData<>();

        Data inputData = new Data.Builder()
                .putString("tipo", "ListaPeticiones")
                .putString("user", user)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String asig = workInfo.getOutputData().getString("asig");
                            String nombre = workInfo.getOutputData().getString("nombre");
                            String fotoper = workInfo.getOutputData().getString("fotoper");
                            String duracion = workInfo.getOutputData().getString("duracion");
                            String fechahora = workInfo.getOutputData().getString("fechahora");
                            String intensivo = workInfo.getOutputData().getString("intensivo");
                            String dias = workInfo.getOutputData().getString("dias");

                            String[] arraya = asig.split(";");
                            String[] arrayn = nombre.split(",");
                            String[] arrayf = fotoper.split(",");
                            String[] arrayd = duracion.split(",");
                            String[] arrayh = fechahora.split(",");
                            String[] arrayi = intensivo.split(",");
                            String[] arraydd = dias.split(",");

                            asignaturas = new ArrayList<>(Arrays.asList(arraya));
                            noms = new ArrayList<>(Arrays.asList(arrayn));
                            fotos = new ArrayList<>(Arrays.asList(arrayf));
                            duraciones = new ArrayList<>(Arrays.asList(arrayd));
                            fechas = new ArrayList<>(Arrays.asList(arrayh));
                            intens = new ArrayList<>(Arrays.asList(arrayi));
                            diasSem = new ArrayList<>(Arrays.asList(arraydd));

                            RecyclerView lista = view.findViewById(R.id.recyclerViewPeticiones);
                            AdaptadorPeticiones eladap = new AdaptadorPeticiones(requireContext(), asignaturas, fotos, noms, duraciones, fechas, intens, diasSem);
                            lista.setAdapter(eladap);
                            LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            lista.setLayoutManager(elLayoutLineal);

                            workerFinishedLiveData.setValue(true); // Notificar que el worker ha terminado
                        }
                    }
                });

        WorkManager.getInstance(getContext()).enqueue(otwr);
        /*workerFinishedLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean workerFinished) {
                if (workerFinished) {
                    // Worker ha terminado, se puede devolver la vista
                    // Inflate the layout for this fragment
                }
            }
        });*/

    }

    }