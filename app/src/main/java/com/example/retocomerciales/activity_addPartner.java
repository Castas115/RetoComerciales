package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Partner;

import javax.xml.parsers.ParserConfigurationException;

public class activity_addPartner extends AppCompatActivity {

    EditText nombrePartner, direccionPartner, poblacionPartner, cifPartner, telefonoPartner, emailPartner;
    Button anadir, volver;
    Spinner partnerComerciales;

    private int posicionComercialEnLista;
    String idComercial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_partner);
        nombrePartner = findViewById(R.id.tbNombreAddPartner);
        direccionPartner = findViewById(R.id.tbDireccionAddPartner);
        poblacionPartner = findViewById(R.id.tbPoblacionAddPartner);
        cifPartner = findViewById(R.id.tbCifAddPartner);
        telefonoPartner = findViewById(R.id.tbTelefonoAddPartner);
        emailPartner = findViewById(R.id.tbEmailAddPartner);
        anadir = findViewById(R.id.btnAnadir);
        volver = findViewById(R.id.btnVolver2);
        partnerComerciales = findViewById(R.id.spiner_partnerComerciales);

        final Datos datos = Datos.getInstance();

        final ArrayAdapter adapterComerciales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresComerciales());
        adapterComerciales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partnerComerciales.setAdapter(adapterComerciales);

        partnerComerciales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionComercialEnLista = position;
                idComercial = String.valueOf(datos.getComercial(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombrePartner.getText().toString().isEmpty() || !direccionPartner.getText().toString().isEmpty() || !cifPartner.getText().toString().isEmpty() || !poblacionPartner.getText().toString().isEmpty() || !telefonoPartner.getText().toString().isEmpty() || !emailPartner.getText().toString().isEmpty()){

                    new Partner("4", nombrePartner.toString(), direccionPartner.toString(), cifPartner.toString(), poblacionPartner.toString() ,telefonoPartner.toString(), emailPartner.toString(), "0");
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Añadido", Toast.LENGTH_SHORT);
                    toast1.show();

                }else{

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Hay campos vacios!", Toast.LENGTH_SHORT);

                    toast1.show();

                }

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