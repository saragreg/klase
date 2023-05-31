package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

public class Graph_demanda_asig_annos extends AppCompatActivity {
    int anno=2020;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_demanda_asig_annos);
        //obtener los datos de las clases
        //peti=getIntent().getParcelableArrayListExtra("peticiones_anno");
        Integer[] petiM= new Integer[4];
        Integer[] petiL= new Integer[4];
        Integer[] petiE= new Integer[4];
        Integer[] petiI= new Integer[4];
        Integer[] petiF= new Integer[4];
        Integer[] petiN= new Integer[4];
        Integer[] petiS= new Integer[4];
        Integer[] petiT= new Integer[4];
        Integer[] petiA= new Integer[4];

        petiM[0]=1;petiL[0]=2;petiE[0]=5;petiI[0]=1;petiF[0]=6;petiN[0]=0;petiS[0]=1;petiT[0]=3;petiA[0]=10;
        petiM[1]=12;petiL[1]=5;petiE[1]=5;petiI[1]=2;petiF[1]=2;petiN[1]=3;petiS[1]=4;petiT[1]=3;petiA[1]=12;
        petiM[2]=10;petiL[2]=7;petiE[2]=15;petiI[2]=9;petiF[2]=6;petiN[2]=5;petiS[2]=4;petiT[2]=3;petiA[2]=13;
        petiM[3]=15;petiL[3]=7;petiE[3]=10;petiI[3]=14;petiF[3]=8;petiN[3]=2;petiS[3]=6;petiT[3]=13;petiA[3]=12;


        LineChart lineChart = findViewById(R.id.lineChart);
        //creamos una lista de entradas para cada asignatura
        ArrayList<Entry> entriesMate = new ArrayList<>();
        ArrayList<Entry> entriesLengua = new ArrayList<>();
        ArrayList<Entry> entriesEuskera = new ArrayList<>();
        ArrayList<Entry> entriesIngles = new ArrayList<>();
        ArrayList<Entry> entriesFiki = new ArrayList<>();
        ArrayList<Entry> entriesNatur = new ArrayList<>();
        ArrayList<Entry> entriesSoc = new ArrayList<>();
        ArrayList<Entry> entriesTic = new ArrayList<>();
        ArrayList<Entry> entriesApoyo = new ArrayList<>();
        while (anno<2024){
            entriesMate.add(new Entry(anno, petiM[i]));
            entriesLengua.add(new Entry(anno, petiL[i]));
            entriesEuskera.add(new Entry(anno, petiE[i]));
            entriesIngles.add(new Entry(anno, petiI[i]));
            entriesFiki.add(new Entry(anno, petiF[i]));
            entriesNatur.add(new Entry(anno, petiN[i]));
            entriesSoc.add(new Entry(anno, petiS[i]));
            entriesTic.add(new Entry(anno, petiT[i]));
            entriesApoyo.add(new Entry(anno, petiA[i]));
            anno++;
            i++;
        }
        /*entriesMate.add(new Entry(2020, 3));
        entriesMate.add(new Entry(2021, 5));
        entriesMate.add(new Entry(2022, 12));
        entriesMate.add(new Entry(2023, 10));
        entriesLengua.add(new Entry(2020,2));
        entriesLengua.add(new Entry(2021, 3));
        entriesLengua.add(new Entry(2022, 4));
        entriesLengua.add(new Entry(2023, 2));
        entriesEuskera.add(new Entry(2020,1));
        entriesEuskera.add(new Entry(2021, 8));
        entriesEuskera.add(new Entry(2022, 10));
        entriesEuskera.add(new Entry(2023, 8));
        entriesIngles.add(new Entry(2020,1));
        entriesIngles.add(new Entry(2021, 3));
        entriesIngles.add(new Entry(2022, 4));
        entriesIngles.add(new Entry(2023, 1));
        entriesFiki.add(new Entry(2020,1));
        entriesFiki.add(new Entry(2021, 3));
        entriesFiki.add(new Entry(2022, 10));
        entriesFiki.add(new Entry(2023, 11));
        entriesNatur.add(new Entry(2020,2));
        entriesNatur.add(new Entry(2021, 3));
        entriesNatur.add(new Entry(2022, 0));
        entriesNatur.add(new Entry(2023, 1));
        entriesSoc.add(new Entry(2020,2));
        entriesSoc.add(new Entry(2021, 3));
        entriesSoc.add(new Entry(2022, 0));
        entriesSoc.add(new Entry(2023, 1));
        entriesApoyo.add(new Entry(2020,12));
        entriesApoyo.add(new Entry(2021, 13));
        entriesApoyo.add(new Entry(2022, 10));
        entriesApoyo.add(new Entry(2023, 11));*/


// Configurar las líneas de datos
        LineDataSet lineDataSetMate = new LineDataSet(entriesMate, "Mate");
        lineDataSetMate.setColor(Color.YELLOW);
        lineDataSetMate.setCircleColor(Color.YELLOW);
        //lineDataSetMate.setFormLineWidth(50);

        LineDataSet lineDataSetLengua = new LineDataSet(entriesLengua, "Lengua");
        lineDataSetLengua.setColor(Color.BLUE);
        lineDataSetLengua.setCircleColor(Color.BLUE);

        LineDataSet lineDataSetEus = new LineDataSet(entriesEuskera, "Euskera");
        lineDataSetEus.setColor(Color.RED);
        lineDataSetEus.setCircleColor(Color.RED);
        //lineDataSetEus.setFormLineWidth(50);

        LineDataSet lineDataSetIngles = new LineDataSet(entriesIngles, "Ingles");
        lineDataSetIngles.setColor(Color.rgb(240,155,89));
        lineDataSetIngles.setCircleColor(Color.rgb(240,155,89));

        LineDataSet lineDataSetFiki = new LineDataSet(entriesFiki, "Fisica y Química");
        lineDataSetFiki.setColor(Color.rgb(131,31,140));
        lineDataSetFiki.setCircleColor(Color.rgb(131,31,140));
        //lineDataSetFiki.setFormLineWidth(50);

        LineDataSet lineDataSetNat = new LineDataSet(entriesNatur, "Ciencias de la Naturaleza");
        lineDataSetNat.setColor(Color.rgb(131,31,140));
        lineDataSetNat.setCircleColor(Color.rgb(117,249,77));

        LineDataSet lineDataSetSoc = new LineDataSet(entriesSoc, "Ciencias Sociales");
        lineDataSetSoc.setColor(Color.rgb(120,67,21));
        lineDataSetSoc.setCircleColor(Color.rgb(120,67,21));


        LineDataSet lineDataSetTic = new LineDataSet(entriesTic, "Tics");
        lineDataSetTic.setColor(Color.GRAY);
        lineDataSetTic.setCircleColor(Color.GRAY);

        LineDataSet lineDataSetApoyo = new LineDataSet(entriesApoyo, "Apoyo Académico");
        lineDataSetApoyo.setColor(Color.rgb(242,50,238));
        lineDataSetApoyo.setCircleColor(Color.rgb(242,50,238));

// Crear un objeto LineData con los conjuntos de datos de las líneas
        LineData lineData = new LineData(lineDataSetMate, lineDataSetLengua,lineDataSetEus,lineDataSetEus,lineDataSetIngles,lineDataSetFiki,lineDataSetNat,lineDataSetSoc,lineDataSetApoyo);//

// Configurar el eje X
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int year = (int) value;
                return String.valueOf(year);
            }
        });

// Establecer el objeto LineData en el gráfico
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refrescar el gráfico

    }
}