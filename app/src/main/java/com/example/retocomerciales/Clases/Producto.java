package com.example.retocomerciales.Clases;

import java.io.Serializable;

/**
 * Cada producto individual. Dato cargado del XML/base de datos
 */

public class Producto implements Serializable {
    final static long serialVersionUID = 1L;

    //FALTA EXISTENCIAS
    String cod, nombre, descripcion, imagen;
    int existencias, existenciasCompra; //existenciasCompra son las existencias que el producto tiene mientras se realiza una compra
    float pr_unidad;

    //Constructor

    public Producto(String cod, String nombre, String imagen, float pr_unidad, int existencias) {
        this.cod = cod;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = "El innovador " + nombre + " de Pistacho.";
        this.pr_unidad = pr_unidad;
        this.existencias = existencias;
        this.existenciasCompra = existencias;
    }


    public Producto(String cod, String nombre, String descripcion, String imagen, float pr_unidad, int existencias) {
        this.cod = cod;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.pr_unidad = pr_unidad;
        this.existencias = existencias;
        this.existenciasCompra = existencias;
    }

    //Getters
    public String getCod() {return cod;}
    public String getNombre() {return nombre;}
    public String getImagen() {return imagen;}
    public float getPr_unidad() {return pr_unidad;}
    public String getDescripcion() {return descripcion;}
    public int getExistencias() {return existencias;}
    public int getExistenciasCompra() {return existenciasCompra;}

    //Setter
    public void setExistenciasCompra(int existencias) {this.existenciasCompra = existencias;}
    public void restaExistencias(int existenciasRestadas) {
        this.existenciasCompra = this.existenciasCompra - existenciasRestadas;
    }

    //ajustan las existencias si el pedido se ha realizado o si no se ha podido realizar
    public void ajustarExistencias(){
        this.existencias = this.existenciasCompra;
    }
    public void ajustarExistenciasCompra(){
        this.existenciasCompra = this.existencias;
    }

}
