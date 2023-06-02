package com.example.tfg_profes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_profes.utils.AgendaUtils;

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
                Evento.eventosLis.add(e);
                finish();
            }
        });
    }


}