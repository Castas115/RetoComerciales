package com.example.retocomerciales;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class activity_menu extends AppCompatActivity {

    ImageButton calendario, pedidos, delegaciones, partners;
    Button volver;
    Intent intent;
    String addresses;
    String subject;
    Uri attachment ;



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

                /*


                try {
                    crearCorreo();

                } catch (IOException e) {
                    e.printStackTrace();
                }

 */
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

    /*


    public void crearCorreo() throws IOException {



        String filename="partners.xml";



        File filelocation = new File(String.valueOf(getAssets().open(filename)));
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"asd@gmail.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));




    }

    public void composeEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"delegacionChargenetic@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Xml Partners");
        startActivity(emailIntent);
    }


     */
}