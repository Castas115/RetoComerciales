package com.example.retocomerciales;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

public class activity_pedido3 extends AppCompatActivity {

    private ListView carroProductos;
    private Spinner spnPartners;
    private Datos datos;
    private Button confirmar, volver;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido3);

        carroProductos = findViewById(R.id.lsv_listaProductos);
        confirmar = findViewById(R.id.btn_terminarPedido);
        volver = findViewById(R.id.btn_volver);
        spnPartners = findViewById(R.id.spn_partners);

        datos = Datos.getInstance();

        final LineaListAdapter adapter = new LineaListAdapter(this, R.layout.list_item_productos, datos.getPedido().getLineas());
        carroProductos.setAdapter(adapter);


        //Spinner partnerss
        final ArrayAdapter adapterPartners = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresPartners());
        adapterPartners.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPartners.setAdapter(adapterPartners);
            //partner seleccionado anteriormente seleccionado por defecto
        int num = datos.getPosPartner();
        spnPartners.setSelection(datos.getPosPartner());

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarPedido();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnPartners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datos.getPedido().setPartner(datos.getPartner(position));
                Toast.makeText(getApplicationContext(), "Partner cambiado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void realizarPedido(){
        //introducir escritura en xml

        Toast.makeText(getApplicationContext(), "Compra realizada", Toast.LENGTH_SHORT).show();
        //para vovler al menú tras la compra
        Intent volver = new Intent();
        volver.putExtra("Volver",true);
        setResult(RESULT_OK, volver);
        finish();
    }
}