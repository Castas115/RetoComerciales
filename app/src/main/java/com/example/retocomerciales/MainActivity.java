package com.example.retocomerciales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Partner;
import com.example.retocomerciales.Clases.Producto;
import com.example.retocomerciales.Clases.ReadXML;


public class MainActivity extends AppCompatActivity{

    Button _iniciar,llamar;
    Intent intent;
    Producto[] listaProductos;
    Partner[] listaPartners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _iniciar=findViewById(R.id.btn_iniciar);
        llamar = findViewById(R.id.btnLlamar);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:688847776"));
                startActivity(intent);
            }
        });


        _iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, activity_menu.class);
                startActivity(intent);
            }
        });
    }
}