package com.example.tfg_profes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosPerfilFragment extends Fragment {
    public static final int CAMERA_PERM_CODE=101;
    public static final int CAMERA_REQUEST_CODE=102;
    public static final int GALLERY_REQUEST_CODE=105;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=103;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=104;

    ImageView selectedImage;
    Button camara,galeria,comenzar;
    String currentPhotoPath;
    StorageReference storageReference;
    String usu;

    public DatosPerfilFragment() {
        // Required empty public constructor
    }

    public static DatosPerfilFragment newInstance(String param1, String param2) {
        DatosPerfilFragment fragment = new DatosPerfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_perfil, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedImage=view.findViewById(R.id.imageView4);
        String fotoperfil=Imagenes.perfilusuario.getImagen();
        if (!fotoperfil.equals("")) {
            if (fotoperfil.length()<100){
                Uri imageUri = Uri.parse(fotoperfil);
                selectedImage.setImageURI(imageUri);
            }else {
                String image64 = fotoperfil;
                byte[] b = Base64.decode(image64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
                        b.length);
                Bitmap rescaledImage = adjustImageSize(bitmap);
                selectedImage.setImageBitmap(rescaledImage);
            }
        }
        FileUtils fileUtils=new FileUtils();
        usu=fileUtils.readFile(getContext(), "config.txt");
        selectedImage=view.findViewById(R.id.imageView4);

        camara=view.findViewById(R.id.camara);
        galeria=view.findViewById(R.id.galeria);
        storageReference= FirebaseStorage.getInstance().getReference();
        //ponerFotoPerfil(usu);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askCameraPermissions();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
    }
    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[] { android.Manifest.permission.CAMERA }, CAMERA_PERM_CODE);
            return;
        }else{
            dispatchTakePictureIntent();
        }

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // El permiso no ha sido otorgado, solicítalo
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // El permiso no ha sido otorgado, solicítalo
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();

            } else {
                // El permiso fue denegado
                Toast.makeText(getContext(), "No da permisos camara", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido otorgado
            } else {
                // El permiso ha sido denegado
                Toast.makeText(getContext(), "No da permisos lectura", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido otorgado
            } else {
                // El permiso ha sido denegado
                Toast.makeText(getContext(), "No da permisos escritura", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                selectedImage.setImageBitmap(image);
                // Transform the photo to a Base64 String and compress it
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                String photo64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Imagenes.perfilusuario.setImagen(photo64);
                subirUriImagen();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri contentUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentUri);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "IMG_" + timeStamp + ".jpg";
                    File storageDir = getActivity().getFilesDir(); // O usa getCacheDir() si prefieres almacenar en la memoria caché
                    File imageFile = new File(storageDir, imageFileName);
                    try (OutputStream outputStream = new FileOutputStream(imageFile)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream); // Ajusta la calidad de compresión según tus necesidades
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String imagePath = imageFile.getAbsolutePath();
                    Uri imageUri = Uri.parse(imagePath);
                    selectedImage.setImageURI(imageUri);
                    Imagenes.perfilusuario.setImagen(imagePath);
                    subirUriImagen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void subirUriImagen() {

        Data inputData = new Data.Builder()
                .putString("usuario", usu)
                .putString("tipo","insertarImagen")
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);

    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,CAMERA_REQUEST_CODE);

    }

    // Adjust the image size to be bigger than the one taken
    private Bitmap adjustImageSize(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int length = bitmap.getHeight();

        int newSize = 800;
        float scaleWidth = ((float) newSize / width);
        float scaleLength = ((float) newSize / length);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleLength);

        return Bitmap.createBitmap(bitmap, 0, 0, width, length, matrix, true);
    }

}