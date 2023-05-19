package com.example.tfg_profes;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class LisAlumnos extends AppCompatActivity {
    private String profe="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis_alumnos);
        profe=getIntent().getExtras().getString("usuario");
    }

    public void onClickMapa(View v){
        //se obtienen los alumnos pendientes
        ArrayList<LatLng> locationsPend = new ArrayList<LatLng>();
        Data inputData = new Data.Builder()
                .putString("tipo", "pendientes")
                .putString("profe",profe)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String usupend = workInfo.getOutputData().getString("usupend");

                            String[] arrayusu = usupend.split(",");
                            for (int i = 0; i < usupend.length(); i++) {
                                String res=obtenerLoc(arrayusu[i]);
                                String[] localizacion=res.split(",");
                                LatLng loc=new LatLng(parseDouble(localizacion[0]), parseDouble(localizacion[1]));
                                locationsPend.add(loc);
                            }
                            obtenerAceptados(profe,locationsPend);
                            }
                        }
                    });
        WorkManager.getInstance(this).enqueue(otwr);
    }
    private void obtenerAceptados(String profe,ArrayList<LatLng> locationsPend) {
        //se obtienen los alumnos pendientes
        ArrayList<LatLng> locationsAccepted = new ArrayList<LatLng>();
        Data inputData = new Data.Builder()
                .putString("tipo", "aceptados")
                .putString("profe",profe)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String usuacep = workInfo.getOutputData().getString("usuacep");

                            String[] arrayusu = usuacep.split(",");
                            for (int i = 0; i < arrayusu.length; i++) {
                                String res=obtenerLoc(arrayusu[i]);
                                String[] localizacion=res.split(",");
                                LatLng loc=new LatLng(parseDouble(localizacion[0]), parseDouble(localizacion[1]));
                                locationsAccepted.add(loc);
                            }
                            Intent intent = new Intent(LisAlumnos.this, Mapa.class);
                            intent.putParcelableArrayListExtra("pend",locationsPend);
                            intent.putParcelableArrayListExtra("acept",locationsAccepted);
                            startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    private String obtenerLoc(String usu) {
        final String[] respuesta = {""};
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
                            String lat = workInfo.getOutputData().getString("lat");
                            String lng = workInfo.getOutputData().getString("lng");
                            respuesta[0] =lat+","+lng;
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
        return respuesta[0];
    }

}