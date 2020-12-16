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
        /**
         * sutituir los datos por la lectura de xmls
         */

        listaProductos = new Producto[]{
                new Producto("PPB_", "PistachoB", "movil", 79.95f, 10),
                new Producto("PPA_", "PistachoA", "movil", 125.95f, 10),
                new Producto("PPA+", "PistachoA+", "movil", 153.45f, 10),
                new Producto("PPO_", "PistachoO", "movil", 279.95f, 10),
                new Producto("PPO+", "PistachoO+", "movil", 293.45f, 10),
                new Producto("PPod", "PistachoPods", "airpodsPistacho", 24.95f, 10),
                new Producto("Carg", "Cargador Pistacho", "cargadorPistacho", 12.34f, 10),
                new Producto("FPB_", "Funda PistachoB", "Funda diseñada para proteger de tu PistachoPhone Beta",  "fundaPistacho", 7f, 10),
                new Producto("FPA_", "Funda PistachoA", "Funda diseñada para proteger de tu PistachoPhone Alfa", "fundaPistacho", 7f, 10),
                new Producto("FPA+", "Funda PistachoA+", "Funda diseñada para proteger de tu PistachoPhone Alfa+", "fundaPistacho", 8.54f, 10),
                new Producto("FPO_", "Funda PistachoO", "Funda diseñada para proteger de tu PistachoPhone Omega", "fundaPistacho", 8f, 10),
                new Producto("FPO+", "Funda PistachoO+", "Funda diseñada para proteger de tu PistachoPhone Omega+", "fundaPistacho", 9.54f, 10)};

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