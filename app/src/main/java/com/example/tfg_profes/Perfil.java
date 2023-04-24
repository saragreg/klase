package com.example.tfg_profes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE=101;
    public static final int CAMERA_REQUEST_CODE=102;
    ImageView selectedImage;
    Button camara,galeria;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        selectedImage=findViewById(R.id.imageView2);
        camara=findViewById(R.id.camara);
        galeria=findViewById(R.id.galeria);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Perfil.this, "has clikado la camara", Toast.LENGTH_SHORT).show();
                askCameraPermissions();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Perfil.this, "has clikado la galeria", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERM_CODE);
            return;
        }else{
            openCamera();
        }
    }

    public void onclick_imagen(View v){

        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                .build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                // El permiso fue denegado
                Toast.makeText(getApplicationContext(), "No da permisos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent cam= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam,CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        //super.onActivityResult();
        if (requestCode == CAMERA_REQUEST_CODE) {

            Bitmap image = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);
        } else {
            Log.d("TakenPicture", "No photo taken");
        }
    }


}