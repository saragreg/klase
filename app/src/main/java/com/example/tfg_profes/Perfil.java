package com.example.tfg_profes;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;
import com.example.tfg_profes.utils.ImageUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Perfil extends AppCompatActivity {
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
    String path;
    String path_mod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        selectedImage=findViewById(R.id.imageView4);
        String image64 =Usuario.getUsuariosLis().get(0).getImagen();
        byte[] b = Base64.decode(image64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        //Bitmap rescaledImage = adjustImageSize(bitmap);
        selectedImage.setImageBitmap(bitmap);
        /*String image64 =obtenerImagenEnString(Usuario.getUsuariosLis().get(0).getImagen());
        if (!image64.equals("")) {
            byte[] b = Base64.decode(image64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            //Bitmap rescaledImage = adjustImageSize(bitmap);
            selectedImage.setImageBitmap(bitmap);
        }*/
        /*ImageUtils iu = new ImageUtils();
        if (!iu.sessionExists(this, "image.txt")) {
            ImageUtils imageUtils= new ImageUtils();
            String imagen= imageUtils.readImage(this, "image.txt");
            // The photo exists
            String image64 = imagen;
            byte[] b = Base64.decode(image64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
                    b.length);
            Bitmap rescaledImage = adjustImageSize(bitmap);
            selectedImage.setImageBitmap(rescaledImage);
        }*/
        //usu=getIntent().getExtras().getString("usuario");
        FileUtils fileUtils=new FileUtils();
        usu=fileUtils.readFile(this, "config.txt");
        selectedImage=findViewById(R.id.imageView4);

        camara=findViewById(R.id.camara);
        galeria=findViewById(R.id.galeria);
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

        if (savedInstanceState != null) {
            String ruta =savedInstanceState.getString("imagen");
            // Decodificar la cadena Base64 a un array de bytes
            bitmap = BitmapFactory.decodeFile(ruta);

            // Mostrar el Bitmap en un ImageView
            selectedImage.setImageBitmap(bitmap);
        }


    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERM_CODE);
            return;
        }else{
            dispatchTakePictureIntent();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // El permiso no ha sido otorgado, solicítalo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // El permiso no ha sido otorgado, solicítalo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            return;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();

            } else {
                // El permiso fue denegado
                Toast.makeText(getApplicationContext(), "No da permisos camara", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido otorgado
            } else {
                // El permiso ha sido denegado
                Toast.makeText(getApplicationContext(), "No da permisos lectura", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido otorgado
            } else {
                // El permiso ha sido denegado
                Toast.makeText(getApplicationContext(), "No da permisos escritura", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == this.RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                selectedImage.setImageBitmap(image);
                // Transform the photo to a Base64 String and compress it
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                String photo64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                // Set a name to the photo and save it to internal storage
                String imageFileName =
                        "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss")
                                .format(new Date());
                File directory =
                        getApplicationContext().getFilesDir();
                File imageFile = new File(directory, imageFileName);
                // Guardar el bitmap en el archivo temporal
                try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                    //image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.write(photo64.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Obtener la ruta del archivo temporal
                String filePath = imageFile.getAbsolutePath();
                System.out.println(filePath);
                subirUriImagen(filePath);

            /*FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 50,
                        outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                OutputStreamWriter outputStreamWriter =
                        new OutputStreamWriter(openFileOutput("image.txt",
                                Context.MODE_PRIVATE));
                outputStreamWriter.write(photo64);
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e);
            }*/
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if(resultCode == this.RESULT_OK){
                Uri contentUri=data.getData();
                /*String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName= "JPEG_"+timeStamp+"."+getFileExt(contentUri);
                Log.d("tag","gallery url:"+ imageFileName);*/
                selectedImage.setImageURI(contentUri);

                //uploadimage
                //uploadImageToFirebase(imageFileName,contentUri);
            }
        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri){
        StorageReference image = storageReference.child("images/"+ name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri){
                        Log.d("tag","uploaded url:"+ uri.toString());
                        Picasso.get().load(uri).into(selectedImage);
                        subirUriImagen(uri.toString());
                    }
                });
                Toast.makeText(getApplicationContext(), "imagen subida", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(getApplicationContext(), "No se sube", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void subirUriImagen(String uri) {

        Data inputData = new Data.Builder()
                .putString("usuario", usu)
                .putString("uri",uri)
                .putString("tipo","insertarImagen")
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
    //images/image.jpg

    private String getFileExt(Uri contentUri){
        ContentResolver c = getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile()throws IOException{
        //se crea
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileNAme= "JPEG_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image= File.createTempFile( imageFileNAme,".jpg",storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent(){
        Intent takePictureIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,CAMERA_REQUEST_CODE);
        /*if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try{
                photoFile=createImageFile();
            }catch (IOException ex){

            }
            if (photoFile != null){
                Uri photoURI= FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,CAMERA_REQUEST_CODE);
            }
        }*/

    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Obtener el drawable del ImageView
        Drawable drawable = selectedImage.getDrawable();

        // Convertir el drawable a un Bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Crear un archivo temporal
        File tempFile = null;
        try {
            tempFile = File.createTempFile("image", ".jpg", getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Guardar el bitmap en el archivo temporal
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Obtener la ruta del archivo temporal
        String filePath = tempFile.getAbsolutePath();
        System.out.println(filePath);

        outState.putString("imagen",filePath);

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
    public static String obtenerImagenEnString(String filePath) {
        try {
            // Leer los bytes del archivo
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));

            // Decodificar los bytes en forma de cadena Base64
            String imageString = Base64.encodeToString(bytes, Base64.DEFAULT);

            return imageString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}