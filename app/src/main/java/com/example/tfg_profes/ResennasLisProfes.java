package com.example.tfg_profes;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;

import java.util.ArrayList;

public class ResennasLisProfes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resennas_lis_profes);
        FileUtils fileUtils= new FileUtils();
        String profe=fileUtils.readFile(this,"config.txt");
        Resenna.resennasLis=new ArrayList<>();

        MutableLiveData<Boolean> workerFinishedLiveData = new MutableLiveData<>();

        Data inputData = new Data.Builder()
                .putString("tipo", "ListaResennas")
                .putString("profe", profe)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (Resenna.resennasLis.size()!=0) {
                                RecyclerView lista = findViewById(R.id.resennasRecyclerView);
                                AdaptadorResennas eladap = new AdaptadorResennas(Resenna.resennasLis);
                                lista.setAdapter(eladap);
                                LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                lista.setLayoutManager(elLayoutLineal);
                            }else{
                                Toast.makeText(getApplicationContext(), "No hay reseñas disponibles", Toast.LENGTH_SHORT).show();
                            }

                            workerFinishedLiveData.setValue(true); // Notificar que el worker ha terminado
                        }
                    }
                });

        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
        workerFinishedLiveData.observe(this, new Observer<Boolean>() {
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