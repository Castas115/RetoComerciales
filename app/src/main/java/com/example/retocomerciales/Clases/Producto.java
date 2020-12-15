package com.example.retocomerciales.Clases;

import java.io.Serializable;

/**
 * Cada producto individual. Dato cargado del XML/base de datos
 */

public class Producto implements Serializable {
    final static long serialVersionUID = 1L;

    //FALTA EXISTENCIAS
    String cod, nombre, descripcion, imagen;
    int existencias;
    float pr_unidad;

    //Constructor

    public Producto(String cod, String nombre, String imagen, float pr_unidad, int existencias) {
        this.cod = cod;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = "El innovador " + nombre + " de Pistacho.";
        this.pr_unidad = pr_unidad;
        this.existencias = existencias;
    }


    public Producto(String cod, String nombre, String descripcion, String imagen, float pr_unidad, int existencias) {
        this.cod = cod;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.pr_unidad = pr_unidad;
        this.existencias = existencias;
    }

    //Getters
    public String getCod() {return cod;}
    public String getNombre() {return nombre;}
    public String getImagen() {return imagen;}
    public float getPr_unidad() {return pr_unidad;}
    public String getDescripcion() {return descripcion;}
    public int getExistencias() {return existencias;}

    //Setter
    public void setExistencias(int existencias) {this.existencias = existencias;}
    public void restaExistencias(int existenciasRestadas) {
        this.existencias = this.existencias - existenciasRestadas;
    }

}
