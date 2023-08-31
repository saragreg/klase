package com.example.tfg_profes;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHorario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHorario extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout l,m,x,j,v,s,d;
    private EditText l1,l2,m1,m2,x1,x2,j1,j2,v1,v2,s1,s2,d1,d2;
    private TextView des;
    private static final String DEFAULT_PERFIL = "nada";
    private SharedPreferences sharedPreferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHorario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHorario.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHorario newInstance(String param1, String param2) {
        FragmentHorario fragment = new FragmentHorario();
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
        return inflater.inflate(R.layout.fragment_horario, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String perfil = sharedPreferences.getString("perfil", DEFAULT_PERFIL);
        l = view.findViewById(R.id.linearLayout7);
        m = view.findViewById(R.id.linearLayout8);
        x = view.findViewById(R.id.linearLayout9);
        j = view.findViewById(R.id.linearLayout10);
        v = view.findViewById(R.id.linearLayout11);
        s = view.findViewById(R.id.linearLayout12);
        d = view.findViewById(R.id.linearLayout13);
        des = view.findViewById(R.id.textView15);
        l1=view.findViewById(R.id.horal1);
        l2=view.findViewById(R.id.horal2);
        m1=view.findViewById(R.id.horam1);
        m2=view.findViewById(R.id.horam2);
        x1=view.findViewById(R.id.horax1);
        x2=view.findViewById(R.id.horax2);
        j1=view.findViewById(R.id.horaj1);
        j2=view.findViewById(R.id.horaj2);
        v1=view.findViewById(R.id.horav1);
        v2=view.findViewById(R.id.horav2);
        s1=view.findViewById(R.id.horas1);
        s2=view.findViewById(R.id.horas2);
        d1=view.findViewById(R.id.horad1);
        d2=view.findViewById(R.id.horad2);
        if (perfil.equals("a")){
            l1.setClickable(false);
            l2.setClickable(false);
            m1.setClickable(false);
            m2.setClickable(false);
            x1.setClickable(false);
            x2.setClickable(false);
            j1.setClickable(false);
            j2.setClickable(false);
            v1.setClickable(false);
            v2.setClickable(false);
            s1.setClickable(false);
            s2.setClickable(false);
            d1.setClickable(false);
            d2.setClickable(false);
        }
        if (!Horario.horario.getLun().equals("")){
            l.setVisibility(View.VISIBLE);
            String[] lun=Horario.horario.getLun().split("-");
            l1.setText(lun[0]);
            l2.setText(lun[1]);
        }else{
            l.setVisibility(View.GONE);
        }
        if (!Horario.horario.getMar().equals("")){
            m.setVisibility(View.VISIBLE);
            String[] mar=Horario.horario.getMar().split("-");
            m1.setText(mar[0]);
            m2.setText(mar[1]);
        }else{
            m.setVisibility(View.GONE);
        }
        if (!Horario.horario.getMie().equals("")){
            x.setVisibility(View.VISIBLE);
            String[] mie=Horario.horario.getMie().split("-");
            x1.setText(mie[0]);
            x2.setText(mie[1]);
        }else{
            x.setVisibility(View.GONE);
        }
        if (!Horario.horario.getJue().equals("")){
            j.setVisibility(View.VISIBLE);
            String[] jue=Horario.horario.getJue().split("-");
            j1.setText(jue[0]);
            j2.setText(jue[1]);
        }else{
            j.setVisibility(View.GONE);
        }
        if (!Horario.horario.getVie().equals("")){
            v.setVisibility(View.VISIBLE);
            String[] vie=Horario.horario.getVie().split("-");
            v1.setText(vie[0]);
            v2.setText(vie[1]);
        }else{
            v.setVisibility(View.GONE);
        }
        if (!Horario.horario.getSab().equals("")){
            s.setVisibility(View.VISIBLE);
            String[] sab=Horario.horario.getSab().split("-");
            s1.setText(sab[0]);
            s2.setText(sab[1]);
        }else{
            s.setVisibility(View.GONE);
        }
        if (!Horario.horario.getDom().equals("")){
            d.setVisibility(View.VISIBLE);
            String[] dom=Horario.horario.getDom().split("-");
            d1.setText(dom[0]);
            d2.setText(dom[1]);
        }else{
            d.setVisibility(View.GONE);
        }
        if (!Horario.horario.getDescr().equals("")){
            des.setText(Horario.horario.getDescr());
        }else{
            des.setText("");
        }

    }
}