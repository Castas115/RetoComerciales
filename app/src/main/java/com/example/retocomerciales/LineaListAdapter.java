package com.example.retocomerciales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Linea;
import com.example.retocomerciales.Clases.Producto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class LineaListAdapter extends ArrayAdapter<Linea> {

    private final static String TAG = "LineaListAdapter";
    private Context context;
    int resource;
    Datos datos;

    DecimalFormat formatoDecimal;


    public LineaListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Linea> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        Producto producto = Objects.requireNonNull(getItem(pos)).getProducto();
        int cantidad = Objects.requireNonNull(getItem(pos)).getCantidad();
        //String prTotal = getItem(pos).getPr_total() + "€";

        Linea linea =new Linea(producto, cantidad);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView nombreProd = convertView.findViewById(R.id.lsv_lbl_nomArt);
        TextView cantidadProd = convertView.findViewById(R.id.lsv_lbl_cantArt);
        TextView prTotalProd = convertView.findViewById(R.id.lsv_lbl_prTotalArt);
        Button borrar = convertView.findViewById(R.id.btn_borrar);
        datos = Datos.getInstance();

        //listener del boton borrar
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estas seguro?")
                        .setMessage("¿Quieres eliminar este producto de la lista?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datos.getPedido().deleteLinea(pos);
                                LineaListAdapter.super.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        nombreProd.setText(linea.getProducto().getNombre());
        cantidadProd.setText(linea.getCantidad() + " unidades");

        formatoDecimal = new DecimalFormat("0.00");
        formatoDecimal.setRoundingMode(RoundingMode.DOWN);
        prTotalProd.setText(formatoDecimal.format(Double.parseDouble(formatoDecimal.format( linea.getPr_unidad() * (double) linea.getCantidad()))) + " €");

        return convertView;

    }
}
