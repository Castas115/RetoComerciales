package com.example.retocomerciales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.DescripcionPedido;

import java.util.ArrayList;

public class activity_visualizaPedidos extends AppCompatActivity {
    Datos datos;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_visualiza_pedidos);

       datos = Datos.getInstance();
       Button volver = findViewById(R.id.btn_volver);
       ListView listPedidos = findViewById(R.id.lsv_pedidos);
       TextView nomComercial = findViewById(R.id.lbl_nomComercial);

       nomComercial.setText("Pedidos de " + datos.getComercial().getNombre() + datos.getComercial().getApellidos());
       //adapter creado por nosotros aplicado al listview
       final DescipcionPedidoListAdapter adapter = new DescipcionPedidoListAdapter(this, R.layout.list_item_resumen_pedido, Datos.getInstance().cargarListaPedidos());
       listPedidos.setAdapter(adapter);

       volver.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
            finish();
           }
       });
    }

}