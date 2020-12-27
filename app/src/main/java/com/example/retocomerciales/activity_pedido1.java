package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;


import java.text.DateFormat;
import java.util.Date;


public class activity_pedido1 extends AppCompatActivity {

    Button siguiente, volver,fecha;
    Intent intent;
    Datos datos;
    TextView mostrarFecha;


    Spinner spnPartners,spnComerciales;

    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido1);

        siguiente = findViewById(R.id.btn_siguiente);
        volver = findViewById(R.id.btn_volver);
        spnPartners = findViewById(R.id.spn_partners);
        spnComerciales = findViewById(R.id.spn_comerciales);
        fecha = findViewById(R.id.btn_elegirFecha);
        mostrarFecha = findViewById(R.id.lbl_fecha);

        datos = Datos.getInstance();

        //Spinner partners
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresPartners());
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPartners.setAdapter(adapterPartners);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarFecha.setText(currentDateTimeString);
            }
        });


        //lambdas con listeners para los botones
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_pedido1.this, activity_pedido2.class);
                datos.setPosPartner(spnPartners.getSelectedItemPosition());
                datos.getPedido().setPartner(datos.getPartner(spnPartners.getSelectedItemPosition()));
                startActivityForResult(intent, 123);
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //para vovler al menú tras la compra

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent vuelta) {
        super.onActivityResult(requestCode, resultCode, vuelta);
        if (requestCode == 123 && resultCode == RESULT_OK){
            boolean res = vuelta.getExtras().getBoolean("Volver");
            if (res){
                finish();
            }
        }
    }
}