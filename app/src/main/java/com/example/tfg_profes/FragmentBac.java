package com.example.tfg_profes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
            curso="Primaria,";
        }
        if(eso.isChecked()){
            curso=curso+"ESO,";
        }
        if(bac.isChecked()){
            curso=curso+"Bachiller";
        }
        return curso;
    }
    public String getIdiom(){
        String curso="";
        if(cas.isChecked()){
            curso="castellano,";
        }
        if(eus.isChecked()){
            curso=curso+"euskera,";
        }
        if(ing.isChecked()){
            curso=curso+"inglés";
        }
        return curso;
    }
    public String getAsig(){
        String asig="";

        if(mx){
            asig=asig+"Matemáticas,";
        }
        if(lx){
            asig=asig+"Lengua,";
        }
        if(ex){
            asig=asig+"Euskera,";
        }
        if(ix){
            asig=asig+"Inglés,";
        }
        if(qx){
            asig=asig+"Física y Química,";
        }
        if(nx){
            asig=asig+"Ciencias de la naturaleza,";
        }
        if(sx){
            asig=asig+"Ciencias sociales,";
        }
        if(tx){
            asig=asig+"Tics,";
        }
        if(ax){
            asig=asig+"Apoyo escolar,";
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
        String cursos=Profesor.profesor.getCursos();
        if (!cursos.equals("")){
            String[] curso=cursos.split(",");
            for (int j = 0; j < curso.length; j++) {
                if (curso[j].equals("Primaria")){
                    prim.setChecked(true);
                } else if (curso[j].equals("ESO")) {
                    eso.setChecked(true);
                } else if (curso[j].equals("Bachiller")) {
                    bac.setChecked(true);
                }
            }
        }

        prec = view.findViewById(R.id.precioreg);
        String precio=Profesor.profesor.getPrecio();
        if (!precio.equals("")){
            prec.setText(precio);
        }

        m = view.findViewById(R.id.mate);
        l = view.findViewById(R.id.lengua);
        e = view.findViewById(R.id.eusk);
        i = view.findViewById(R.id.ing);
        q = view.findViewById(R.id.fiki);
        n = view.findViewById(R.id.natura);
        s = view.findViewById(R.id.gizar);
        t = view.findViewById(R.id.tic);
        a = view.findViewById(R.id.apoyo);
        String asignaturas=Profesor.profesor.getAsig();
        if (!asignaturas.equals("")){
            String[] asig=asignaturas.split(",");
            for (int j = 0; j < asig.length; j++) {
                if (asig[j].equals("Matemáticas")){
                    m.setImageResource(R.drawable.btn_asig_pulsada);
                    mx=true;
                } else if (asig[j].equals("Lengua")) {
                    l.setImageResource(R.drawable.btn_asig_pulsada);
                    lx=true;
                } else if (asig[j].equals("Euskera")) {
                    e.setImageResource(R.drawable.btn_asig_pulsada);
                    ex=true;
                }else if (asig[j].equals("Inglés")) {
                    i.setImageResource(R.drawable.btn_asig_pulsada);
                    ix=true;
                } else if (asig[j].equals("Física y Química")) {
                    q.setImageResource(R.drawable.btn_asig_pulsada);
                    qx=true;
                }else if (asig[j].equals("Ciencias de la naturaleza")) {
                    n.setImageResource(R.drawable.btn_asig_pulsada);
                    nx=true;
                }else if (asig[j].equals("Ciencias sociales")) {
                    s.setImageResource(R.drawable.btn_asig_pulsada);
                    sx=true;
                } else if (asig[j].equals("Tics")) {
                    t.setImageResource(R.drawable.btn_asig_pulsada);
                    tx=true;
                }else if (asig[j].equals("Apoyo escolar")) {
                    a.setImageResource(R.drawable.btn_asig_pulsada);
                    ax=true;
                }

            }
        }

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mx) {
                    m.setImageResource(R.drawable.btn_asig);
                    mx=false;
                }else{
                    m.setImageResource(R.drawable.btn_asig_pulsada);
                    mx=true;
                }
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lx) {
                    l.setImageResource(R.drawable.btn_asig);
                    lx=false;
                }else{
                    l.setImageResource(R.drawable.btn_asig_pulsada);
                    lx=true;
                }
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ex) {
                    e.setImageResource(R.drawable.btn_asig);
                    ex=false;
                }else{
                    e.setImageResource(R.drawable.btn_asig_pulsada);
                    ex=true;
                }
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ix) {
                    i.setImageResource(R.drawable.btn_asig);
                    ix=false;
                }else{
                    i.setImageResource(R.drawable.btn_asig_pulsada);
                    ix=true;
                }
            }
        });
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qx) {
                    q.setImageResource(R.drawable.btn_asig);
                    qx=false;
                }else{
                    q.setImageResource(R.drawable.btn_asig_pulsada);
                    qx=true;
                }
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nx) {
                    n.setImageResource(R.drawable.btn_asig);
                    nx=false;
                }else{
                    n.setImageResource(R.drawable.btn_asig_pulsada);
                    nx=true;
                }
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sx) {
                    s.setImageResource(R.drawable.btn_asig);
                    sx=false;
                }else{
                    s.setImageResource(R.drawable.btn_asig_pulsada);
                    sx=true;
                }
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tx) {
                    t.setImageResource(R.drawable.btn_asig);
                    tx=false;
                }else{
                    t.setImageResource(R.drawable.btn_asig_pulsada);
                    tx=true;
                }
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ax) {
                    a.setImageResource(R.drawable.btn_asig);
                    ax=false;
                }else{
                    a.setImageResource(R.drawable.btn_asig_pulsada);
                    ax=true;
                }
            }
        });

        cas = view.findViewById(R.id.checkbox_cas);
        eus = view.findViewById(R.id.checkbox_eusk);
        ing = view.findViewById(R.id.checkbox_ing);
        String idiomas=Profesor.profesor.getIdiomas();
        if (!idiomas.equals("")){
            String[] idiom=idiomas.split(",");
            for (int j = 0; j < idiom.length; j++) {
                if (idiom[j].equals("Castellano")||idiom[j].equals("castellano")){
                    cas.setChecked(true);
                } else if (idiom[j].equals("Euskera")||idiom[j].equals("euskera")) {
                    eus.setChecked(true);
                } else if (idiom[j].equals("Inglés")||idiom[j].equals("inglés")) {
                    ing.setChecked(true);
                }
            }
        }

        exp=view.findViewById(R.id.expNum);
        String experiencia=Profesor.profesor.getExperiencia();
        if (!experiencia.equals("")){
            exp.setText(experiencia);
        }
    }

}