package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Partner;

public class activity_addPartner extends AppCompatActivity {

    EditText nombrePartner, direccionPartner, poblacionPartner, cifPartner, telefonoPartner, emailPartner;
    Button anadir, volver;

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




        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombrePartner.getText().toString().isEmpty() || !direccionPartner.getText().toString().isEmpty() || !cifPartner.getText().toString().isEmpty() || !poblacionPartner.getText().toString().isEmpty() || !telefonoPartner.getText().toString().isEmpty() || !emailPartner.getText().toString().isEmpty()){

                    new Partner("4", nombrePartner.toString(), direccionPartner.toString(), cifPartner.toString(), poblacionPartner.toString() ,telefonoPartner.toString(), emailPartner.toString());
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