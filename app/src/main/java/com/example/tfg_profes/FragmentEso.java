package com.example.tfg_profes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private String h;
    private  EditText h1,h2,h3,h4,h5,h6;

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
        h1=view.findViewById(R.id.hijo1);
        h2=view.findViewById(R.id.hijo2);
        h3=view.findViewById(R.id.hijo3);
        h4=view.findViewById(R.id.hijo4);
        h5=view.findViewById(R.id.hijo5);
        h6=view.findViewById(R.id.hijo6);

        Spinner numHijos= (Spinner) view.findViewById(R.id.hijos);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(),R.array.hijos, android.R.layout.simple_spinner_item);
        numHijos.setAdapter(adapter);
        if (!Alumno.alumno.getIdCliente().equals("")){
            numHijos.setSelection(Alumno.alumno.getNumHijos()-1);
            int num=Alumno.alumno.getNumHijos();
            String[] nombres=Alumno.alumno.getNombres().split(",");
            h1.setVisibility(View.GONE);
            h2.setVisibility(View.GONE);
            h3.setVisibility(View.GONE);
            h4.setVisibility(View.GONE);
            h5.setVisibility(View.GONE);
            h6.setVisibility(View.GONE);
            if (num==1) {
                h1.setVisibility(View.VISIBLE);
                h1.setText(nombres[0]);
            } else if (num==2) {
                h1.setVisibility(View.VISIBLE);
                h2.setVisibility(View.VISIBLE);
                h1.setText(nombres[0]);
                h2.setText(nombres[1]);
            } else if (num==3) {
                h1.setVisibility(View.VISIBLE);
                h2.setVisibility(View.VISIBLE);
                h3.setVisibility(View.VISIBLE);
                h1.setText(nombres[0]);
                h2.setText(nombres[1]);
                h3.setText(nombres[2]);
            } else if (num==4) {
                h1.setVisibility(View.VISIBLE);
                h2.setVisibility(View.VISIBLE);
                h3.setVisibility(View.VISIBLE);
                h4.setVisibility(View.VISIBLE);
                h1.setText(nombres[0]);
                h2.setText(nombres[1]);
                h3.setText(nombres[2]);
                h4.setText(nombres[3]);
            } else if (num==5) {
                h1.setVisibility(View.VISIBLE);
                h2.setVisibility(View.VISIBLE);
                h3.setVisibility(View.VISIBLE);
                h4.setVisibility(View.VISIBLE);
                h5.setVisibility(View.VISIBLE);
                h1.setText(nombres[0]);
                h2.setText(nombres[1]);
                h3.setText(nombres[2]);
                h4.setText(nombres[3]);
                h5.setText(nombres[4]);
            } else if (num==6) {
                h1.setVisibility(View.VISIBLE);
                h2.setVisibility(View.VISIBLE);
                h3.setVisibility(View.VISIBLE);
                h4.setVisibility(View.VISIBLE);
                h5.setVisibility(View.VISIBLE);
                h6.setVisibility(View.VISIBLE);
                h1.setText(nombres[0]);
                h2.setText(nombres[1]);
                h3.setText(nombres[2]);
                h4.setText(nombres[3]);
                h5.setText(nombres[4]);
                h6.setText(nombres[5]);
            }
        }

        numHijos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                h = adapterView.getItemAtPosition(i).toString();
                h1.setVisibility(View.GONE);
                h2.setVisibility(View.GONE);
                h3.setVisibility(View.GONE);
                h4.setVisibility(View.GONE);
                h5.setVisibility(View.GONE);
                h6.setVisibility(View.GONE);
                if (h.equals("1")) {
                    h1.setVisibility(View.VISIBLE);
                } else if (h.equals("2")) {
                    h1.setVisibility(View.VISIBLE);
                    h2.setVisibility(View.VISIBLE);
                } else if (h.equals("3")) {
                    h1.setVisibility(View.VISIBLE);
                    h2.setVisibility(View.VISIBLE);
                    h3.setVisibility(View.VISIBLE);
                } else if (h.equals("4")) {
                    h1.setVisibility(View.VISIBLE);
                    h2.setVisibility(View.VISIBLE);
                    h3.setVisibility(View.VISIBLE);
                    h4.setVisibility(View.VISIBLE);
                } else if (h.equals("5")) {
                    h1.setVisibility(View.VISIBLE);
                    h2.setVisibility(View.VISIBLE);
                    h3.setVisibility(View.VISIBLE);
                    h4.setVisibility(View.VISIBLE);
                    h5.setVisibility(View.VISIBLE);
                } else if (h.equals("6")) {
                    h1.setVisibility(View.VISIBLE);
                    h2.setVisibility(View.VISIBLE);
                    h3.setVisibility(View.VISIBLE);
                    h4.setVisibility(View.VISIBLE);
                    h5.setVisibility(View.VISIBLE);
                    h6.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public String[] getHijos(){
        String[] hijos = new String[6];
        hijos[0]=h1.getText().toString();
        hijos[1]=h2.getText().toString();
        hijos[2]=h3.getText().toString();
        hijos[3]=h4.getText().toString();
        hijos[4]=h5.getText().toString();
        hijos[5]=h6.getText().toString();
        return hijos;
    }
    public int getNumHijos(){
        return Integer.parseInt(h);
    }
}
