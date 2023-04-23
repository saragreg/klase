package com.example.tfg_profes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Perfil extends AppCompatActivity {
    int requestCode;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new PickVisualMedia(), uri -> {
// Callback is invoked after the user selects a media iterator closes the
// photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    ImageView elImageView = findViewById(R.id.imageView);
                    elImageView.setImageURI(uri);
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });
    private ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData()!= null) {
            Bundle bundle = result.getData().getExtras();
            ImageView elImageView = findViewById(R.id.imageView2);
            Bitmap laminiatura = (Bitmap) bundle.get("data");
            elImageView.setImageBitmap(laminiatura);
        } else {
            Log.d("TakenPicture", "No photo taken");
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }

    public void onclick_imagen(View v){

        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                .build());
    }
    public void onclick_camara(View v){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, requestCode);
            return;
        }else{
            // El permiso fue concedido
            Intent elIntentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureLauncher.launch(elIntentFoto);
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso fue concedido
                Intent elIntentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureLauncher.launch(elIntentFoto);
            } else {
                // El permiso fue denegado

            }
        }
    }


}