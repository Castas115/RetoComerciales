package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class activity_calendario extends AppCompatActivity {
    CalendarView calendario;
    ArrayList<String> fechas = new ArrayList<String>();
    ArrayList<String> desc = new ArrayList<String>();
    String date;
    Button evento, guardar;
    EditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendario);
       calendario = findViewById(R.id.calendarView2);
        evento  = findViewById(R.id.btnAddEvento);
        evento  = findViewById(R.id.btnGuardar);
        descripcion = findViewById(R.id.descEvento);

       /*descripcion.setEnabled(false);
        guardar.setEnabled(false);*/

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
               descripcion.setEnabled(false);
               guardar.setEnabled(false);
               date = dayOfMonth+"/"+month+"/"+year;
            }
        });

        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               descripcion.setEnabled(true);
               guardar.setEnabled(true);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!descripcion.getText().toString().isEmpty()){
                   fechas.add(date);
                   desc.add(descripcion.toString());
               }
            }
        });
    }
}