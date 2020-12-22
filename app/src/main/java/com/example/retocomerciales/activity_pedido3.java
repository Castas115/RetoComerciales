package com.example.retocomerciales;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;

import org.w3c.dom.Text;

public class activity_pedido3 extends AppCompatActivity {

    private ListView carroProductos;
    Datos datos;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido3);

        carroProductos = findViewById(R.id.lsv_listaProductos);

        datos = Datos.getInstance();

        final LineaListAdapter adapter = new LineaListAdapter(this, R.layout.list_item_productos, datos.getPedido().getLineas());
        carroProductos.setAdapter(adapter);

    }
}