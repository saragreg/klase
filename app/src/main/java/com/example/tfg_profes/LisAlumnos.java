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
    private String res="";
    Double latProfe,lngProfe;
    private ArrayList<LatLng> locationsAccepted = new ArrayList<LatLng>();
    private ArrayList<LatLng> locationsPend = new ArrayList<LatLng>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis_alumnos);
        profe=getIntent().getExtras().getString("usuario");
    }

    public void onClickMapa(View v){
        obtenerLoc(profe,0,false);
        //se obtienen los alumnos pendientes
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
                            int i=0;
                            while ( i < (arrayusu.length)) {
                                obtenerLoc(arrayusu[i],1,false);
                                i++;
                            }
                            obtenerAceptados(profe,locationsPend);
                            }
                        }
                    });
        WorkManager.getInstance(this).enqueue(otwr);
    }
    private void obtenerAceptados(String profe,ArrayList<LatLng> locationsPend) {
        //se obtienen los alumnos pendientes

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
                            String usuacep = workInfo.getOutputData().getString("usuacept");

                            String[] arrayusu = usuacep.split(",");
                            for (int i = 0; i < arrayusu.length; i++) {
                                if (i==arrayusu.length-1) {
                                    obtenerLoc(arrayusu[i], 2,true);
                                }else{
                                    obtenerLoc(arrayusu[i], 2,false);
                                }
                            }

                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    private void obtenerLoc(String usu,int n, boolean last) {
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
                            LatLng loc=new LatLng(parseDouble(lat), parseDouble(lng));
                            if(n==0){
                                latProfe=Double.parseDouble(lat);
                                lngProfe=Double.parseDouble(lng);
                            } else if (n==1){
                                locationsPend.add(loc);
                            }else{
                                locationsAccepted.add(loc);
                            }
                            if (last) {
                                Intent intent = new Intent(LisAlumnos.this, Mapa.class);
                                intent.putParcelableArrayListExtra("pend", locationsPend);
                                intent.putParcelableArrayListExtra("acept", locationsAccepted);
                                intent.putExtra("latProfe",latProfe);
                                intent.putExtra("lngProfe",lngProfe);
                                startActivity(intent);
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

}