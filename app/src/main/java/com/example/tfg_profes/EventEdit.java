package com.example.tfg_profes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.tfg_profes.utils.AgendaUtils;
import com.example.tfg_profes.utils.FileUtils;

public class EventEdit extends AppCompatActivity {
    private EditText eventEditET;
    private TextView fechaKlase;
    private Button addevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        eventEditET=findViewById(R.id.descEvenET);
        fechaKlase=findViewById(R.id.fechaKlase);
        addevent=findViewById(R.id.addEvent);
        eventEditET.setText(String.valueOf(AgendaUtils.formattedDate(AgendaUtils.selectedDate)));
        fechaKlase.setText(AgendaUtils.formattedDate(AgendaUtils.selectedDate));
        addevent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String descEvento=eventEditET.getText().toString();
                Evento e=new Evento(descEvento,AgendaUtils.selectedDate);
                e.guardarEvento();
                addEvento(descEvento,String.valueOf(AgendaUtils.selectedDate));
                Evento.eventosLis.add(e);
                finish();
            }
        });
    }

    private void addEvento(String descEvento, String fechaEvento) {
        FileUtils fileUtils=new FileUtils();
        String user=fileUtils.readFile(this, "config.txt");
        Data inputData = new Data.Builder()
                .putString("tipo", "addEvento")
                .putString("usu", user)
                .putString("fecha", fechaEvento)
                .putString("descr", descEvento)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                            Toast.makeText(getApplicationContext(), "Se ha añadido correctamente", Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }


}