package com.example.tfg_profes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InfoProfes extends AppCompatActivity {
    String usuario;
    int tipo=2;
    TextView asig1,asig2,asig3,asig4,asig5,asig6,asig7,asig8,asig9,l,m,x,j,vi,s,d;
    boolean[] textViewClicked = new boolean[9];
    boolean[] textViewClickedDias = new boolean[7];
    String asig;
    LinearLayout diassemana;
    int numCaracteres1=0,numCaracteres2=0,numCaracteres3=0,numasig=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profes);
        //obtenemos info del usuario
        usuario = getIntent().getExtras().getString("usu");
        String precio = getIntent().getExtras().getString("precio");
        asig = getIntent().getExtras().getString("asig");
        String cursos = getIntent().getExtras().getString("cursos");
        String idiomas = getIntent().getExtras().getString("idiomas");
        String exp = getIntent().getExtras().getString("exp");
        String punts = getIntent().getExtras().getString("punt");

        TextView n=findViewById(R.id.nombreusu);
        RatingBar p=findViewById(R.id.ratingBar2);
        p.setIsIndicator(true);
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
        String[] asignaturas=asig.split(",");
        // Crear el diálogo personalizado
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_registro_peticiones, null);
        dialogBuilder.setView(dialogView);
        asig1 = dialogView.findViewById(R.id.asig1);
        asig2 = dialogView.findViewById(R.id.asig2);
        asig3 = dialogView.findViewById(R.id.asig3);
        asig4 = dialogView.findViewById(R.id.asig4);
        asig5 = dialogView.findViewById(R.id.asig5);
        asig6 = dialogView.findViewById(R.id.asig6);
        asig7 = dialogView.findViewById(R.id.asig7);
        asig8 = dialogView.findViewById(R.id.asig8);
        asig9 = dialogView.findViewById(R.id.asig9);
        if(9<=asignaturas.length){
            asig1.setVisibility(View.VISIBLE);
            asig2.setVisibility(View.VISIBLE);
            asig3.setVisibility(View.VISIBLE);
            asig4.setVisibility(View.VISIBLE);
            asig5.setVisibility(View.VISIBLE);
            asig6.setVisibility(View.VISIBLE);
            asig7.setVisibility(View.VISIBLE);
            asig8.setVisibility(View.VISIBLE);
            asig9.setVisibility(View.VISIBLE);
        }else {
            numasig = 0;
            while (numasig < asignaturas.length) {
                if (1 > numasig && numasig<asignaturas.length) {
                    asig1.setText(asignaturas[numasig]);
                    asig1.setVisibility(View.VISIBLE);
                    numCaracteres1 = numCaracteres1 + asignaturas[numasig].length();
                    numasig = numasig + 1;
                }
                if (2 > numasig && numasig<asignaturas.length) {
                    asig2.setText(asignaturas[numasig]);
                    asig2.setVisibility(View.VISIBLE);
                    numCaracteres1 = numCaracteres1 + asignaturas[numasig].length();
                    numasig = numasig + 1;
                }
                if (3 > numasig && numasig<asignaturas.length &&numCaracteres1+asignaturas[numasig].length() < 41) {
                    asig3.setText(asignaturas[numasig]);
                    asig3.setVisibility(View.VISIBLE);
                    numCaracteres1 = numCaracteres1 + asignaturas[numasig].length();
                    numasig = numasig + 1;
                }
                if (4 > numasig && numasig<asignaturas.length && numCaracteres1+asignaturas[numasig].length() < 41) {
                    asig4.setText(asignaturas[numasig]);
                    asig4.setVisibility(View.VISIBLE);
                    numasig = numasig + 1;
                }
                if (5 > numasig&& numasig<asignaturas.length) {
                    asig5.setText(asignaturas[numasig]);
                    asig5.setVisibility(View.VISIBLE);
                    numCaracteres2 = numCaracteres2 + asignaturas[numasig].length();
                    numasig = numasig + 1;
                }
                if (6 > numasig && numasig<asignaturas.length && numCaracteres2+asignaturas[numasig].length() < 41) {
                    asig6.setText(asignaturas[numasig]);
                    asig6.setVisibility(View.VISIBLE);
                    numasig = numasig + 1;
                }
                if (7 > numasig&& numasig<asignaturas.length) {
                    asig7.setText(asignaturas[numasig]);
                    asig7.setVisibility(View.VISIBLE);
                    numCaracteres3 = numCaracteres3 + asignaturas[numasig].length();
                    numasig = numasig + 1;
                }
                if (8 > numasig&& numasig<asignaturas.length) {
                    asig8.setText(asignaturas[numasig]);
                    asig8.setVisibility(View.VISIBLE);
                    numCaracteres3 = numCaracteres3 + asignaturas[numasig].length();
                    numasig = numasig + 1;
                }
            }
        }

        diassemana=dialogView.findViewById(R.id.diassemana);
        // Inicializar todos los elementos del array a false
        for (int i = 0; i < textViewClicked.length; i++) {
            textViewClicked[i] = false;
        }


        l = dialogView.findViewById(R.id.l);
        m = dialogView.findViewById(R.id.m);
        x = dialogView.findViewById(R.id.x);
        j = dialogView.findViewById(R.id.j);
        vi = dialogView.findViewById(R.id.v);
        s = dialogView.findViewById(R.id.s);
        d = dialogView.findViewById(R.id.d);
        asig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[0]==false) {
                    textViewClicked[0] = true;
                    asig1.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[0] = false;
                    asig1.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        asig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[1]==false) {
                    textViewClicked[1] = true;
                    asig2.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[1] = false;
                    asig2.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });
        asig3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[2]==false) {
                    textViewClicked[2] = true;
                    asig3.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[2] = false;
                    asig3.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        asig4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[3]==false) {
                    textViewClicked[3] = true;
                    asig4.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[3] = false;
                    asig4.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });
        asig5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[4]==false) {
                    textViewClicked[4] = true;
                    asig5.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[4] = false;
                    asig5.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        asig6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[5]==false) {
                    textViewClicked[5] = true;
                    asig6.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[5] = false;
                    asig6.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });
        asig7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[6]==false) {
                    textViewClicked[6] = true;
                    asig7.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[6] = false;
                    asig7.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        asig8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[7]==false) {
                    textViewClicked[7] = true;
                    asig8.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[7] = false;
                    asig8.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });
        asig9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[8]==false) {
                    textViewClicked[8] = true;
                    asig9.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[8] = false;
                    asig9.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[0]==false) {
                    textViewClickedDias[0] = true;
                    l.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[0] = false;
                    l.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[1]==false) {
                    textViewClickedDias[1] = true;
                    m.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[1] = false;
                    m.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[2]==false) {
                    textViewClickedDias[2] = true;
                    x.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[2] = false;
                    x.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[3]==false) {
                    textViewClickedDias[3] = true;
                    j.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[3] = false;
                    j.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[4]==false) {
                    textViewClickedDias[4] = true;
                    vi.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[4] = false;
                    vi.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[5]==false) {
                    textViewClickedDias[5] = true;
                    s.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[5] = false;
                    s.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClickedDias[6]==false) {
                    textViewClickedDias[6] = true;
                    d.setBackgroundResource(R.drawable.redondo_blanco_borde_naranja);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClickedDias[6] = false;
                    d.setBackgroundResource(R.drawable.redondo_blanco_10);
                }
            }
        });

        asig4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[3]==false) {
                    textViewClicked[3] = true;
                    asig4.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[3] = false;
                    asig4.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });
        asig5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[4]==false) {
                    textViewClicked[4] = true;
                    asig5.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[4] = false;
                    asig5.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        asig6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[5]==false) {
                    textViewClicked[5] = true;
                    asig6.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[5] = false;
                    asig6.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });
        asig7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewClicked[6]==false) {
                    textViewClicked[6] = true;
                    asig7.setBackgroundResource(R.drawable.btn_asig_pulsada);
                    // Realiza otras acciones necesarias cuando se pulsa el TextView
                }else{
                    textViewClicked[6] = false;
                    asig7.setBackgroundResource(R.drawable.btn_asig);
                }
            }
        });

        EditText editTextDate = dialogView.findViewById(R.id.editTextDate);

        // Establece el click listener para el EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén la fecha actual del calendario
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crea el DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(InfoProfes.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Aquí puedes obtener la fecha seleccionada y realizar acciones con ella
                        // Por ejemplo, puedes mostrarla en el EditText
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                }, year, month, day);

                // Muestra el DatePickerDialog
                datePickerDialog.show();
            }
        });
        // Obtén una referencia al EditText
        EditText editTextTime = dialogView.findViewById(R.id.editTextTime2);

