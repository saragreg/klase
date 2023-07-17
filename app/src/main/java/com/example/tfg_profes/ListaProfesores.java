package com.example.tfg_profes;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;

public class ListaProfesores extends Fragment {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> locs= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();
    Double latUsu,lngUsu;
    String usuario;

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
        ListView lisprofes = view.findViewById(R.id.listView);
        Data inputData = new Data.Builder()
                .putString("tipo", "infoLista")
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                            if (Profesor.lisProfes.size()!=0) {
                                RecyclerView lista = view.findViewById(R.id.resennasRecyclerView);
                                AdaptadorProfesLista eladap = new AdaptadorProfesLista(requireContext(),Profesor.lisProfes,getViewLifecycleOwner());
                                lista.setAdapter(eladap);
                                LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                lista.setLayoutManager(elLayoutLineal);
                            }else{
                                Toast.makeText(getContext(), "No hay tutores disponibles", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);

        lisprofes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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


    public void calcularDistancias(String[] lislat,String[] lislng,String[] usus,String[] nombres,String[] precios,String[] puntos,String[] locs){
        int i=0;
        Float[] distancias = new Float[lislat.length];
        Location location1 = new Location("loc1");
        location1.setLatitude(latUsu);
        location1.setLatitude(lngUsu);
        while (i<lislat.length){
            Double latitud=Double.parseDouble(lislat[i]);//obtenemos la latitud
            Double longitud=Double.parseDouble(lislng[i]);//obtenemos la longitud
            Location location2 = new Location("loc2");
            location2.setLatitude(latitud);
            location2.setLatitude(longitud);
            float distance = location1.distanceTo(location2);
            distancias[i]=distance;
            i++;
        }

        quicksort(distancias,0,i-1,usus,nombres,precios,puntos,locs);

    }

    public static void quicksort(Float A[], int izq, int der, String B[],String C[],String D[],String E[],String F[]) {

        Float pivote = A[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Float aux;

        String pivote2 = B[izq];
        String aux2;
        String pivote3 = C[izq];
        String aux3;
        String pivote4 = D[izq];
        String aux4;
        String pivote5 = E[izq];
        String aux5;
        String pivote6 = F[izq];
        String aux6;

        while (i < j) {                          // mientras no se crucen las búsquedas
            while (A[i] <= pivote && i < j) i++; // busca elemento mayor que pivote
            while (A[j] > pivote) j--;           // busca elemento menor que pivote
            if (i < j) {                        // si no se han cruzado
                aux = A[i];                      // los intercambia
                A[i] = A[j];
                A[j] = aux;

                aux2 = B[i];
                B[i] = B[j];
                B[j] = aux2;
                aux3 = C[i];
                C[i] = C[j];
                C[j] = aux3;
                aux4 = D[i];
                D[i] = D[j];
                D[j] = aux4;
                aux5 = E[i];
                E[i] = E[j];
                E[j] = aux5;
                aux6 = F[i];
                F[i] = F[j];
                F[j] = aux6;
            }
        }

        A[izq] = A[j];      // se coloca el pivote en su lugar de forma que tendremos
        A[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

        B[izq] = B[j];
        B[j] = pivote2;
        C[izq] = C[j];
        C[j] = pivote3;
        D[izq] = D[j];
        D[j] = pivote4;
        E[izq] = E[j];
        E[j] = pivote5;
        F[izq] = F[j];
        F[j] = pivote6;
        if (izq < j - 1) {
            quicksort(A, izq, j - 1, B,C,D,E,F);
        }// ordenamos subarray izquierdo
        if (j + 1 < der){
            quicksort(A, j + 1, der, B,C,D,E,F);          // ordenamos subarray derecho
        }
    }

}