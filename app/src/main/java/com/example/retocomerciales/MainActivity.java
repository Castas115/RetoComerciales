package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Partner;
import com.example.retocomerciales.Clases.Producto;


public class MainActivity extends AppCompatActivity{

    Button _iniciar;
    Intent intent;
    Producto[] listaProductos;
    Partner[] listaPartners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _iniciar=findViewById(R.id.btn_iniciar);


        listaProductos = new Producto[]{
                new Producto("PPB_", "PistachoB", "movil", 79.95f),
                new Producto("PPA_", "PistachoA", "movil", 125.95f),
                new Producto("PPA+", "PistachoA+", "movil", 153.45f),
                new Producto("PPO_", "PistachoO", "movil", 279.95f),
                new Producto("PPO+", "PistachoO+", "movil", 293.45f),
                new Producto("PPod", "PistachoPods", "airpodsPistacho", 24.95f),
                new Producto("Carg", "Cargador Pistacho", "cargadorPistacho", 12.34f),
                new Producto("FPB_", "Funda PistachoB", "fundaPistacho", 7f),
                new Producto("FPA_", "Funda PistachoA", "fundaPistacho", 7f),
                new Producto("FPA+", "Funda PistachoA+", "fundaPistacho", 8.54f),
                new Producto("FPO_", "Funda PistachoO", "fundaPistacho", 8f),
                new Producto("FPO+", "Funda PistachoO+", "fundaPistacho", 9.54f)};

        listaPartners = new Partner[]{
            new Partner("1", "Cebanc", "Berio Pasealekua, 50, 20018 Donostia, Gipuzkoa", "A20045548", "943316900", "contacto@cebanc.com"),
            new Partner("2", "Ibermática", "Mikeletegi Pasealekua, 5, 20009 Donostia, Gipuzkoa", "A20038915", "943413500", "contacto@ibermatica.com"),
            new Partner("3", "Dosystem S.L.", "Sagardotegi Kalea, 1, 20160 Lasarte-Oria, SS", "A20040547", "943369533", "contacto@dosystem.com")
        };

        _iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, activity_menu.class);
                intent.putExtra("listaProductos", listaProductos);
                intent.putExtra("listaPartners", listaPartners);
                startActivity(intent);
            }
        });
    }
}