package com.example.tfg_profes;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {
    private GeocodeTask geocodeTask;
    private String usu;
    String lat,lng;
    private ArrayList<LatLng> pend=new ArrayList<>();
    private ArrayList<LatLng> acept=new ArrayList<>();
    private Double latProfe,lngProfe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        latProfe=getIntent().getExtras().getDouble("latProfe");
        lngProfe=getIntent().getExtras().getDouble("lngProfe");
        pend=getIntent().getParcelableArrayListExtra("pend");
        acept=getIntent().getParcelableArrayListExtra("acept");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        LatLng locProfe =new LatLng(latProfe,lngProfe);
        MarkerOptions marker = new MarkerOptions()
                .position(locProfe)
                .title("Este soy yo")
                .icon(BitmapDescriptorFactory.defaultMarker());
        googleMap.addMarker(marker);
        Iterator<LatLng> iterator = pend.iterator();
        while (iterator.hasNext()) {
            LatLng location = iterator.next();
            // Obtener un desplazamiento aleatorio en metros (por ejemplo, 10 metros)
            double displacement = 0.0009; // Aproximadamente 10 metros en latitud/longitud (ajustar según tus necesidades)

            // Generar un desplazamiento aleatorio en latitud y longitud
            double randomLat = (Math.random() * 2 - 1) * displacement;
            double randomLng = (Math.random() * 2 - 1) * displacement;

            // Calcular nuevas coordenadas aleatorias para mover el marcador
            double newLat = location.latitude + randomLat;
            double newLng = location.longitude + randomLng;

            LatLng newLocation = new LatLng(newLat, newLng);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(newLocation)
                    .title("pendiente")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            googleMap.addMarker(markerOptions);
        }
        Iterator<LatLng> iterator2 = acept.iterator();
        while (iterator2.hasNext()) {
            LatLng location = iterator2.next();

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title("aceptado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            googleMap.addMarker(markerOptions);
        }
        //ajustar camara a la ubicacion del profesor
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(locProfe)
                .zoom(12) // ajustar el nivel de zoom según tus necesidades
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
                    .title("Mi marcador")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            map.addMarker(markerOptions);

            // Mueve la cámara para enfocar la posición de tu marcador
            map.moveCamera(CameraUpdateFactory.newLatLng(location));
            // Centrar la cámara en el marcador y acercar el mapa
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(location)
                    .zoom(15) // ajustar el nivel de zoom según tus necesidades
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

}