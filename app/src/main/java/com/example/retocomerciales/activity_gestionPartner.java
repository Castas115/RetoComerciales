package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;

public class activity_gestionPartner extends AppCompatActivity {


    Button alta,volver;
    Intent intent;
    Spinner spnGestionPartners;
    Datos datos;
    TextView tbnombre , tbdireccion, tbpoblacion, tbcif, tbtlfn, tbemail;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_partner);

        //findViewById
        alta = findViewById(R.id.btnAlta);
        volver = findViewById(R.id.btnVolver);
        spnGestionPartners = findViewById(R.id.spinner2);
        tbnombre = findViewById(R.id.contenidoNombre);
        tbdireccion = findViewById(R.id.contenidoDireccion);
        tbpoblacion = findViewById(R.id.contenidoPoblacion);
        tbcif = findViewById(R.id.contenidoCif);
        tbtlfn = findViewById(R.id.contenidoTlfno);
        tbemail = findViewById(R.id.contenidoEmail);




        datos = Datos.getInstance();

        //Spinner partners
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresPartners());
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGestionPartners.setAdapter(adapterPartners);

        //listener del spinner
        spnGestionPartners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tbnombre.setText(String.valueOf(datos.getPartner(position).getNombre()));
                tbdireccion.setText(String.valueOf(datos.getPartner(position).getDireccion()));
                tbcif.setText(String.valueOf(datos.getPartner(position).getCIF()));
                tbpoblacion.setText(String.valueOf(datos.getPartner(position).getPoblacion()));
                tbtlfn.setText(String.valueOf(datos.getPartner(position).getTelefono()));
                tbemail.setText(String.valueOf(datos.getPartner(position).getEmail()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });


        alta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_gestionPartner.this, activity_addPartner.class);
                startActivity(intent);
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}