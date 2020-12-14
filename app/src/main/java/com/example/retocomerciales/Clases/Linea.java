package com.example.retocomerciales.Clases;

/**
 * La línea de cada producto con su cantidad y precio total (lo que aparecerá en el pedido)
 */
public class Linea {
    Producto producto;
    int cantidad;
    double pr_total;


    //constructor (el precio total se calcula con el precio_unidad del producto y la cantidad
    public Linea(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pr_total = producto.pr_unidad * cantidad;
    }

    //Getter
    public Producto getProducto() {return producto;}
    public int getCantidad() {return cantidad;}
    public double getPr_total() {return pr_total;}

    //Setter de la cantidad (actualiza el precio)
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
        this.pr_total = (double) (producto.pr_unidad * cantidad);
    }

    //para pruebas
    public  void imprimir(){
        System.out.println("linea: ");
        System.out.println(" - codProd: " + this.producto.getCod());
        System.out.println(" - nomProd: " + this.producto.getNombre());
        System.out.println(" - Pr unidad: " + this.producto.getPr_unidad());
        System.out.println(" - cantidad: " + this.cantidad);
        System.out.println(" - Pr total: " + this.pr_total);
    }
}
