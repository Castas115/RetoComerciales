package com.example.retocomerciales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;


public class activity_menu extends AppCompatActivity {

    ImageButton calendario, pedidos, delegaciones, partners;
    Button volver;
    Intent intent;
    String addresses;
    String subject;
    Uri attachment ;

    String title;
    String location;
    long begin;
    long end;

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
                //Para acceder al layout de calendario para utilizar con BD en la segunda fase
                /*intent = new Intent(activity_menu.this, activity_calendario.class);
                startActivity(intent);*/
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, title)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
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