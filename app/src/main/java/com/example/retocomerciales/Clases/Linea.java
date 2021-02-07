package com.example.retocomerciales.Clases;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * La línea de cada producto con su cantidad y precio total (lo que aparecerá en el pedido)
 */
public class Linea {
    private Producto producto;
    private int cantidad;
    private double pr_unidad;
    private static DecimalFormat formatoDecimal;

    //constructor (el precio total se calcula con el precio_unidad del producto y la cantidad
    public Linea(Producto producto, int cantidad) {

        DecimalFormatSymbols sinbolos = new DecimalFormatSymbols();
        sinbolos.setDecimalSeparator('.');
        formatoDecimal = new DecimalFormat("#.##", sinbolos);
        formatoDecimal.setRoundingMode(RoundingMode.DOWN);

        this.producto = producto;
        this.cantidad = cantidad;
        this.pr_unidad = producto.getPr_unidad();
    }

    //Getter
    public Producto getProducto() {return producto;}
    public int getCantidad() {return cantidad;}
    public double getPr_unidad() {return pr_unidad;}

    //Setter de la cantidad (actualiza el precio)
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    //para pruebas
    public  void imprimir(){
        System.out.println("linea: ");
        System.out.println(" - codProd: " + this.producto.getCod());
        System.out.println(" - nomProd: " + this.producto.getNombre());
        System.out.println(" - Pr unidad: " + this.producto.getPr_unidad());
        System.out.println(" - cantidad: " + this.cantidad);
        System.out.println(" - Pr total: " + Double.parseDouble(formatoDecimal.format( this.pr_unidad * (double) cantidad)));
    }
}