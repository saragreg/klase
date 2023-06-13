package com.example.tfg_profes;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class CambiosLoc extends AppCompatActivity {
    private String lat;
    private String lng;
    private String loc;
    private EditText ciudad,barrio,calle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios_loc);
        ciudad = findViewById(R.id.ciudad);
        barrio = findViewById(R.id.barrio1);
        calle = findViewById(R.id.calle);
        String[] partesLoc = Usuario.usuariosLis.get(0).getLoc().split(",");
        if (partesLoc.length == 2) {
            barrio.setText(partesLoc[0]);
            ciudad.setText(partesLoc[1]);
        } else if (partesLoc.length==3) {
            calle.setText(partesLoc[0]);
            barrio.setText(partesLoc[1]);
            ciudad.setText(partesLoc[2]);
        }
    }
    public void onClickRegLoc2 (View view){
        if (!calle.getText().toString().equals("")){
            loc = calle.getText().toString()+","+barrio.getText().toString() + "," + ciudad.getText().toString();
        }else {
            loc = barrio.getText().toString() + "," + ciudad.getText().toString();
        }
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
            lat = location.latitude + "";
            lng = location.longitude + "";
            regLoc(lat, lng);
        }
    }


    private void obtenerLatLon (String loc){
        new CambiosLoc.GeocodeTask().execute(loc);
    }
    private void regLoc (String lat, String lng){
        FileUtils fileUtils=new FileUtils();
        String usu=fileUtils.readFile(this,"config.txt");
        //meter loc lat y long en el usuario
        Data inputData = new Data.Builder()
                .putString("tipo", "addLocUsu")
                .putString("usuario", usu)
                .putString("loc", loc)
                .putString("lat", lat)
                .putString("lng", lng)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
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
