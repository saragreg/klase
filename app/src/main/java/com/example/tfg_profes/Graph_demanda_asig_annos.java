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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_demanda_asig_annos);

        LineChart lineChart = findViewById(R.id.lineChart);

// Crear una lista de entradas para cada línea del gráfico
        ArrayList<Entry> entriesLine1 = new ArrayList<>();
        entriesLine1.add(new Entry(2018, 5)); // Año 2018, valor línea 1
        entriesLine1.add(new Entry(2019, 8)); // Año 2019, valor línea 1
        entriesLine1.add(new Entry(2020, 3)); // Año 2020, valor línea 1
        entriesLine1.add(new Entry(2021, 6)); // Año 2021, valor línea 1
        entriesLine1.add(new Entry(2022, 10)); // Año 2022, valor línea 1

        ArrayList<Entry> entriesLine2 = new ArrayList<>();
        entriesLine2.add(new Entry(2018, 3)); // Año 2018, valor línea 2
        entriesLine2.add(new Entry(2019, 6)); // Año 2019, valor línea 2
        entriesLine2.add(new Entry(2020, 9)); // Año 2020, valor línea 2
        entriesLine2.add(new Entry(2021, 4)); // Año 2021, valor línea 2
        entriesLine2.add(new Entry(2022, 7)); // Año 2022, valor línea 2

// Configurar las líneas de datos
        LineDataSet lineDataSet1 = new LineDataSet(entriesLine1, "Línea 1");
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setCircleColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(entriesLine2, "Línea 2");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setCircleColor(Color.RED);

// Crear un objeto LineData con los conjuntos de datos de las líneas
        LineData lineData = new LineData(lineDataSet1, lineDataSet2);

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