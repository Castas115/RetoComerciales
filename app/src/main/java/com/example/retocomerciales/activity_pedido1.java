package com.example.retocomerciales;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.retocomerciales.Clases.Datos;
import java.util.Calendar;
import java.util.Objects;


public class activity_pedido1 extends AppCompatActivity {

    Button siguiente, volver,fecha, visualizaPedidos;
    Intent intent;
    Datos datos;
    TextView mostrarFecha;

    Spinner spnPartners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido1);

        siguiente = findViewById(R.id.btn_siguiente);
        volver = findViewById(R.id.btn_volver);
        spnPartners = findViewById(R.id.spn_partners);
        fecha = findViewById(R.id.btn_elegirFecha);
        mostrarFecha = findViewById(R.id.lbl_fecha);
        visualizaPedidos = findViewById(R.id.btn_visualizaPedidos);


        siguiente.setEnabled(false);
        datos = Datos.getInstance();

        //Spinner partners
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresPartners());
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPartners.setAdapter(adapterPartners);

        visualizaPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos.cargarListaPedidos();
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               elegirFecha();
            }
        });


        //lambdas con listeners para los botones
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity_pedido1.this, activity_pedido2.class);
                datos.setPosPartner(spnPartners.getSelectedItemPosition());
                datos.getPedido().setPartner(datos.getPartner(spnPartners.getSelectedItemPosition()));

                datos.getPedido().setFecha(mostrarFecha.getText().toString());

                startActivityForResult(intent, 123);
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent vuelta) {
        super.onActivityResult(requestCode, resultCode, vuelta);
        if (requestCode == 123 && resultCode == RESULT_OK){
            boolean res = Objects.requireNonNull(vuelta.getExtras()).getBoolean("Volver");
            if (res){
                finish();
            }
        }
    }


    public void elegirFecha() {

        final Calendar cldr = Calendar.getInstance();
        int dia = cldr.get(Calendar.DAY_OF_MONTH);
        int mes = cldr.get(Calendar.MONTH);
        int ano = cldr.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mostrarFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    if(mostrarFecha.length()!=0){
                        siguiente.setEnabled(true);
                    }

                }
            }, ano, mes, dia);
            datePickerDialog.show();





    }


}