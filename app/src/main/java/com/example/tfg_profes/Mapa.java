package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (geocodeTask != null) {
            geocodeTask.cancel(true);
        }
        geocodeTask = new GeocodeTask(googleMap);
        new GeocodeTask(googleMap).execute("Indautxu, Bilbao, España");
        new GeocodeTask(googleMap).execute("Basurto, Bilbao, Vizcaya");
        new GeocodeTask(googleMap).execute("San Ignacio, Bilbao, España");
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