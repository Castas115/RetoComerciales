package com.example.retocomerciales;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


public class activity_pedido3 extends AppCompatActivity {

    private Datos datos;
    private boolean primer;


    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido3);

        ListView carroProductos = findViewById(R.id.lsv_listaProductos);
        Button confirmar = findViewById(R.id.btn_terminarPedido);
        Button volver = findViewById(R.id.btn_volver);
        Spinner spnPartners = findViewById(R.id.spn_partners);
        TextView comercial = findViewById(R.id.lbl_comercial);
        primer = true;

        datos = Datos.getInstance();

        //mostrar nombre del comercial
        comercial.setText("Comercial: " + datos.getPedido().getComercial().getNombre() + " " + datos.getPedido().getComercial().getApellidos());

        //adapter creado por nosotros aplicado al listview
        final LineaListAdapter adapter = new LineaListAdapter(this, R.layout.list_item_productos, datos.getPedido().getLineas());
        carroProductos.setAdapter(adapter);

        //Spinner partnerss
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresPartners());
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPartners.setAdapter(adapterPartners);
        //partner seleccionado anteriormente seleccionado por defect
        spnPartners.setSelection(datos.getPosPartner());

        //boton confimar
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarPedido();
            }
        });

        //boton volver
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //spinner partners
        spnPartners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datos.getPedido().setPartner(datos.getPartner(position));
                if (!primer) {
                    Toast.makeText(getApplicationContext(), "Partner cambiado", Toast.LENGTH_SHORT).show();
                } else {
                    primer = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void realizarPedido() {
        //introducir escritura en xml
        datos.escribirPedidoDOM();
        datos.escribirProductoDOM();

        Toast.makeText(getApplicationContext(), "Compra realizada", Toast.LENGTH_SHORT).show();
        //para vovler al menú tras la compra
        Intent volver = new Intent();
        volver.putExtra("Volver", true);
        setResult(RESULT_OK, volver);
        finish();
    }
}