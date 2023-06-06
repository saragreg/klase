package com.example.tfg_profes;

import static com.example.tfg_profes.utils.AgendaUtils.mesAnnoFromDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AgendaFragment extends Fragment implements AdaptadorAgenda.OnItemListener{

    private TextView mesannoTxt;
    private RecyclerView calendarRecyclerView;
    public AgendaFragment() {
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
        return inflater.inflate(R.layout.fragment_agenda, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button previousWeek=view.findViewById(R.id.previousMonth);
        Button nextWeek=view.findViewById(R.id.nextMonth);
        Button semanal=view.findViewById(R.id.week);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        mesannoTxt=view.findViewById(R.id.monthyearTV);
        AgendaUtils.selectedDate=LocalDate.now();
        setMesView();
        previousWeek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                AgendaUtils.selectedDate = AgendaUtils.selectedDate.minusMonths(1);
                setMesView();
            }
        });
        nextWeek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                AgendaUtils.selectedDate = AgendaUtils.selectedDate.plusMonths(1);
                setMesView();
            }
        });
        semanal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                AgendaSemanalFragment agendaSemanalFragment=new AgendaSemanalFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, agendaSemanalFragment)
                        .commit();
            }
        });
    }

    private void setMesView() {
        ArrayList<LocalDate> arrayDiasNaranjasMes=new ArrayList<>();
        for (int i = 0; i < Evento.eventosLis.size(); i++) {
            arrayDiasNaranjasMes.add(Evento.eventosLis.get(i).getDate());
        }
        mesannoTxt.setText(mesAnnoFromDate(AgendaUtils.selectedDate));
        ArrayList<LocalDate> diasEnMes =AgendaUtils.diasEnMesArray(AgendaUtils.selectedDate);
        AdaptadorAgenda adaptadorAgenda=new AdaptadorAgenda(diasEnMes, arrayDiasNaranjasMes, this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(adaptadorAgenda);
    }


    @Override
    public void onItemClick(int position, LocalDate date) {
        AgendaUtils.selectedDate=date;
        setMesView();
    }
}