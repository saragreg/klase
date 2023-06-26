package com.example.tfg_profes;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentResennas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResennas extends Fragment {
    private RecyclerView resennasRecyclerView;
    public FragmentResennas() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentResennas newInstance(String param1, String param2) {
        FragmentResennas fragment = new FragmentResennas();

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
        return inflater.inflate(R.layout.fragment_resennas, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button resenna=view.findViewById(R.id.escribirResenna);
        String profe = getArguments().getString("user");
        String val = getArguments().getString("val");
        FileUtils fileUtils=new FileUtils();
        String idClie=fileUtils.readFile(getContext(),"config.txt");
        AdaptadorResennas eladap = new AdaptadorResennas(Resenna.resennasLis);
        resenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear el diálogo personalizado
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_escribir_resenna, null);
                dialogBuilder.setView(dialogView);
                int grayColor = Color.GRAY;
                int primaryColor;
                primaryColor= getResources().getColor(R.color.theme_color);
                dialogBuilder.setPositiveButton("Aceptar", null);
                dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                // Crear el diálogo y obtener referencias a los elementos
                AlertDialog alertDialog = dialogBuilder.create();
                RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar4);

// Configurar el listener para controlar el estado del botón Aceptar
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        if (rating < 1.0) {
                            // Valoración menor a 1.0: deshabilitar botón Aceptar y cambiar a color gris
                            positiveButton.setEnabled(false);
                            positiveButton.setBackgroundTintList(ColorStateList.valueOf(grayColor));
                        } else {
                            // Valoración de 1.0 o más: habilitar botón Aceptar y cambiar a color naranja
                            positiveButton.setEnabled(true);
                            positiveButton.setBackgroundTintList(ColorStateList.valueOf(primaryColor)); // Color naranja en formato hexadecimal
                        }
                    }
                });
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button acceptButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                RatingBar ratingBar1=dialogView.findViewById(R.id.ratingBar4);
                                EditText comentario=dialogView.findViewById(R.id.editTextTextMultiLine);
                                LocalDateTime fechaHoraActual = LocalDateTime.now();
                                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                String fechaHoraActualFormateada = fechaHoraActual.format(formato);
                                Resenna resenna1=new Resenna(profe,idClie,ratingBar1.getRating(),comentario.getText().toString(),fechaHoraActualFormateada);
                                Resenna.resennasLis.add(resenna1);
                                eladap.notifyDataSetChanged();
                                // Obtener los datos del diálogo
                                Toast.makeText(getContext(), "valoracion: "+ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                                Data inputData = new Data.Builder()
                                        .putString("tipo", "escribir_resenna")
                                        .putString("idProfe", profe)
                                        .putString("idClie", idClie)
                                        .putString("FechaHora",fechaHoraActualFormateada)
                                        .putString("valoracion", String.valueOf(ratingBar1.getRating()))
                                        .putString("comentario", comentario.getText().toString())
                                        .build();
                                OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
                                WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                                        .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                                            @Override
                                            public void onChanged(WorkInfo workInfo) {
                                                if (workInfo != null && workInfo.getState().isFinished()) {

                                                    Toast.makeText(getContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                                                    dialogInterface.dismiss();
                                                    updateValoracion(profe,idClie,val,String.valueOf(ratingBar1.getRating()));

                                                }
                                            }
                                        });
                                WorkManager.getInstance(getContext()).enqueue(otwr);
                            }
                        });
                    }
                });

// Mostrar el diálogo
                alertDialog.show();


            }
        });
        Resenna.resennasLis=new ArrayList<>();

        MutableLiveData<Boolean> workerFinishedLiveData = new MutableLiveData<>();

        Data inputData = new Data.Builder()
                .putString("tipo", "ListaResennas")
                .putString("profe", profe)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (Resenna.resennasLis.size()!=0) {
                                RecyclerView lista = view.findViewById(R.id.resennasRecyclerView);
                                lista.setAdapter(eladap);
                                LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                lista.setLayoutManager(elLayoutLineal);
                            }else{
                                Toast.makeText(getContext(), "No hay reseñas disponibles", Toast.LENGTH_SHORT).show();
                            }

                            workerFinishedLiveData.setValue(true); // Notificar que el worker ha terminado
                        }
                    }
                });

        WorkManager.getInstance(getContext()).enqueue(otwr);
        workerFinishedLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean workerFinished) {
                if (workerFinished) {
                    // Worker ha terminado, se puede devolver la vista
                    // Inflate the layout for this fragment
                }
            }
        });
    }

    private void updateValoracion(String profe, String idClie, String val, String s) {
        // Obtener los datos del diálogo
        Data inputData = new Data.Builder()
                .putString("tipo", "updateValoracion")
                .putString("idProfe", profe)
                .putString("idClie", idClie)
                .putString("val",val)
                .putString("valoracion",s)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {


                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);

    }
}