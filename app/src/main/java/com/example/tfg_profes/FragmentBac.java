package com.example.tfg_profes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBac#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBac extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText exp,prec;
    private CheckBox prim,eso,bac,cas,eus,ing;

    private ImageButton m,l,e,i,n,s,t,q,a;

    private boolean mx=false,lx=false,ex=false,ix=false,nx=false,sx=false,tx=false,qx=false,ax=false;



    public FragmentBac() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBac.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBac newInstance(String param1, String param2) {
        FragmentBac fragment = new FragmentBac();
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
    public String getExp(){
        return exp.getText().toString();
    }
    public Float getPrecio(){
        return Float.parseFloat(prec.getText().toString());
    }
    public String getCurso(){
        String curso="";
        if(prim.isChecked()){
            curso="prim,";
        }
        if(eso.isChecked()){
            curso=curso+"eso,";
        }
        if(bac.isChecked()){
            curso=curso+"bac";
        }
        return curso;
    }
    public String getIdiom(){
        String curso="";
        if(cas.isChecked()){
            curso="cas,";
        }
        if(eus.isChecked()){
            curso=curso+"eus,";
        }
        if(ing.isChecked()){
            curso=curso+"ing";
        }
        return curso;
    }
    public String getAsig(){
        String asig="";

        if(mx){
            asig="mate";
        }
        if(lx){
            asig=asig+"len,";
        }
        if(ex){
            asig=asig+"eus,";
        }
        if(ix){
            asig="ing,";
        }
        if(qx){
            asig=asig+"fiki,";
        }
        if(nx){
            asig=asig+"nat,";
        }
        if(sx){
            asig="soc,";
        }
        if(tx){
            asig=asig+"tic,";
        }
        if(ax){
            asig=asig+"apo";
        }
        return asig;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bac, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prim = view.findViewById(R.id.checkbox_prim);
        eso = view.findViewById(R.id.checkbox_eso);
        bac = view.findViewById(R.id.checkbox_bac);

        prec = view.findViewById(R.id.precioreg);

        m = view.findViewById(R.id.mate);
        l = view.findViewById(R.id.lengua);
        e = view.findViewById(R.id.eusk);
        i = view.findViewById(R.id.ing);
        q = view.findViewById(R.id.fiki);
        n = view.findViewById(R.id.natura);
        s = view.findViewById(R.id.gizar);
        t = view.findViewById(R.id.tic);
        a = view.findViewById(R.id.apoyo);

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mx) {
                    mx=false;
                }else{
                    mx=true;
                }
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lx) {
                    lx=false;
                }else{
                    lx=true;
                }
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ex) {
                    ex=false;
                }else{
                    ex=true;
                }
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ix) {
                    ix=false;
                }else{
                    ix=true;
                }
            }
        });
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qx) {
                    qx=false;
                }else{
                    qx=true;
                }
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nx) {
                    nx=false;
                }else{
                    nx=true;
                }
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sx) {
                    sx=false;
                }else{
                    sx=true;
                }
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tx) {
                    tx=false;
                }else{
                    tx=true;
                }
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ax) {
                    ax=false;
                }else{
                    ax=true;
                }
            }
        });

        cas = view.findViewById(R.id.checkbox_cas);
        eus = view.findViewById(R.id.checkbox_eusk);
        ing = view.findViewById(R.id.checkbox_ing);

        exp=view.findViewById(R.id.expNum);
    }
    /*public void onClickm(View v){
        if (mx) {
            mx=false;
        }else{
            mx=true;
        }
    }
    public void onClickl(View v){
        if (lx) {
            lx=false;
        }else{
            lx=true;
        }
    }
    public void onClicke(View v){
        if (ex) {
            ex=false;
        }else{
            ex=true;
        }
    }
    public void onClicki(View v){
        if (ix) {
            ix=false;
        }else{
            ix=true;
        }
    }
    public void onClickq(View v){
        if (qx) {
            qx=false;
        }else{
            qx=true;
        }
    }
    public void onClickn(View v){
        if (nx) {
            nx=false;
        }else{
            nx=true;
        }
    }
    public void onClicks(View v){
        if (sx) {
            sx=false;
        }else{
            sx=true;
        }
    }
    public void onClickt(View v){
        if (tx) {
            tx=false;
        }else{
            tx=true;
        }
    }
    public void onClicka(View v){
        if (ax) {
            ax=false;
        }else{
            ax=true;
        }
    }*/

}