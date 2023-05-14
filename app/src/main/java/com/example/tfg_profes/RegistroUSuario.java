package com.example.tfg_profes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;

public class RegistroUSuario extends AppCompatActivity {

    String per="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
    }
    public void onRadioButtonRol (View v) {
// Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

// Check which radio button was clicked
        switch (v.getId()) {
            case R.id.profesor:
                if (checked)
                    per = "p";
                break;
            case R.id.alumno:
                if (checked)
                    per = "a";
                break;
        }
    }

    public void onclick_reg(View v){
        TextView usu = findViewById(R.id.usuario);
        String usuInt = usu.getText().toString();
        System.out.println("usuario introducido: "+usuInt);
        TextView contra = findViewById(R.id.contraseña);
        String contraInt = contra.getText().toString();
        System.out.println("contra introducido: "+contraInt);

        TextView nom = findViewById(R.id.nombre);
        String nomInt = nom.getText().toString();

        TextView tel = findViewById(R.id.editTextPhone);
        String telInt = tel.getText().toString();
        if (usuInt.equals("")|| contra.equals("")|| nom.equals("")|| tel.equals("")||per.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else {
            String hashedPassword = PasswordHasher.hashPassword(contraInt);
// Código de inserción en la base de datos usando la contraseña hasheada

            comprobarUsu(usuInt, hashedPassword,nomInt,telInt);

        }

    }

    private void comprobarUsu(String usuInt, String contraInt, String nomInt, String telInt) {
        Data inputData = new Data.Builder()
                .putString("tipo", "login")
                .putString("usuario",usuInt)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String contraRec = workInfo.getOutputData().getString("res");
                            //comprobamos que no existe el usuario
                            if (contraRec.equals("mal")) {
                                //el usuario no existe
                                addToken(usuInt,contraInt,nomInt,telInt);
                            } else {
                                //la contraseña es incorrecta
                                Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }

    private void addToken(String usuInt, String contraInt, String nomInt, String telInt) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                String token = task.getResult();
                System.out.println("el token:" + token);
                insertarUsu(usuInt,contraInt,nomInt,telInt,token);

            }
        });

    }
    private void insertarUsu(String usuInt, String contraInt, String nomInt, String telInt, String token) {
        Data inputData = new Data.Builder()
                .putString("tipo", "registroUsu")
                .putString("usu", usuInt)
                .putString("contra", contraInt)
                .putString("nom", nomInt)
                .putString("tel", telInt)
                .putString("perfil", per)
                .putString("token",token)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                            Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                            //subirProfe(usuInt);
                            Intent intent = new Intent(RegistroUSuario.this, RegLoc.class);
                            intent.putExtra("usuario", usuInt);
                            intent.putExtra("per",per);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }



    private void subirProfe(String usuInt) {
        Data inputData = new Data.Builder()
                .putString("usuario", usuInt)
                .putString("tipo","insertProf")
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Intent intent = new Intent(RegistroUSuario.this, RegLoc.class);
                            intent.putExtra("usuario", usuInt);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);

    }


}