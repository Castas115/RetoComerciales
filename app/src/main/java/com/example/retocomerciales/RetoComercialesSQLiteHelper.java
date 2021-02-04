package com.example.retocomerciales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Producto;

public class RetoComercialesSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear las tablas
    String sqlComerciales = "CREATE TABLE COMERCIALES (id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT, password TEXT, nombre TEXT, apellidos TEXT,email TEXT,delegacion TEXT, telefono_delegacion TEXT, email_delegacion TEXT, loggeado INTEGER DEFAULT 0)";

    String sqlLineas = "CREATE TABLE LINEAS (id_pedido INTEGER, cod_producto TEXT, cantidad INTEGER, pr_unidad DECIMAL(10,2), PRIMARY KEY (id_pedido,cod_producto), FOREIGN KEY (cod_producto) REFERENCES PRODUCTOS(cod_productos))";

    String sqlPartners = "CREATE TABLE PARTNERS (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, direccion TEXT, cif TEXT, telefono TEXT, email TEXT, id_comercial INTEGER,FOREIGN KEY (id_comercial) REFERENCES COMERCIALES(id))";

    String sqlPedidos = "CREATE TABLE PEDIDOS (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha DATE, id_partner INTEGER, id_comercial INTEGER ,FOREIGN KEY (id_comercial) REFERENCES COMERCIALES(id),FOREIGN KEY (id_partner) REFERENCES PARTNERS(id))";

    String sqlProductos = "CREATE TABLE PRODUCTOS (cod_producto TEXT PRIMARY KEY, nombre TEXT, descripcion TEXT, imagen TEXT, existencias INTEGER, pr_unidad DECIMAL(10,2))";

    public RetoComercialesSQLiteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlComerciales);
        db.execSQL(sqlLineas);
        db.execSQL(sqlPartners);
        db.execSQL(sqlPedidos);
        db.execSQL(sqlProductos);
        Datos.getInstance().crearDB();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}