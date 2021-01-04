package com.example.retocomerciales;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Linea;
import com.example.retocomerciales.Clases.Partner;
import com.example.retocomerciales.Clases.Producto;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class activity_pedido2 extends AppCompatActivity {


    //info que viene del intentç
    Partner partner;

    Spinner spinnerProductos;
    EditText prUnidad, descripcion, prTotal, unidades;
    TextView stock;
    ImageView imagen;
    Button addToPedido, volver, siguiente;
    Intent intent;
    Datos datos;

    private static DecimalFormat formatoDecimal;

    private int posicionProductoEnLista;//posición del producto elegido en el spinneer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido2);

        volver  = findViewById(R.id.btn_volver);
        siguiente  = findViewById(R.id.btn_siguiente);
        spinnerProductos = findViewById(R.id.spinner);
        prUnidad = findViewById(R.id.txt_precioUnidad);
        stock = findViewById(R.id.lbl_stock);
        descripcion = findViewById(R.id.txt_descripcion);
        imagen = findViewById(R.id.imagen);
        prTotal = findViewById(R.id.txt_precioTotal);
        unidades = findViewById(R.id.txt_cantidades);
        addToPedido = findViewById(R.id.btn_addToPedido);

        //el partner del intent del anterior activity (pedido1)

        //instancia de los datos
        datos = Datos.getInstance();

        //datos de los spinners
        final ArrayAdapter adapterPoductos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos.getNombresProductos());
        adapterPoductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductos.setAdapter(adapterPoductos);



        //formato de decimales
        formatoDecimal = new DecimalFormat("0.00");
        formatoDecimal.setRoundingMode(RoundingMode.DOWN);

        //listener del spinner
        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionProductoEnLista = position;

                prUnidad.setText(String.valueOf(datos.getProducto(position).getPr_unidad()) + "€");
                stock.setText("(" + String.valueOf(datos.getProducto(position).getExistenciasCompra()) + " en stock)");
                descripcion.setText(datos.getProducto(position).getDescripcion());
                cambiarImagen(datos.getProducto(position).getImagen());
                calcPrecioTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stock.setText("");
                prUnidad.setText("0€");
            }
        });

        //listener de cambiar unidades
        unidades.addTextChangedListener(new TextWatcher(){

            //cosas de lambda
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) { calcPrecioTotal(); }
        });

        //listener de boton añadir
        addToPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirAPedido();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datos.getPedido().getLineas().size()>0) {
                    intent = new Intent(activity_pedido2.this, activity_pedido3.class);
                    startActivityForResult(intent, 123);
                }else{
                    Toast.makeText(getApplicationContext(), "Añade artículos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //para vovler al menú tras la compra
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent vuelta) {
        super.onActivityResult(requestCode, resultCode, vuelta);
        if (requestCode == 123 && resultCode == RESULT_OK){
            boolean res = vuelta.getExtras().getBoolean("Volver");
            if (res){
                Intent volver = new Intent();
                volver.putExtra("Volver",true);
                setResult(RESULT_OK, volver);
                finish();
            }
        }
    }

    public void cambiarImagen(String nombreImagen){
        switch (nombreImagen){
            case "movil":
                imagen.setImageResource(R.drawable.movil);
                break;

            case "airpodsPistacho":
                imagen.setImageResource(R.drawable.airpodspistacho);
                break;

            case "cargadorPistacho":
                imagen.setImageResource(R.drawable.cargadorpistacho);
                break;


            case "fundaPistacho":
                imagen.setImageResource(R.drawable.fundapistacho);
                break;

            default:
                imagen.setImageResource(R.drawable.x);
                break;

        }
    }

    //metodo para calcular y escribir el precio total
    public void calcPrecioTotal() {
        try {
            float result = datos.getProducto(posicionProductoEnLista).getPr_unidad() * Float.parseFloat(unidades.getText().toString());
            prTotal.setText(String.valueOf(formatoDecimal.format(result)) + "€");
        }catch (Exception e){
            prTotal.setText("0.0€");
        }
    }

    public void anadirAPedido(){
        try {
            int cantidad = Integer.parseInt(unidades.getText().toString());//fallo
            if (cantidad <= datos.getProducto(posicionProductoEnLista).getExistenciasCompra()) {
                datos.getPedido().addLinea(new Linea(datos.getProducto(posicionProductoEnLista), cantidad));
                datos.restaExistenciasCompra(posicionProductoEnLista, cantidad);
                stock.setText("(" + String.valueOf(datos.getProducto(posicionProductoEnLista).getExistenciasCompra()) + " en stock)");
                Toast.makeText(getApplicationContext(), "Artículo añadido", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Cantidad superior al stock", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            //En caso de que no se haya elegido una cantidad
            Toast.makeText(getApplicationContext(), "Elige cantidad", Toast.LENGTH_SHORT).show();
        }
    }
}