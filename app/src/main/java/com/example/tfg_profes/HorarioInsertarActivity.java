package com.example.tfg_profes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class HorarioInsertarActivity extends AppCompatActivity {
    EditText l1,l2,m1,m2,x1,x2,j1,j2,v1,v2,s1,s2,d1,d2,descr;
    String user,l,m,x,j,vie,s,d,descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_insertar);
        user=getIntent().getExtras().getString("usuario");
        l1=findViewById(R.id.horal1);
        l2=findViewById(R.id.horal2);
        m1=findViewById(R.id.horam1);
        m2=findViewById(R.id.horam2);
        x1=findViewById(R.id.horax1);
        x2=findViewById(R.id.horax2);
        j1=findViewById(R.id.horaj1);
        j2=findViewById(R.id.horaj2);
        v1=findViewById(R.id.horav1);
        v2=findViewById(R.id.horav2);
        s1=findViewById(R.id.horas1);
        s2=findViewById(R.id.horas2);
        d1=findViewById(R.id.horad1);
        d2=findViewById(R.id.horad2);
        descr=findViewById(R.id.descripcionhorario);
        Button horario=findViewById(R.id.regHor);
        horario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (l1.getText().toString().equals("")&&m1.getText().toString().equals("")&&x1.getText().toString().equals("")&&j1.getText().toString().equals("")&&v1.getText().toString().equals("")&&s1.getText().toString().equals("")&&d1.getText().toString().equals("")&&descr.getText().toString().equals("")){
                    Toast.makeText(HorarioInsertarActivity.this, "El horario no puede estar vacío", Toast.LENGTH_SHORT).show();
                }else{
                    l="";m="";x="";j="";vie="";s="";d="";descripcion="";
                    if (!l1.getText().toString().equals("")) {
                        l = l1.getText().toString() + "-" + l2.getText().toString();
                    }
                    if (!m1.getText().toString().equals("")) {
                        m = m1.getText().toString() + "-" + m2.getText().toString();
                    }
                    if (!l1.getText().toString().equals("")) {
                        x = x1.getText().toString() + "-" + x2.getText().toString();
                    }
                    if (!l1.getText().toString().equals("")) {
                        j = j1.getText().toString() + "-" + j2.getText().toString();
                    }
                    if (!l1.getText().toString().equals("")) {
                        vie = v1.getText().toString() + "-" + v2.getText().toString();
                    }
                    if (!l1.getText().toString().equals("")) {
                        s = s1.getText().toString() + "-" + s2.getText().toString();
                    }
                    if (!l1.getText().toString().equals("")) {
                        d = d1.getText().toString() + "-" + d2.getText().toString();
                    }
                    if (!l1.getText().toString().equals("")) {
                        descripcion = descr.getText().toString();
                    }
                    registrarHorario();
                }
            }
        });
    }

    private void registrarHorario() {
        Data inputData = new Data.Builder()
                .putString("tipo", "registrarHorario")
                .putString("usu", user)
                .putString("l", l)
                .putString("m", m)
                .putString("x", x)
                .putString("j", j)
                .putString("v", vie)
                .putString("s", s)
                .putString("d", d)
                .putString("descr", descripcion)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            // Do something in response to button click
                            Intent intent = new Intent(HorarioInsertarActivity.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }

    public void insertarHorario(String profesor){}
}