package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class InfoProfes extends AppCompatActivity {
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profes);
        //obtenemos info del usuario
        usuario = getIntent().getExtras().getString("usus");
        String precio = getIntent().getExtras().getString("precio");
        String asig = getIntent().getExtras().getString("asig");
        String cursos = getIntent().getExtras().getString("cursos");
        String idiomas = getIntent().getExtras().getString("idiomas");
        String exp = getIntent().getExtras().getString("exp");
        String punts = getIntent().getExtras().getString("punt");

        TextView n=findViewById(R.id.nombreusu);
        RatingBar p=findViewById(R.id.ratingBar2);
        n.setText(usuario);
        p.setRating(Float.parseFloat(punts));

        System.out.println("curso que se envia al fragment: "+cursos);

        FragmentProfes fragInfo= (FragmentProfes) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView6);
        Bundle bundle=new Bundle();
        bundle.putString("asig",asig);
        bundle.putString("curso",cursos);
        bundle.putString("idioma",idiomas);
        bundle.putString("exp",exp);
        fragInfo.setArguments(bundle);

    }

    public void notificacion(View v) {
        enviarnotificacion(usuario);
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
}