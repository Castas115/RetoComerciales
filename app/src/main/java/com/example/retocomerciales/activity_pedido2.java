package com.example.retocomerciales;

/*
falta la recepción de datos del intent del que se abre y el envio en intent + algunos apartados visuales como toasts
 */

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.retocomerciales.Clases.Comercial;
import com.example.retocomerciales.Clases.Linea;
import com.example.retocomerciales.Clases.Partner;
import com.example.retocomerciales.Clases.Pedido;
import com.example.retocomerciales.Clases.Producto;

import java.util.Calendar;

public class activity_pedido2 extends AppCompatActivity {


    //info que viene del intent
    Producto[] listaProductos;
    Partner partner;

    Spinner spinnerProductos;
    Pedido pedido;
    EditText prUnidad, descripcion, prTotal, unidades;
    ImageView imagen;
    Button addToPedido, volver, siguiente;
    Intent intent, extras;

    Producto prod;//producto elegido en el spinner

    float precioUnidad = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pedido2);

        volver  = findViewById(R.id.btn_volver);
        siguiente  = findViewById(R.id.btn_siguiente);
        spinnerProductos = findViewById(R.id.spinner);
        prUnidad = findViewById(R.id.txt_precioUnidad);
        descripcion = findViewById(R.id.txt_descripcion);
        imagen = findViewById(R.id.imagen);
        prTotal = findViewById(R.id.txt_precioTotal);
        unidades = findViewById(R.id.txt_cantidades);
        addToPedido = findViewById(R.id.btn_addToPedido);

        extras = getIntent();
        listaProductos = (Producto[]) extras.getSerializableExtra("listaProductos");
        partner = (Partner) extras.getSerializableExtra("partner");

        //datos de los spinners
        final ArrayAdapter adapterPoductos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getListNombres(listaProductos));
        adapterPoductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductos.setAdapter(adapterPoductos);

        //fecha
        Calendar cal = Calendar.getInstance();
        String fecha = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR); //mes mal


        //Crear pruebas
        pedido = new Pedido(fecha, partner, new Comercial("1", "s", "123 12", "Gipuzkoa"));


        //listener del spinner
        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prod = listaProductos[position];
                precioUnidad = prod.getPr_unidad();

                prUnidad.setText(String.valueOf(precioUnidad) + "€");
                descripcion.setText(prod.getDescripcion());
                cambiarImagen(prod.getImagen());
                calcPrecioTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
                try {
                    pedido.addLinea(new Linea(prod, Integer.parseInt(unidades.getText().toString())));
                    Toast.makeText(getApplicationContext(), "Artículo", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    //En caso de que no se haya elegido una cantidad
                    Toast.makeText(getApplicationContext(), "Elige cantidad", Toast.LENGTH_SHORT).show();
                }
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
                if (pedido.getLineas().size()>0) {
                    intent = new Intent(activity_pedido2.this, activity_pedido3.class);
                    intent.putExtra("listaProductos", listaProductos);
                    intent.putExtra("pedido", pedido);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Añade artículos", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public String[] getListNombres(Producto[] list){
        String[] nombres = new String[list.length];

        for(int i = 0; i < list.length; i++){
            nombres[i] = list[i].getNombre();
        }
        return nombres;
    }

    //metodo para calcular y escribir el precio total
    public void calcPrecioTotal() {
        try {
            float result = precioUnidad * Float.parseFloat(unidades.getText().toString());
            prTotal.setText(String.valueOf(result) + "€");
        }catch (Exception e){
            prTotal.setText("0.0€");
        }
    }

    //metodo para testear
    public void sysoPedido(View v){
        System.out.println("______________________________________________");
        System.out.println("_______________DATOS DE PEDIDO________________");
        System.out.println("______________________________________________");
        System.out.println(" - fecha: " + pedido.getFecha());
        System.out.println(" - comercial: " + pedido.getComercial().getNombre() + " " + pedido.getComercial().getApellidos() + " | " + pedido.getComercial().getEmail() + " | " + pedido.getComercial().getDelegacion());
        System.out.println(" - partner: " +  pedido.getPartner().getCIF() + " " +  pedido.getPartner().getNombre() + " | " +  pedido.getPartner().getEmail() + " | " +  pedido.getPartner().getTelefono());
        System.out.println(" - lineas: ");
        for (int i=0; i < pedido.getLineas().size(); i++){
            System.out.println("    * " + pedido.getLinea(i).getProducto().getCod() + " " + pedido.getLinea(i).getProducto().getNombre() + " | "  + pedido.getLinea(i).getCantidad() + " | "  + pedido.getLinea(i).getPr_total());
        }
    }
}