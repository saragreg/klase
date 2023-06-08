package com.example.tfg_profes;

import static android.content.Context.MODE_PRIVATE;

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

import com.example.tfg_profes.utils.FileUtils;

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
        Button idiomas=view.findViewById(R.id.cambiarIdioma);
        Button cerrarSes=view.findViewById(R.id.cerrarsesion);
        Button borrarcuenta=view.findViewById(R.id.borrarcuenta);

        FileUtils fileUtils = new FileUtils();
        String user = fileUtils.readFile(requireContext(), "config.txt");
        cerrarSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("¿Estás seguro de que quieres cerrar sesión?");
                builder.setMessage("Sentimos mucho que te vayas");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
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
                builder.setTitle("Cambiar idioma");
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
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
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
}