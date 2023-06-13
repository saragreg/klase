package com.example.tfg_profes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class Login extends AppCompatActivity {
    private String contraRec="";
    String usuIntro;
    private static final String DEFAULT_LANGUAGE = "default";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = sharedPreferences.getString("idioma", DEFAULT_LANGUAGE);
        setIdioma(idioma);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registrar = findViewById(R.id.Registrarse);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)!=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.POST_NOTIFICATIONS}, 11);
            }
        }

        registrar.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), RegistroUSuario.class);
            startActivity(intent);

        });
    }
    public void onclick_login(View v){
        TextView usu = findViewById(R.id.user);
        usuIntro = usu.getText().toString();
        TextView contra = findViewById(R.id.contra);
        String contraIntro = contra.getText().toString();

        if (usuIntro.equals("") || contra.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else {

            TextView error = findViewById(R.id.error);

            Data inputData = new Data.Builder()
                    .putString("tipo", "login")
                    .putString("usuario", usuIntro)
                    .build();
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                contraRec = workInfo.getOutputData().getString("res");
                                String per = workInfo.getOutputData().getString("per");
                                //comprobamos que existe el usuario
                                if (contraRec.equals("mal")) {
                                    //el usuario no existe
                                    Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                                }  else {// Obtiene la contraseña hasheada almacenada en la base de datos
                                    boolean passwordMatches = PasswordHasher.checkPassword(contraIntro, contraRec);
                                    if (passwordMatches) {
                                        // Contraseña correcta
                                        saveSession(usuIntro, per);
                                        //se ha logeado correctamente
                                        Intent intent = new Intent(Login.this, Menu.class);
                                        intent.putExtra("usuario", usuIntro);
                                        startActivity(intent);

                                        Toast.makeText(getApplicationContext(), "Se ha logeado correctamente", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Contraseña incorrecta
                                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
        }

    }

    public void saveSession(String user, String perfil) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(openFileOutput("config.txt",
                            Context.MODE_PRIVATE));
            outputStreamWriter.write(user);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("perfil", perfil); // Ejemplo: Guardar una cadena de texto
        editor.apply(); // Guardar los cambios
    }
    public void setIdioma(String idiomCod){

        Locale locale = new Locale(idiomCod);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, displayMetrics);
    }


}