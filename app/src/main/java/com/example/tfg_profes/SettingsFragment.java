package com.example.tfg_profes;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Double.parseDouble;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String DEFAULT_LANGUAGE = "default";
    private String idioma;
    private int indiceSeleccionado = 0;
    private SharedPreferences sharedPreferences;
    private static final String DEFAULT_PERFIL = "nada";
    String user;
    Double latProfe,lngProfe;
    private ArrayList<LatLng> locationsAccepted = new ArrayList<LatLng>();
    private ArrayList<LatLng> locationsPend = new ArrayList<LatLng>();
    String[] arrayusuAcept,arrayusuPend;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        idioma = sharedPreferences.getString("idioma", DEFAULT_LANGUAGE);
        setIdioma(idioma);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String perfil = sharedPreferences.getString("perfil", DEFAULT_PERFIL);
        Button idiomas=view.findViewById(R.id.cambiarIdioma);
        Button cerrarSes=view.findViewById(R.id.cerrarsesion);
        Button datosPer=view.findViewById(R.id.cambiarDatosPer);
        Button resennas=view.findViewById(R.id.verreseñas);
        Button mapa=view.findViewById(R.id.vermapa);
        Button graficas=view.findViewById(R.id.vergraficas);
        Button cambiarLoc=view.findViewById(R.id.cambiarDire);
        Button borrarcuenta=view.findViewById(R.id.borrarcuenta);
        if (perfil.equals("a")){
            graficas.setVisibility(View.GONE);
            resennas.setVisibility(View.GONE);
        }

        FileUtils fileUtils = new FileUtils();
        user = fileUtils.readFile(requireContext(), "config.txt");
        cerrarSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.cerrarses);
                builder.setMessage(R.string.marchar);
                builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean success = getContext().deleteFile("config.txt");
                        if (success) {

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
        borrarcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.eliminarcuenta);
                builder.setMessage(R.string.norecuperarinfo);
                builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean success = getContext().deleteFile("config.txt");
                        if (success) {

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
        idiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.cambiaridioma);
                final String[] opciones = {"Castellano", "Euskara", "English"};
                final String[] opcionesCod = {"es", "eu", "en"};
                // Establecer el índice del RadioButton por defecto (0 para "Castellano")
                if (idioma.equals("es")){indiceSeleccionado=0;} else if (idioma.equals("eu")) {indiceSeleccionado=1;}else{indiceSeleccionado=2;}

                builder.setSingleChoiceItems(opciones, indiceSeleccionado, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones a realizar cuando se selecciona un idioma
                        indiceSeleccionado=i;
                    }
                });
                builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharedPreferences = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("idioma", opcionesCod[indiceSeleccionado]); // Ejemplo: Guardar una cadena de texto
                        editor.apply(); // Guardar los
                        SettingsFragment settingsFragment=new SettingsFragment();
                    }
                });
                builder.show();
            }
        });
        resennas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResennasLisProfes.class);
                startActivity(intent);
            }
        });
        datosPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CambioDatos.class);
                startActivity(intent);
            }
        });
        cambiarLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CambiosLoc.class);
                startActivity(intent);
            }
        });
        graficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Graph_demanda_asig_annos.class);
                startActivity(intent);
            }
        });
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerLoc(user,0,false);
                if (perfil.equals("p")) {
                    //se obtienen los alumnos pendientes
                    Data inputData = new Data.Builder()
                            .putString("tipo", "pendientes")
                            .putString("profe", user)
                            .build();
                    OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
                    WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                            .observe(getActivity(), new Observer<WorkInfo>() {
                                @Override
                                public void onChanged(WorkInfo workInfo) {
                                    if (workInfo != null && workInfo.getState().isFinished()) {
                                        String usupend = workInfo.getOutputData().getString("usupend");
                                        if (!usupend.equals("")) {
                                            arrayusuPend = usupend.split(",");
                                            int i = 0;
                                            while (i < (arrayusuPend.length)) {
                                                obtenerLoc(arrayusuPend[i], 1, false);
                                                i++;
                                            }
                                        }
                                        obtenerAceptados(user, locationsPend);
                                    }
                                }
                            });
                    WorkManager.getInstance(getContext()).enqueue(otwr);
                }else{
                    Intent intent = new Intent(getActivity(), Mapa.class);
                    intent.putParcelableArrayListExtra("pend", locationsPend);
                    intent.putParcelableArrayListExtra("acept", locationsAccepted);
                    intent.putExtra("nomAcept",arrayusuAcept);
                    intent.putExtra("nomPend",arrayusuPend);
                    intent.putExtra("latProfe",latProfe);
                    intent.putExtra("lngProfe",lngProfe);
                    startActivity(intent);
                }
            }
        });
    }
    public void setIdioma(String idiomCod){

        Locale locale = new Locale(idiomCod);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void obtenerAceptados(String profe,ArrayList<LatLng> locationsPend) {
        //se obtienen los alumnos pendientes
        Data inputData = new Data.Builder()
                .putString("tipo", "aceptados")
                .putString("profe",profe)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String usuacep = workInfo.getOutputData().getString("usuacept");
                            if (!usuacep.equals("")) {
                                arrayusuAcept = usuacep.split(",");
                                for (int i = 0; i < arrayusuAcept.length; i++) {
                                    if (i == arrayusuAcept.length - 1) {
                                        obtenerLoc(arrayusuAcept[i], 2, true);
                                    } else {
                                        obtenerLoc(arrayusuAcept[i], 2, false);
                                    }
                                }
                            }else{
                                Intent intent = new Intent(getActivity(), Mapa.class);
                                intent.putParcelableArrayListExtra("pend", locationsPend);
                                intent.putParcelableArrayListExtra("acept", locationsAccepted);
                                intent.putExtra("nomAcept",arrayusuAcept);
                                intent.putExtra("nomPend",arrayusuPend);
                                intent.putExtra("latProfe",latProfe);
                                intent.putExtra("lngProfe",lngProfe);
                                startActivity(intent);
                            }
                        }
                    }
                });
        WorkManager.getInstance(getContext()).enqueue(otwr);
    }
    private void obtenerLoc(String usu,int n, boolean last) {
        Data inputData = new Data.Builder()
                .putString("tipo", "selectLoc")
                .putString("usuario", usu)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String lat = workInfo.getOutputData().getString("lat");
                            String lng = workInfo.getOutputData().getString("lng");
                            LatLng loc=new LatLng(parseDouble(lat), parseDouble(lng));
                            if(n==0){
                                latProfe= parseDouble(lat);
                                lngProfe= parseDouble(lng);
                            } else if (n==1){
                                locationsPend.add(loc);
                            }else{
                                locationsAccepted.add(loc);
                            }
                            if (last) {
                                Intent intent = new Intent(getActivity(), Mapa.class);
                                intent.putParcelableArrayListExtra("pend", locationsPend);
                                intent.putParcelableArrayListExtra("acept", locationsAccepted);
                                intent.putExtra("nomAcept",arrayusuAcept);
                                intent.putExtra("nomPend",arrayusuPend);
                                intent.putExtra("latProfe",latProfe);
                                intent.putExtra("lngProfe",lngProfe);
                                startActivity(intent);
                            }
                            }
                        }
                    });
        WorkManager.getInstance(getContext()).enqueue(otwr);
                }
}