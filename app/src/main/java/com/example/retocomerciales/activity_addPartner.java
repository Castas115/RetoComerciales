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

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Partner;

import java.util.Objects;

public class activity_addPartner extends AppCompatActivity {

    EditText nombrePartner, direccionPartner, poblacionPartner, cifPartner, telefonoPartner, emailPartner;
    Button anadir, volver;
    Spinner partnerComerciales;

    private String[][] listComerciales;
    private String[] listNombresComerciales;
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

        listComerciales = datos.selectNombresComerciales();
        listNombresComerciales = new String[listComerciales[1].length];
        for (int i = 0; i < listComerciales[1].length; i++){
            listNombresComerciales[i] = listComerciales[1][i];
        }
        final ArrayAdapter adapterComerciales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listNombresComerciales);
        adapterComerciales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partnerComerciales.setAdapter(adapterComerciales);

        partnerComerciales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idComercial = listComerciales[0][position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombrePartner.getText().toString().isEmpty() && !direccionPartner.getText().toString().isEmpty() && !cifPartner.getText().toString().isEmpty() && !poblacionPartner.getText().toString().isEmpty() && !telefonoPartner.getText().toString().isEmpty() && !emailPartner.getText().toString().isEmpty()){
                    int id;
                    if (datos.getPartners().length > 0){
                        id = Integer.parseInt(datos.getPartner(datos.getPartners().length-1).getId())+1;
                    }else{
                        id = 1;
                    }
                    Partner partner = new Partner(String.valueOf(id), nombrePartner.getText().toString(), direccionPartner.getText().toString(), cifPartner.getText().toString(), poblacionPartner.getText().toString() ,telefonoPartner.getText().toString(), emailPartner.getText().toString(), idComercial);
                    Datos.getInstance().escribirNewPartnerDOM(partner);
                    Datos.getInstance().insert(partner,datos.getDb());
                    Datos.getInstance().addPartner(partner);

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Añadido", Toast.LENGTH_SHORT);
                    toast1.show();


                    Intent intent = new Intent(activity_addPartner.this, activity_gestionPartner.class);
                    startActivityForResult(intent, 123);
                    finish();

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