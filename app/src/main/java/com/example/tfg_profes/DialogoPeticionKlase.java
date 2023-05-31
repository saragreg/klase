package com.example.tfg_profes;

import static com.example.tfg_profes.R.*;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoPeticionKlase extends DialogFragment {
    String nom;
    String asig;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            nom= args.getString("nombre");
            asig= args.getString("asig");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Solicita clase a "+ nom);
        LayoutInflater inflater=getActivity().getLayoutInflater();
        /*View elaspecto= inflater.inflate(R.layout.ellayoutcustomizado,null);
        builder.setView(elaspecto);
        TextView etiqueta= elaspecto.findViewById(R.id.textView);
        etiqueta.setText("¿En serio vas a abandonar la aplicación?");*/
        return builder.create();
    }
}
