package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {
    private GeocodeTask geocodeTask;
    private String usu;
    String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        usu=getIntent().getExtras().getString("usuario");
        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void obtenerLoc(String usu,GoogleMap googleMap) {
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
                            lat = workInfo.getOutputData().getString("lat");
                            lng = workInfo.getOutputData().getString("lng");
                            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title("mi casa");

                            googleMap.addMarker(markerOptions);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        obtenerLoc(usu,googleMap);
        /*if (geocodeTask != null) {
            geocodeTask.cancel(true);
        }
        geocodeTask = new GeocodeTask(googleMap);
        new GeocodeTask(googleMap).execute("Indautxu, Bilbao, España");
        new GeocodeTask(googleMap).execute("San Ignacio, Bilbao, España");*/
    }

    private class GeocodeTask extends AsyncTask<String, Void, LatLng> {

        private GoogleMap map;

        public GeocodeTask(GoogleMap map) {
            this.map = map;
        }

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
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title("Mi marcador");
            map.addMarker(markerOptions);

            // Mueve la cámara para enfocar la posición de tu marcador
            map.moveCamera(CameraUpdateFactory.newLatLng(location));
            // Centrar la cámara en el marcador y acercar el mapa
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(location)
                    .zoom(60) // ajustar el nivel de zoom según tus necesidades
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

}