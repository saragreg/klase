package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class RegLoc extends AppCompatActivity {
    private String lat;
    private String lng;
    private String usu;
    private String per;
    private String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_loc);
        usu=getIntent().getExtras().getString("usuario");
        per=getIntent().getExtras().getString("per");
        if (per.equals("a")){
            FragmentEso fragAlu= new FragmentEso();
            Bundle bundle=new Bundle();
            fragAlu.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView,fragAlu)
                    .commit();
        }
    }

    public void onClickRegLoc(View view) {
        EditText ciudad=findViewById(R.id.ciudad);
        EditText barrio=findViewById(R.id.barrio);
        loc=barrio.getText().toString()+","+ciudad.getText().toString();
        obtenerLatLon(loc);
    }


    private class GeocodeTask extends AsyncTask<String, Void, LatLng> {

        @Override
        protected LatLng doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(params[0], 1);
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(LatLng location) {
            lat=location.latitude+"";
            lng=location.longitude+"";
            regLoc(lat,lng);
        }
    }



    private void obtenerLatLon(String loc) {
        new GeocodeTask().execute(loc);
    }
    private void regLoc(String lat, String lng) {
        //meter loc lat y long en el usuario
        Data inputData = new Data.Builder()
                .putString("tipo", "addLocUsu")
                .putString("usuario",usu)
                .putString("loc",loc)
                .putString("lat",lat)
                .putString("lng",lng)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (per.equals("a")){
                                //registrarAlu(latitude,longitude);
                            }else{
                                registrarProfe();
                            }

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
    private void registrarProfe() {
        FragmentBac fragProf= (FragmentBac) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        String exp=fragProf.getExp();
        String cursos=fragProf.getCurso();
        String idiom=fragProf.getIdiom();
        String asig = fragProf.getAsig();
        Float prec =fragProf.getPrecio();
        insertarProf(exp,cursos,idiom,asig,prec);
    }

    private void insertarProf(String exp,String cursos,String idiom,String asig,Float prec) {
        System.out.println("exp:"+exp+"curs:"+cursos+"idiom:"+idiom+"asig:"+asig+"prec:"+prec+"");
        Data inputData = new Data.Builder()
                .putString("tipo", "addProf")
                .putString("usuario",usu)
                .putString("exp",exp)
                .putString("cursos",cursos)
                .putString("idiom",idiom)
                .putString("asig",asig)
                .putFloat("prec",prec)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                            Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                            if (per.equals("p")) {
                                Intent intent = new Intent(RegLoc.this, LisAlumnos.class);
                                intent.putExtra("usuario", usu);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(RegLoc.this, Menu.class);
                                intent.putExtra("usuario", usu);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                        } else {

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
}