package com.example.tfg_profes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfes newInstance(String param1, String param2) {
        FragmentProfes fragment = new FragmentProfes();
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
        return inflater.inflate(R.layout.fragment_profes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView a=view.findViewById(R.id.asigProfe);
        String asignaturas=getArguments().getString("asig");
        a.setText(asignaturas);

        TextView c=view.findViewById(R.id.cur);
        String curso=getArguments().getString("curso");
        c.setText(curso);

        TextView i=view.findViewById(R.id.idio);
        String idiom=getArguments().getString("idioma");
        i.setText(idiom);

        TextView e=view.findViewById(R.id.años);
        String exp=getArguments().getString("exp");

        e.setText(exp);


    }
}

