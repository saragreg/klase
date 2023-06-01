package com.example.tfg_profes;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends AppCompatActivity {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();
    ArrayList<String> locs= new ArrayList<String>();
    Double latUsu,lngUsu;
    String usuario;
    ImageButton lisprofes,perfilbtn,graficabtn;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FileUtils fu = new FileUtils();
        if (!fu.sessionExists(this, "config.txt")) {
            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_menu);

            bottomNavigationView = findViewById(R.id.bottomNavigationView);

            // Configurar el listener para el menú inferior
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Realizar acciones según el elemento seleccionado
                    if (item.getItemId() == R.id.home_bar) {
                        replaceFragment(new FragmentPeticiones());
                    } else if (item.getItemId() == R.id.chat_bar) {
                        replaceFragment(new UserListFragment());
                    } else if (item.getItemId() == R.id.agenda_bar) {
                        replaceFragment(new AgendaFragment());
                }/* else if (item.getItemId() == R.id.settings_bar) {
                    replaceFragment(new SettingsFragment());
                }*/
                    return true;
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment) {
        // Reemplazar el fragmento actual con el fragmento proporcionado
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, fragment)
                .commit();
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
                            String usuarios_conf = workInfo.getOutputData().getString("usu_conf");
                            String loc = workInfo.getOutputData().getString("loc");
                            String lat = workInfo.getOutputData().getString("lat");
                            String lon = workInfo.getOutputData().getString("lng");

                            String[] arrayu = usuarios.split(",");
                            String[] arrayn = nombre.split(",");
                            String[] arrayp = precio.split(",");
                            String[] arraypp = punts.split(",");
                            String[] arrayloc = loc.split(";");
                            String[] arraylat = lat.split(",");
                            String[] arraylon = lon.split(",");

                            calcularDistancias(arraylat,arraylon,arrayu,arrayn,arrayp,arraypp,arrayloc);

                            System.out.println("punt: "+punts);

                            usus = new ArrayList<String>(Arrays.asList(arrayu));
                            noms = new ArrayList<String>(Arrays.asList(arrayn));
                            precios = new ArrayList<String>(Arrays.asList(arrayp));
                            punt = new ArrayList<String>(Arrays.asList(arraypp));
                            locs = new ArrayList<String>(Arrays.asList(arrayloc));

                            Intent intent = new Intent(Menu.this, ListaProfesores.class);
                            intent.putExtra("usuario", usuInt);
                            intent.putStringArrayListExtra("usus",usus);
                            intent.putStringArrayListExtra("noms",noms);
                            intent.putStringArrayListExtra("precios",precios);
                            intent.putStringArrayListExtra("punt",punt);
                            intent.putStringArrayListExtra("locs",locs);

                            startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);


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

    private void obtenerLoc(String usu) {
        Data inputData = new Data.Builder()
                .putString("tipo", "selectLoc")
                .putString("usuario", usu)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String latUsus = workInfo.getOutputData().getString("lat");
                            String lngUsus = workInfo.getOutputData().getString("lng");
                            latUsu=Double.parseDouble(latUsus);
                            lngUsu=Double.parseDouble(lngUsus);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }



}