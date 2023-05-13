package com.example.tfg_profes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEso extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentEso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEso.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEso newInstance(String param1, String param2) {
        FragmentEso fragment = new FragmentEso();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eso, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckBox checkboxeso = view.findViewById(R.id.checkbox_bac);
    ListView listaAsignaturasEso = view.findViewById(R.id.lista_asignaturas_eso);

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


        ListView listVieweso = view.findViewById(R.id.lista_asignaturas_eso);
        ArrayAdapter<CharSequence> adaptereso = ArrayAdapter.createFromResource(getContext(), R.array.asignaturas_eso, android.R.layout.simple_list_item_multiple_choice);
        listVieweso.setAdapter(adaptereso);

        SparseBooleanArray checkedItemseso = listVieweso.getCheckedItemPositions();
        for (int i = 0; i < checkedItemseso.size(); i++) {
        if (checkedItemseso.valueAt(i)) {
        int position = checkedItemseso.keyAt(i);
        String asignatura = adaptereso.getItem(position).toString();
        // hacer algo con la asignatura seleccionada
        }
        }


    }
}

