package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;

public class activity_menu extends AppCompatActivity {

    ImageButton calendario, pedidos, delegaciones, partners;
    Button volver;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        //findViewById
        calendario = findViewById(R.id.img_calendario);
        pedidos = findViewById(R.id.img_gestionPedidos);
        delegaciones = findViewById(R.id.img_envioDelegacion);
        partners = findViewById(R.id.img_gestionPartners);
        volver = findViewById(R.id.btn_volver);




        //Lambdas con listeners para cada ImageButton
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_menu.this, activity_calendario.class);
                startActivity(intent);
            }
        });

        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_menu.this, activity_pedido1.class);
                Datos datos = Datos.getInstance();

                datos.cargarExistencias();//cargar existenciasCompras = existencias (nuevo pedido cancelando los cambios hechos anteriormente)
                datos.nuevoPedido(datos.getComercial());
                startActivity(intent);
            }
        });

        delegaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_menu.this, activity_envios.class);
                startActivity(intent);
            }
        });

        partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_menu.this, activity_gestionPartner.class);
                startActivity(intent);
            }
        });

    }
}