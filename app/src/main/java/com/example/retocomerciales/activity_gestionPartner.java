package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;

import java.util.Objects;

public class activity_gestionPartner extends AppCompatActivity {


    Button alta, volver, borrar, actualizar;
    Intent intent;
    Spinner spnGestionPartners;
    Datos datos;
    TextView tbnombre , tbdireccion, tbcif, tbtlfn, tbemail;
    String posicion;
    int pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_partner);

        //findViewById
        alta = findViewById(R.id.btnAlta);
        volver = findViewById(R.id.btnVolver);
        spnGestionPartners = findViewById(R.id.spinner2);
        tbnombre = findViewById(R.id.contenidoNombre);
        tbdireccion = findViewById(R.id.contenidoDireccion);
        tbcif = findViewById(R.id.contenidoCif);
        tbtlfn = findViewById(R.id.contenidoTlfno);
        tbemail = findViewById(R.id.contenidoEmail);
        borrar = findViewById(R.id.btnBorrar);
        actualizar = findViewById(R.id.btn_actualizar);




        datos = Datos.getInstance();

        //Spinner partners
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresPartners());
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGestionPartners.setAdapter(adapterPartners);

        //listener del spinner
        spnGestionPartners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                posicion = String.valueOf(position);
                tbnombre.setText(String.valueOf(datos.getPartner(position).getNombre()));
                tbdireccion.setText(String.valueOf(datos.getPartner(position).getDireccion()));
                tbcif.setText(String.valueOf(datos.getPartner(position).getCIF()));
                tbtlfn.setText(String.valueOf(datos.getPartner(position).getTelefono()));
                tbemail.setText(String.valueOf(datos.getPartner(position).getEmail()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos.borrarPartner(datos.getPartner(Integer.parseInt(posicion)).getId());

                Toast.makeText(getApplicationContext(), "Partner borrado", Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_gestionPartner.this, activity_gestionPartner.class);
                startActivity(intent);
                finish();
            }
        });

        alta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_gestionPartner.this, activity_addPartner.class);
                startActivityForResult(intent, 123);
                finish();
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