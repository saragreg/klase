package com.example.tfg_profes;

import android.content.Intent;
import android.location.Location;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lis_profes, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FileUtils fileUtils= new FileUtils();
        String user= fileUtils.readFile(getContext(), "config.txt");
        lisprofes = view.findViewById(R.id.listaprofesores);
        Data inputData = new Data.Builder()
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
        WorkManager.getInstance(getContext()).enqueue(otwr);

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
        });
        eladap.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Profesor position) {
                // Obtén el elemento en la posición seleccionada

                obtenerInfoProfe(position.getIdProfe(), position.getPrecio(), String.valueOf(position.getVal()));
            }
        });*/
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
    private void obtenerLoc(String usu) {
        Data inputData = new Data.Builder()
                .putString("tipo", "selectLoc")
                .putString("usuario", usu)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String latUsuss = workInfo.getOutputData().getString("lat");
                            String lngUsuss = workInfo.getOutputData().getString("lng");
                            latUsu=Double.parseDouble(latUsuss);
                            lngUsu=Double.parseDouble(lngUsuss);

                            calcularDistancias();
                            if (Profesor.lisProfes.size()!=0) {
                                eladap = new AdaptadorProfesLista(requireContext(),Profesor.lisProfes,getViewLifecycleOwner());
                                lisprofes.setAdapter(eladap);
                                LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                lisprofes.setLayoutManager(elLayoutLineal);
                            }else{
                                Toast.makeText(getContext(), "No hay tutores disponibles", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);
    }

    public void calcularDistancias(){
        int i=0;
        Float[] distancias = new Float[Profesor.lisProfes.size()];
        Location location1 = new Location("loc1");
        location1.setLatitude(latUsu);
        location1.setLatitude(lngUsu);
        while (i<Profesor.lisProfes.size()){
            Profesor p=Profesor.lisProfes.get(i);
            Double latitud=Double.parseDouble(p.getLat());//obtenemos la latitud
            Double longitud=Double.parseDouble(p.getLng());//obtenemos la longitud
            Location location2 = new Location("loc2");
            location2.setLatitude(latitud);
            location2.setLatitude(longitud);
            float distance = location1.distanceTo(location2);
            distancias[i]=distance;
            i++;
        }

        quicksort(distancias,0,i-1);

    }

    public static void quicksort(Float A[], int izq, int der) {

        Float pivote = A[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Float aux;

        Profesor pivote2 = Profesor.lisProfes.get(izq);
        Profesor aux2;

        while (i < j) {                          // mientras no se crucen las búsquedas
            while (A[i] <= pivote && i < j) i++; // busca elemento mayor que pivote
            while (A[j] > pivote) j--;           // busca elemento menor que pivote
            if (i < j) {                        // si no se han cruzado
                aux = A[i];                      // los intercambia
                A[i] = A[j];
                A[j] = aux;

                aux2 = Profesor.lisProfes.get(i);
                Profesor.lisProfes.add(i,Profesor.lisProfes.get(j));
                Profesor.lisProfes.remove(i+1);
                Profesor.lisProfes.add(j,aux2);
                Profesor.lisProfes.remove(j+1);
                //B[i] = B[j];
                //B[j] = aux2;

            }
        }

        A[izq] = A[j];      // se coloca el pivote en su lugar de forma que tendremos
        A[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

        Profesor.lisProfes.add(izq,Profesor.lisProfes.get(j));
        Profesor.lisProfes.remove(izq+1);
        Profesor.lisProfes.add(j,pivote2);
        Profesor.lisProfes.remove(j+1);
        //B[izq] = B[j];
        //B[j] = pivote2;
        if (izq < j - 1) {
            quicksort(A, izq, j - 1);
        }// ordenamos subarray izquierdo
        if (j + 1 < der){
            quicksort(A, j + 1, der);          // ordenamos subarray derecho
        }
    }

}