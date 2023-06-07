package com.example.tfg_profes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private Spinner idiomas;
    private String idiomSel;
    private boolean idiomacargado = false;
    private static final String DEFAULT_LANGUAGE = "default";
    private SharedPreferences sharedPreferences;
    Activity a = this;
    private String idioma ="es";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getPreferences(MODE_PRIVATE);

        idioma = sharedPreferences.getString("idioma", DEFAULT_LANGUAGE);
        setIdioma(idioma);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idiomas=findViewById(R.id.spinnerIdioms);


        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.idiomas, android.R.layout.simple_spinner_item);
        idiomas.setAdapter(adapter);
        idiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(136,0,21));
                //((TextView) adapterView.getChildAt(0)).setTextSize(35);
                idiomSel = adapterView.getItemAtPosition(i).toString();

                if (idiomSel.equals("Castellano")){
                    saveLanguage("es");
                } else if (idiomSel.equals("Euskara")) {
                    saveLanguage("eu");
                }else if (idiomSel.equals("English")){
                    saveLanguage("en");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void btn_login(View v){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    public void btn_reg(View v){
        Intent intent = new Intent(MainActivity.this, RegistroUSuario.class);
        startActivity(intent);
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
    private void saveLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idioma", language); // Ejemplo: Guardar una cadena de texto
        editor.apply(); // Guardar los cambios
        finish();
        startActivity(getIntent());
    }

    /*protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mySpinner", idiomas.getSelectedItemPosition());
        outState.putString("idiomaActual", idiomSel);
        // do this for each or your Spinner
    }*/

}
