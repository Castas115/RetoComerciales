package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Partner;
import com.example.retocomerciales.Clases.Producto;


public class activity_pedido1 extends AppCompatActivity {

    Button siguiente, volver;
    Intent intent;
    Datos datos;

    Spinner spnPartners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido1);

        siguiente = findViewById(R.id.btn_siguiente);
        volver = findViewById(R.id.btn_volver);
        spnPartners = findViewById(R.id.spn_partners);

        datos = Datos.getInstance();

        //Spinner partners
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getListNombres(datos.getPartners()));
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPartners.setAdapter(adapterPartners);



        //lambdas con listeners para los botones
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_pedido1.this, activity_pedido2.class);
                intent.putExtra("partner", datos.getPartner(spnPartners.getSelectedItemPosition()));
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

    //devuelven lista de nombres
    public String[] getListNombres(Producto[] list){
        String[] nombres = new String[list.length];

        for(int i = 0; i < list.length; i++){
            nombres[i] = list[i].getNombre();
        }
        return nombres;
    }
    public String[] getListNombres(Partner[] list){
        String[] nombres = new String[list.length];

        for(int i = 0; i < list.length; i++){
            nombres[i] = list[i].getNombre();
        }
        return nombres;
    }
}