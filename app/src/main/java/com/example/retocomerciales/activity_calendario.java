package com.example.retocomerciales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class activity_calendario extends AppCompatActivity {
    CalendarView calendario;
    ArrayList<String> fechas = new ArrayList<String>();
    ArrayList<String> desc = new ArrayList<String>();
    String date;
    Button evento, guardar, volver;
    EditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendario);
       calendario = findViewById(R.id.calendarView2);
        evento  = findViewById(R.id.btnAddEvento);
        guardar  = findViewById(R.id.btnGuardar);
        descripcion = findViewById(R.id.descEvento);
        //volver = findViewById(R.id.btnVolver3);


       descripcion.setEnabled(false);
        guardar.setEnabled(false);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
               descripcion.setEnabled(false);
               guardar.setEnabled(false);
               date = dayOfMonth+"/"+(month+1)+"/"+year;
               recorrerEventos(fechas,desc,date);
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
                   desc.add(descripcion.getText().toString());
               }
                descripcion.setEnabled(false);
                guardar.setEnabled(false);
                descripcion.setText("");
            }
        });
    }


    public void recorrerEventos(ArrayList<String> fechas, ArrayList<String> desc, String fecha){
        for(int i = 0; i<fechas.size(); i++){
           if (fechas.get(i).equalsIgnoreCase(fecha)) {
               AlertDialog alertDialog = new AlertDialog.Builder(activity_calendario.this).create();
               alertDialog.setTitle("Evento");
               alertDialog.setMessage(desc.get(i));

               alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cerrar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                   }
               });

               alertDialog.show();
            }
        }
    }
}