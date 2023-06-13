package com.example.tfg_profes;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.FileUtils;

public class CambioDatos extends AppCompatActivity {
    private boolean hasChanged=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_datos);
        int grayColor = Color.GRAY;
        int primaryColor;
        primaryColor= getResources().getColor(R.color.theme_color);
        Button saveButton = findViewById(R.id.guardar);
        saveButton.setBackgroundTintList(ColorStateList.valueOf(grayColor));
        saveButton.setEnabled(false);
        ImageView atras=findViewById(R.id.atras_btn);
        //atras boton
        atras.setOnClickListener(v -> {
            finish();
        });
        EditText usuario = findViewById(R.id.usuarioCambio);
        usuario.setText(Usuario.getUsuariosLis().get(0).getUser());
        usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasChanged = true;
                saveButton.setEnabled(hasChanged);
                saveButton.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText nombre = findViewById(R.id.nombreCambio);
        nombre.setText(Usuario.getUsuariosLis().get(0).getNombre());
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasChanged = true;
                saveButton.setEnabled(hasChanged);
                saveButton.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        EditText tel = findViewById(R.id.telCambio);
        tel.setText("+34"+Usuario.getUsuariosLis().get(0).getTel());
        tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasChanged = true;
                saveButton.setEnabled(hasChanged);
                saveButton.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario.getText().toString().equals("")|| nombre.getText().toString().equals("")|| tel.getText().toString().equals("")){
                    Toast.makeText(CambioDatos.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
// Código de inserción en la base de datos usando la contraseña hasheada
                    if(!usuario.getText().toString().equals(Usuario.usuariosLis.get(0).getUser())){
                        comprobarUsu(usuario.getText().toString(),nombre.getText().toString(),tel.getText().toString());
                    }else{
                        updateUsuario(usuario.getText().toString(),nombre.getText().toString(),tel.getText().toString());
                    }
                }
            }
        });

        Button saveContra=findViewById(R.id.guardarContra);
        saveContra.setBackgroundTintList(ColorStateList.valueOf(grayColor));
        EditText contraAnti = findViewById(R.id.contraAnti);
        EditText contraNueva = findViewById(R.id.contranueva);
        EditText contraNuevaRepe=findViewById(R.id.contraNuevaRepe);
        contraAnti.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasChanged = true;
                saveContra.setEnabled(hasChanged);
                saveContra.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        saveContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contraAnti.getText().toString().equals("")|| contraNueva.getText().toString().equals("")|| contraNuevaRepe.getText().toString().equals("")){
                    Toast.makeText(CambioDatos.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    comprobarContra(contraAnti.getText().toString(),contraNueva.getText().toString(),contraNuevaRepe.getText().toString());
                }
            }
        });

    }
    private void comprobarUsu(String usuInt, String nomInt, String telInt) {
        Data inputData = new Data.Builder()
                .putString("tipo", "login")
                .putString("usuario",usuInt)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String contraRec = workInfo.getOutputData().getString("res");
                            //comprobamos que no existe el usuario
                            if (contraRec.equals("mal")) {
                                //el usuario no existe
                                //saveSession(usuInt);
                                updateUsuario(usuInt,nomInt,telInt);
                            } else {
                                //la contraseña es incorrecta
                                Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
    private void comprobarContra(String contraAntigua, String contraNue, String contraNueRep){
        FileUtils fileUtils=new FileUtils();
        String usuario=fileUtils.readFile(this, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "login")
                .putString("usuario", usuario)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String contraRec = workInfo.getOutputData().getString("res");
                            String per = workInfo.getOutputData().getString("per");
                            //comprobamos que existe el usuario
                            if (contraRec.equals("mal")) {
                                //el usuario no existe
                                Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                            }  else {// Obtiene la contraseña hasheada almacenada en la base de datos
                                boolean passwordMatches = PasswordHasher.checkPassword(contraAntigua, contraRec);
                                if (passwordMatches) {
                                    // Contraseña correcta
                                    if (!contraNue.equals(contraNueRep)){
                                        updateContra(contraNue);
                                    }
                                } else {
                                    // Contraseña incorrecta
                                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    private void updateContra(String contraNue) {
        FileUtils fileUtils=new FileUtils();
        String usu=fileUtils.readFile(this,"config.txt");
        String hashedPassword = PasswordHasher.hashPassword(contraNue);
        Data inputData = new Data.Builder()
                .putString("tipo", "updateContra")
                .putString("usu", usu)
                .putString("contra", hashedPassword)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
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

    private void updateUsuario(String usuInt, String nomInt, String telInt) {
        FileUtils fileUtils=new FileUtils();
        String usuAnti=fileUtils.readFile(this,"config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "updateUsuario")
                .putString("usuAnti",usuAnti)
                .putString("usu", usuInt)
                .putString("nom", nomInt)
                .putString("tel", telInt)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
}