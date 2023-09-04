package com.example.tfg_profes;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

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
        int[] petiM= new int[4];
        int[] petiL= new int[4];
        int[] petiE= new int[4];
        int[] petiI= new int[4];
        int[] petiF= new int[4];
        int[] petiN= new int[4];
        int[] petiS= new int[4];
        int[] petiT= new int[4];
        int[] petiA= new int[4];
        petiM=getIntent().getExtras().getIntArray("mate");
        petiL=getIntent().getExtras().getIntArray("lengua");
        petiI=getIntent().getExtras().getIntArray("ingles");
        petiE=getIntent().getExtras().getIntArray("euskera");
        petiF=getIntent().getExtras().getIntArray("fiki");
        petiN=getIntent().getExtras().getIntArray("natura");
        petiS=getIntent().getExtras().getIntArray("sociales");
        petiT=getIntent().getExtras().getIntArray("tics");
        petiA=getIntent().getExtras().getIntArray("apoyo");


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