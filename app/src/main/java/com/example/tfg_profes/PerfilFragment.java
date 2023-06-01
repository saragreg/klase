package com.example.tfg_profes;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {
    private ActivityResultLauncher<Intent> imageCaptureLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
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
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        imageCaptureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");

                        // Rescale the image
                        Bitmap rescaledImage = adjustImageSize(bitmap);
                        //imageView.setImageBitmap(rescaledImage);

                        // Set a name to the photo and save it to internal storage
                        String imageFileName =
                                "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss")
                                        .format(new Date());
                        File directory =
                                getContext().getApplicationContext().getFilesDir();
                        File imageFile = new File(directory, imageFileName);

                        FileOutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                    outputStream);
                            outputStream.close();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        // Transform the photo to a Base64 String and compress it
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        byte[] byteArray = stream.toByteArray();
                        String photo64 = Base64.encodeToString(byteArray, Base64.DEFAULT);


                        // HTTP request to save the photo to database
                        Data inputData = new Data.Builder()
                                .putString("tipo", "addImage")
                                .putString("imagen", photo64)
                                .build();
                        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
                        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                                .observe(getActivity(), new Observer<WorkInfo>() {
                                    @Override
                                    public void onChanged(WorkInfo workInfo) {
                                        if (workInfo != null && workInfo.getState().isFinished()) {

                                        }
                                    }
                                });
                        WorkManager.getInstance(getContext()).enqueue(otwr);
                    }

                });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedImage=view.findViewById(R.id.imageView4);

        camara=view.findViewById(R.id.camara);
        galeria=view.findViewById(R.id.galeria);

        camara=view.findViewById(R.id.camara);
        galeria=view.findViewById(R.id.galeria);
        //storageReference= FirebaseStorage.getInstance().getReference();
        //ponerFotoPerfil(usu);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //askCameraPermissions();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        /*FileUtils fileUtils = new FileUtils();
        String mail = fileUtils.readFile(getContext(), "config.txt");

        etcorreo = view.findViewById(R.id.textView1);
        etcorreo.setText(mail);
        obtenerDes(view, mail);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        takeAPhoto();
                    } else {

                        Toast.makeText(getActivity(),
                                "mal",
                                Toast.LENGTH_SHORT).show();
                    }


                });
        Button photobutton = getView().findViewById(R.id.botonCamara);
        photobutton.setOnClickListener(c -> {
            if (ContextCompat.checkSelfPermission(getContext(), CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(CAMERA);
            } else {
                takeAPhoto();
            }
        });

        recogerFoto(view);
        ImageView guardar = getView().findViewById(R.id.imageViewguardar);
        guardar.setOnClickListener(v -> {
            etcontra = getView().findViewById(R.id.editTextcontra);
            String password = etcontra.getText().toString();
            etdesc = getView().findViewById(R.id.editTextdesc);
            String desc = etdesc.getText().toString();

            String url = "http://" + "192.168.1.116" + ":3005/modificarContra";
            JSONObject requestBody = new JSONObject();

            try {

                requestBody.put("password", password);
                requestBody.put("mail", mail);
                requestBody.put("descripcion", desc);


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                    requestBody, response -> {
                try {
                    //Log.e("titosss", "aaaa"+ response.toString());
                    if (response.get("success").equals(true)) {
                        Toast.makeText(getContext(), "Guardado", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, error -> {
                Log.e("PA", "ERROR", error);
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);

        });*/

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