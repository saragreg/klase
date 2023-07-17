package com.example.tfg_profes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLisProfes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLisProfes extends Fragment {
    Double latUsu,lngUsu;
    RecyclerView lisprofes;
    AdaptadorProfesLista eladap;
    public FragmentLisProfes() {
        // Required empty public constructor
    }

    public static FragmentLisProfes newInstance() {
        FragmentLisProfes fragment = new FragmentLisProfes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lis_profes, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FileUtils fileUtils= new FileUtils();
        String user= fileUtils.readFile(getContext(), "config.txt");
        lisprofes = view.findViewById(R.id.listaprofesores);
        if (Profesor.lisProfes.size()!=0) {
            eladap=new AdaptadorProfesLista(requireContext(),Profesor.lisProfes,getViewLifecycleOwner());
            lisprofes.setAdapter(eladap);
            LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            lisprofes.setLayoutManager(elLayoutLineal);
        }else{
            Toast.makeText(getContext(), "No hay tutores disponibles", Toast.LENGTH_SHORT).show();
        }
        /*eladap.setOnItemClickListener(new AdaptadorProfesLista.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Obtén el elemento en la posición seleccionada
                Toast.makeText(getContext(), "SE HA PULSADO", Toast.LENGTH_SHORT).show();
                Profesor item = Profesor.lisProfes.get(position);

                // Abre la actividad deseada, pasando el elemento como dato
                Intent intent = new Intent(getContext(), InfoProfes.class);
                intent.putExtra("usu",item.getIdProfe());
                intent.putExtra("precio",item.getPrecio());
                intent.putExtra("asig",item.getAsig());
                intent.putExtra("cursos",item.getCursos());
                intent.putExtra("idiomas",item.getIdiomas());
                intent.putExtra("exp",item.getExperiencia());
                intent.putExtra("punt",String.valueOf(item.getVal()));
                startActivity(intent);
            }
        });*/

        /*Data inputData = new Data.Builder()
                .putString("tipo", "infoLista")
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            obtenerLoc(user);
                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);*/

        /*lisprofes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                noms.remove(i);
                precios.remove(i);
                adapterView.notify();
                return true;
            }
        });

        lisprofes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerInfoProfe(usus.get(i), precios.get(i), punt.get(i));
            }
        });*/
        // Inflate the layout for this fragment
    }

    public void obtenerInfoProfe(String usu,String prec, String puntuacion) {
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
                            intent.putExtra("usu",usu);
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