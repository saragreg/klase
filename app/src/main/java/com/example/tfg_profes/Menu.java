package com.example.tfg_profes;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.Locale;


public class Menu extends AppCompatActivity {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();
    ArrayList<String> locs= new ArrayList<String>();
    private static final String DEFAULT_LANGUAGE = "default";
    private static final String DEFAULT_PERFIL = "nada";
    private SharedPreferences sharedPreferences;
    private String perfil;
    Double latUsu,lngUsu;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //boolean success = getApplicationContext().deleteFile("config.txt");
        FileUtils fu = new FileUtils();
        if (!fu.sessionExists(this, "config.txt")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            FileUtils fileUtils= new FileUtils();
            String user= fileUtils.readFile(this, "config.txt");
            sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
            String idioma = sharedPreferences.getString("idioma", DEFAULT_LANGUAGE);
            perfil = sharedPreferences.getString("perfil", DEFAULT_PERFIL);
            setIdioma(idioma);
            setContentView(R.layout.activity_menu);
            if (perfil.equals("a")) {
                replaceFragment(new FragmentLisProfes());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new
                            String[]{POST_NOTIFICATIONS}, 11);
                }
            }
            cargarEventos();
            cargarDatosUsu();
            bottomNavigationView = findViewById(R.id.bottomNavigationView);

            // Configurar el listener para el menú inferior
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Realizar acciones según el elemento seleccionado
                    if (item.getItemId() == R.id.home_bar) {
                        if (perfil.equals("p")) {
                            replaceFragment(new FragmentPeticiones());
                        }else{
                            replaceFragment(new FragmentLisProfes());
                        }
                    } else if (item.getItemId() == R.id.chat_bar) {
                        replaceFragment(new UserListFragment());
                    } else if (item.getItemId() == R.id.agenda_bar) {
                        replaceFragment(new AgendaFragment());
                    } else if (item.getItemId() == R.id.perfil_bar) {
                        Intent intent = new Intent(Menu.this, Perfil.class);
                        startActivity(intent);
                } else if (item.getItemId() == R.id.settings_bar) {
                    replaceFragment(new SettingsFragment());
                }
                    return true;
                }
            });
        }
    }

    private void cargarFotoPerfil() {
        FileUtils fileUtils=new FileUtils();
        String user = fileUtils.readFile(this, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "cargarFotoPerfil")
                .putString("user",user)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String fotoPer=workInfo.getOutputData().getString("img");
                            //Usuario.usuariosLis.get(0).setImagen(fotoPer);

                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    private void cargarEventos() {
        FileUtils fileUtils=new FileUtils();
        String user = fileUtils.readFile(this, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "cargarEventos")
                .putString("user",user)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);

    }
    private void cargarDatosUsu() {
        FileUtils fileUtils=new FileUtils();
        String user = fileUtils.readFile(this, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "cargarDatosUsu")
                .putString("user",user)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            cargarFotoPerfil();
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);

    }

    private void replaceFragment(Fragment fragment) {
        // Reemplazar el fragmento actual con el fragmento proporcionado
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, fragment)
                .commit();
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