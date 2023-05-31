package com.example.tfg_profes;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

public class ListaProfesores extends AppCompatActivity {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> locs= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();
    String usuario;

    @Override
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
    }

    private void obtenerInfoProfe(String usu,String prec, String puntuacion) {
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
                            String asig = workInfo.getOutputData().getString("asig");
                            String cursos = workInfo.getOutputData().getString("cursos");
                            String idiomas = workInfo.getOutputData().getString("idiomas");
                            String exp = workInfo.getOutputData().getString("exp");

                            System.out.println("cursos: "+cursos);
                            Intent intent = new Intent(ListaProfesores.this, InfoProfes.class);
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
    public void calcularDistancias(String[] lat,String[] lng,Double latUsu, Double lngUsu,String[] usus){
        int i=0;
        Float[] distancias = new Float[0];
        Location location1 = new Location("loc1");
        location1.setLatitude(latUsu);
        location1.setLatitude(lngUsu);
        while (i<lat.length){
            Double latitud=Double.parseDouble(lat[i]);//obtenemos la latitud
            Double longitud=Double.parseDouble(lng[i]);//obtenemos la longitud
            Location location2 = new Location("loc2");
            location2.setLatitude(latitud);
            location2.setLatitude(longitud);
            float distance = location1.distanceTo(location2);
            distancias[i]=distance;
            i++;
        }

        quicksort(distancias,0,i,usus);

    }

    public static void quicksort(Float A[], int izq, int der, String B[]) {

        Float pivote=A[izq]; // tomamos primer elemento como pivote
        int i=izq;         // i realiza la búsqueda de izquierda a derecha
        int j=der;         // j realiza la búsqueda de derecha a izquierda
        Float aux;

        String pivote2=B[izq];
        String aux2;

        while(i < j){                          // mientras no se crucen las búsquedas
            while(A[i] <= pivote && i < j) i++; // busca elemento mayor que pivote
            while(A[j] > pivote) j--;           // busca elemento menor que pivote
            if (i < j) {                        // si no se han cruzado
                aux= A[i];                      // los intercambia
                A[i]=A[j];
                A[j]=aux;

                aux2=B[i];
                B[i]=B[j];
                B[j]=aux2;
            }
        }

        A[izq]=A[j];      // se coloca el pivote en su lugar de forma que tendremos
        A[j]=pivote;      // los menores a su izquierda y los mayores a su derecha

        B[izq]=B[j];
        B[j]=pivote2;
        if(izq < j-1)
            quicksort(A,izq,j-1,B);          // ordenamos subarray izquierdo
        if(j+1 < der)
            quicksort(A,j+1,der,B);          // ordenamos subarray derecho

    }
}