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
import com.example.retocomerciales.Clases.DescripcionPedido;

import java.util.ArrayList;
import java.util.List;

public class DescipcionPedidoListAdapter extends ArrayAdapter<DescripcionPedido> {

    private final static String TAG = "PedidoListAdapter";
    private Context context;
    int resource;

    public DescipcionPedidoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DescripcionPedido> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView fecha = convertView.findViewById(R.id.lsv_lbl_fecha);
        TextView nomPartner = convertView.findViewById(R.id.lbl_nombrePartner);
        TextView prTotal = convertView.findViewById(R.id.lbl_prTotalPedido);
        Button borrar = convertView.findViewById(R.id.btn_borrar);

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
                                //borar de instancia y de Base de datos
                                //Datos.getInstance().borrarPedido(which);
                                DescipcionPedidoListAdapter.super.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });
        DescripcionPedido desc = getItem(pos);
        fecha.setText(desc.getFecha());
        nomPartner.setText(desc.getNombrePartner());
        prTotal.setText(desc.getPrTotal());

        return convertView;
    }
}
