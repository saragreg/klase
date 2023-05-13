package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;

public class RegistroAsignaturas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_asignaturas);

        /*CheckBox checkbox = findViewById(R.id.checkbox_primaria);
        ListView listaAsignaturas = findViewById(R.id.lista_asignaturas_primaria);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listaAsignaturas.setVisibility(View.VISIBLE);
                } else {
                    listaAsignaturas.setVisibility(View.GONE);
                }
            }
        });


        ListView listView = findViewById(R.id.lista_asignaturas_primaria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.asignaturas_primaria, android.R.layout.simple_list_item_multiple_choice);
        listView.setAdapter(adapter);

        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        for (int i = 0; i < checkedItems.size(); i++) {
            if (checkedItems.valueAt(i)) {
                int position = checkedItems.keyAt(i);
                String asignatura = adapter.getItem(position).toString();
                // hacer algo con la asignatura seleccionada
            }
        }*/

        CheckBox checkboxeso = findViewById(R.id.checkbox_eso);
        ListView listaAsignaturasEso = findViewById(R.id.lista_asignaturas_eso);

        checkboxeso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listaAsignaturasEso.setVisibility(View.VISIBLE);
                } else {
                    listaAsignaturasEso.setVisibility(View.GONE);
                }
            }
        });


        ListView listVieweso = findViewById(R.id.lista_asignaturas_eso);
        ArrayAdapter<CharSequence> adaptereso = ArrayAdapter.createFromResource(this,
                R.array.asignaturas_eso, android.R.layout.simple_list_item_multiple_choice);
        listVieweso.setAdapter(adaptereso);

        SparseBooleanArray checkedItemseso = listVieweso.getCheckedItemPositions();
        for (int i = 0; i < checkedItemseso.size(); i++) {
            if (checkedItemseso.valueAt(i)) {
                int position = checkedItemseso.keyAt(i);
                String asignatura = adaptereso.getItem(position).toString();
                // hacer algo con la asignatura seleccionada
            }
        }

        CheckBox checkboxbac = findViewById(R.id.checkbox_bac);
        ListView listaAsignaturasBac = findViewById(R.id.lista_asignaturas_bac);

        checkboxbac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listaAsignaturasBac.setVisibility(View.VISIBLE);
                } else {
                    listaAsignaturasBac.setVisibility(View.GONE);
                }
            }
        });


        ListView listViewbac = findViewById(R.id.lista_asignaturas_bac);
        ArrayAdapter<CharSequence> adapterbac = ArrayAdapter.createFromResource(this,
                R.array.asignaturas_bac, android.R.layout.simple_list_item_multiple_choice);
        listViewbac.setAdapter(adapterbac);

        SparseBooleanArray checkedItemsbac = listViewbac.getCheckedItemPositions();
        for (int i = 0; i < checkedItemsbac.size(); i++) {
            if (checkedItemsbac.valueAt(i)) {

                int position = checkedItemsbac.keyAt(i);
                String asignatura = adapterbac.getItem(position).toString();
                // hacer algo con la asignatura seleccionada
            }
        }



    }
}