// Establece el click listener para el EditText
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén la hora actual del calendario
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Crea el TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(InfoProfes.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Aquí puedes obtener la hora seleccionada y realizar acciones con ella
                        // Por ejemplo, puedes mostrarla en el EditText en formato "00:00"
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        editTextTime.setText(selectedTime);
                    }
                }, hour, minute, true);

                // Muestra el TimePickerDialog
                timePickerDialog.show();
            }
        });

        // Configurar el diálogo
        dialogBuilder.setTitle("Solicitar una Klase");
        dialogBuilder.setPositiveButton("Aceptar",null);
        // Configurar el botón "Cancelar"
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Acciones a realizar cuando se hace clic en el botón "Cancelar"
                dialogInterface.dismiss(); // Cerrar el diálogo
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCancelable(false);//para evitar que se cierre si se pulsa fuera del diálogo

// Obtener el botón "Aceptar" del diálogo y configurar el OnClickListener personalizado
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button acceptButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Obtener los datos del diálogo
                        String fecha = editTextDate.getText().toString();
                        String hora = editTextTime.getText().toString();
                        EditText dur = dialogView.findViewById(R.id.duracion);
                        String duracion = dur.getText().toString();
                        if (tipo == 2 || fecha.equals("") || duracion.equals("") || (!textViewClicked[0] && !textViewClicked[1] && !textViewClicked[2] && !textViewClicked[3] && !textViewClicked[4] && !textViewClicked[5] && !textViewClicked[6] && !textViewClicked[7] && !textViewClicked[8])) {
                            Toast.makeText(InfoProfes.this, "Todos los campos obligatorios deben estar completos", Toast.LENGTH_SHORT).show();
                            // No hacer nada más si los campos no están completos
                        } else {
                            // Realizar acciones con los datos recolectados
                            Toast.makeText(InfoProfes.this, "Fecha: " + fecha + " Hora: " + hora, Toast.LENGTH_SHORT).show();
                            enviarnotificacion();
                            addSolicitud(fecha,hora,duracion);
                            dialog.dismiss(); // Cerrar el diálogo después de realizar acciones
                        }
                    }
                });
            }
        });

        // Mostrar el diálogo

        dialog.show();

    }

    private void addSolicitud(String fecha, String hora, String dur) {
        FileUtils fileUtils =new FileUtils();
        String cliente=fileUtils.readFile(this,"config.txt");
        // Obtener la fecha y hora actuales
        Date currentDate = new Date();
        // Definir el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Obtener la fecha y hora formateadas
        String formattedDateTime = dateFormat.format(currentDate);
        String dias="";
        if(tipo==0){
            for (int i = 0; i < textViewClickedDias.length; i++) {
                if (textViewClickedDias[i]){
                    switch (i) {
                        case 0:
                            dias="L ";
                            break;
                        case 1:
                            dias=dias+"M ";
                            break;
                        case 2:
                            dias=dias+"X ";
                            break;
                        case 3:
                            dias=dias+"J ";
                            break;
                        case 4:
                            dias=dias+"V ";
                            break;
                        case 5:
                            dias=dias+"S ";
                            break;
                        case 6:
                            dias=dias+"D";
                            break;
                    }
                }
            }
        }
        String asignaturas="";
        for (int i = 0; i < textViewClicked.length; i++) {
            if (textViewClicked[i]){
                switch (i) {
                    case 0:
                        asignaturas=asig1.getText().toString()+",";
                        break;
                    case 1:
                        asignaturas=asignaturas+asig2.getText().toString()+",";
                        break;
                    case 2:
                        asignaturas=asignaturas+asig3.getText().toString()+",";
                        break;
                    case 3:
                        asignaturas=asignaturas+asig4.getText().toString()+",";
                        break;
                    case 4:
                        asignaturas=asignaturas+asig5.getText().toString()+",";
                        break;
                    case 5:
                        asignaturas=asignaturas+asig6.getText().toString()+",";
                        break;
                    case 6:
                        asignaturas=asignaturas+asig7.getText().toString()+",";
                        break;
                    case 7:
                        asignaturas=asignaturas+asig8.getText().toString()+",";
                        break;
                    case 8:
                        asignaturas=asignaturas+asig9.getText().toString();
                        break;
                }
            }
        }
        if(!textViewClicked[8]){
            asignaturas=asignaturas.substring(0, asignaturas.length() - 1);
        }
        //usuario,cliente,fechaActual,fecha,hora,dur,tipo,textViewClickedDias,textViewClicked
        Data inputData = new Data.Builder()
                .putString("tipo", "addSolicitud")
                .putString("idProfe", usuario)
                .putString("idCli", cliente)
                .putString("fechaActual", formattedDateTime)
                .putString("fecha", fecha)
                .putString("hora", hora)
                .putString("dias",dias)
                .putString("puntual",String.valueOf(tipo))
                .putString("asig",asignaturas)
                .putString("dur",dur+"h")
                .build();
       OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InfoProfes.this);
                            builder.setTitle("Solicitud completada");
                            builder.setMessage("La solicitud se ha realizado con éxito");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }

    public void onRadioButtonKlase (View v) {
// Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

// Check which radio button was clicked
        switch (v.getId()) {
            case R.id.continua:
                if (checked)
                    tipo = 0;
                    diassemana.setVisibility(View.VISIBLE);
                break;
            case R.id.puntual:
                if (checked)
                    tipo = 1;
                    diassemana.setVisibility(View.GONE);
                break;
        }
    }

    private void enviarnotificacion() {
        FileUtils fileUtils=new FileUtils();
        String cliente = fileUtils.readFile(this,"config.txt");
        Data inputData = new Data.Builder()
                .putString("usuario",usuario)
                .putString("descr","Hola soy "+cliente+", ¿Quieres ser mi profe?")
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