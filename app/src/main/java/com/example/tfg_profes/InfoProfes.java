package com.example.tfg_profes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class InfoProfes extends AppCompatActivity {
    String usuario;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profes);
        //obtenemos info del usuario
        usuario = getIntent().getExtras().getString("usus");
        String precio = getIntent().getExtras().getString("precio");
        String asig = getIntent().getExtras().getString("asig");
        String cursos = getIntent().getExtras().getString("cursos");
        String idiomas = getIntent().getExtras().getString("idiomas");
        String exp = getIntent().getExtras().getString("exp");
        String punts = getIntent().getExtras().getString("punt");

        TextView n=findViewById(R.id.nombreusu);
        RatingBar p=findViewById(R.id.ratingBar2);
        n.setText(usuario);
        p.setRating(Float.parseFloat(punts));

        System.out.println("curso que se envia al fragment: "+cursos);

        FragmentProfes fragInfo= (FragmentProfes) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView6);
        Bundle bundle=new Bundle();
        bundle.putString("asig",asig);
        bundle.putString("curso",cursos);
        bundle.putString("idioma",idiomas);
        bundle.putString("exp",exp);
        fragInfo.setArguments(bundle);

    }

    public void klase(View v) {
        // Crear el diálogo personalizado
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_registro_peticiones, null);
        dialogBuilder.setView(dialogView);

        // Configurar el diálogo
        dialogBuilder.setTitle("Solicitar una Klase");
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener los datos del diálogo
                EditText editTextDate = dialogView.findViewById(R.id.editTextDate);
                EditText editTextTime = dialogView.findViewById(R.id.editTextTime2);
                String fecha = editTextDate.getText().toString();
                String hora = editTextTime.getText().toString();
                // Realizar acciones con los datos recolectados
                Toast.makeText(InfoProfes.this, "Fecha: " + fecha + " Hora: " + hora, Toast.LENGTH_SHORT).show();
                enviarnotificacion(usuario);
            }
        });
        // Mostrar el diálogo
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

    }
    public void onRadioButtonKlase (View v) {
// Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

// Check which radio button was clicked
        switch (v.getId()) {
            case R.id.profesor:
                if (checked)
                    tipo = "p";
                break;
            case R.id.alumno:
                if (checked)
                    tipo = "a";
                break;
        }
    }

    private void enviarnotificacion(String usuIntro) {
        Data inputData = new Data.Builder()
                .putString("usuario",usuIntro)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDmensajes.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
}