package com.example.tfg_profes;

import static com.example.tfg_profes.utils.AgendaUtils.sundayForDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_profes.utils.AgendaUtils;

import java.time.LocalDate;
import java.util.ArrayList;


public class AgendaSemanalFragment extends Fragment implements AdaptadorAgenda.OnItemListener{

    private TextView mesannoTxt;
    private RecyclerView calendarSemanalRecyclerView;
    private ListView liseventos;
    public AgendaSemanalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agenda_semanal, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button previousWeek=view.findViewById(R.id.previousMonth);
        Button nextWeek=view.findViewById(R.id.nextMonth);
        Button mensual=view.findViewById(R.id.mensual);
        Button crearEvent=view.findViewById(R.id.addEvent);
        liseventos=view.findViewById(R.id.lisEvents);
        calendarSemanalRecyclerView = view.findViewById(R.id.calendarSemanalRecyclerView);
        mesannoTxt=view.findViewById(R.id.monthyearTV);
        AgendaUtils.selectedDate= LocalDate.now();
        setSemView();
        previousWeek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                AgendaUtils.selectedDate = AgendaUtils.selectedDate.minusWeeks(1);
                setSemView();
            }
        });
        nextWeek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                AgendaUtils.selectedDate = AgendaUtils.selectedDate.plusWeeks(1);
                setSemView();
            }
        });
        mensual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                AgendaFragment agendaFragment=new AgendaFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, agendaFragment)
                        .commit();
            }
        });
        crearEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventEdit.class);
                startActivity(intent);
            }
        });
    }

    private void setSemView() {
        mesannoTxt.setText(String.valueOf(sundayForDate(AgendaUtils.selectedDate)));
       ArrayList<LocalDate> diasEnMes =AgendaUtils.diasEnSemArray(AgendaUtils.selectedDate);
        AdaptadorAgenda adaptadorAgenda=new AdaptadorAgenda(diasEnMes,this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),7);
        calendarSemanalRecyclerView.setLayoutManager(layoutManager);
        calendarSemanalRecyclerView.setAdapter(adaptadorAgenda);
        SetEventAdapter();
    }


    @Override
    public void onItemClick(int position, LocalDate date) {
        AgendaUtils.selectedDate=date;
        setSemView();
    }
    @Override
    public void onResume() {
        super.onResume();
        SetEventAdapter();
    }

    private void SetEventAdapter() {
        ArrayList<Evento> eventosDia=Evento.eventosdeldia(AgendaUtils.selectedDate);
        AdaptadorEventos adaptadorEventos=new AdaptadorEventos(getContext(),eventosDia);
        liseventos.setAdapter(adaptadorEventos);
    }
}