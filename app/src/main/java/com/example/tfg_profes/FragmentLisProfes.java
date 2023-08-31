package com.example.tfg_profes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_profes.utils.FileUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLisProfes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLisProfes extends Fragment {
    Double latUsu,lngUsu;
    RecyclerView lisprofes;
    AdaptadorProfesLista eladap;
    boolean[] textViewClicked = new boolean[9];
    TextView asig1,asig2,asig3,asig4,asig5,asig6,asig7,asig8,asig9;
    public FragmentLisProfes() {
        // Required empty public constructor
    }

    public static FragmentLisProfes newInstance() {
        FragmentLisProfes fragment = new FragmentLisProfes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lis_profes, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FileUtils fileUtils= new FileUtils();
        String user= fileUtils.readFile(getContext(), "config.txt");
        lisprofes = view.findViewById(R.id.listaprofesores);
        Button filtrar = view.findViewById(R.id.filtrar);
        if (Profesor.lisProfes.size()!=0) {
            eladap=new AdaptadorProfesLista(requireContext(),Profesor.lisProfes,getViewLifecycleOwner());
            lisprofes.setAdapter(eladap);
            LinearLayoutManager elLayoutLineal = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            lisprofes.setLayoutManager(elLayoutLineal);
        }else{
            Toast.makeText(getContext(), "No hay tutores disponibles", Toast.LENGTH_SHORT).show();
        }
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> asignaturas = new ArrayList<>();
                // Crear el diálogo personalizado
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_filtrado, null);
                dialogBuilder.setView(dialogView);
                asig1 = dialogView.findViewById(R.id.asig1f);
                asig2 = dialogView.findViewById(R.id.asig2f);
                asig3 = dialogView.findViewById(R.id.asig3f);
                asig4 = dialogView.findViewById(R.id.asig4f);
                asig5 = dialogView.findViewById(R.id.asig5f);
                asig6 = dialogView.findViewById(R.id.asig6f);
                asig7 = dialogView.findViewById(R.id.asig7f);
                asig8 = dialogView.findViewById(R.id.asig8f);
                asig9 = dialogView.findViewById(R.id.asig9f);
                // Inicializar todos los elementos del array a false
                for (int i = 0; i < 9; i++) {
                    textViewClicked[i] = false;
                }
                asig1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[0]==false) {
                            textViewClicked[0] = true;
                            asig1.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[0] = false;
                            asig1.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });

                asig2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[1]==false) {
                            textViewClicked[1] = true;
                            asig2.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[1] = false;
                            asig2.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });
                asig3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[2]==false) {
                            textViewClicked[2] = true;
                            asig3.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[2] = false;
                            asig3.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });

                asig4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[3]==false) {
                            textViewClicked[3] = true;
                            asig4.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[3] = false;
                            asig4.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });
                asig5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[4]==false) {
                            textViewClicked[4] = true;
                            asig5.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[4] = false;
                            asig5.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });

                asig6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[5]==false) {
                            textViewClicked[5] = true;
                            asig6.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[5] = false;
                            asig6.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });
                asig7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[6]==false) {
                            textViewClicked[6] = true;
                            asig7.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[6] = false;
                            asig7.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });

                asig8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[7]==false) {
                            textViewClicked[7] = true;
                            asig8.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[7] = false;
                            asig8.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });
                asig9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textViewClicked[8]==false) {
                            textViewClicked[8] = true;
                            asig9.setBackgroundResource(R.drawable.btn_asig_pulsada);
                            // Realiza otras acciones necesarias cuando se pulsa el TextView
                        }else{
                            textViewClicked[8] = false;
                            asig9.setBackgroundResource(R.drawable.btn_asig);
                        }
                    }
                });
                // Configurar el diálogo
                dialogBuilder.setTitle(R.string.filtrarAsig);
                dialogBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Crea una lista con las asignaturas seleccionadas
                        ArrayList<String> asignaturas=new ArrayList<>();
                        // Obtener los datos del diálogo
                        for (int i = 0; i < 9; i++) {
                            if (textViewClicked[i] == true){
                                if (i==0){
                                    asignaturas.add(asig1.getText().toString());
                                } else if (i==1) {
                                    asignaturas.add(asig2.getText().toString());
                                }else if (i==2) {
                                    asignaturas.add(asig3.getText().toString());
                                }else if (i==3) {
                                    asignaturas.add(asig4.getText().toString());
                                }else if (i==4) {
                                    asignaturas.add(asig5.getText().toString());
                                }else if (i==5) {
                                    asignaturas.add(asig6.getText().toString());
                                }else if (i==6) {
                                    asignaturas.add(asig7.getText().toString());
                                }else if (i==7) {
                                    asignaturas.add(asig8.getText().toString());
                                }else if (i==8) {
                                    asignaturas.add(asig9.getText().toString());
                                }
                            }
                        }
                        eladap.filtrarPorAsignaturas(asignaturas);
                        dialog.dismiss();

                    }
                });
                // Configurar el botón "Cancelar"
                dialogBuilder.setNegativeButton("Eliminar Filtros", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones a realizar cuando se hace clic en el botón "Cancelar"
                        eladap.restaurarListaCompleta();
                    }
                });

                AlertDialog dialog = dialogBuilder.create();

                dialog.show();
            }
        });
    }

}