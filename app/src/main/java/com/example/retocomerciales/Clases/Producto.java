package com.example.retocomerciales.Clases;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cada producto individual. Dato cargado del XML/base de datos
 */

public class Producto implements Serializable {
    final static long serialVersionUID = 1L;

    private String cod, nombre, descripcion, imagen;
    private int existencias, existenciasCompra; //existenciasCompra son las existencias que el producto tiene mientras se realiza una compra
    private float pr_unidad;

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
    public void setExistencias(int existencias) {this.existencias = existencias; }

    //ajustan las existencias si el pedido se ha realizado o si no se ha podido realizar
    public void ajustarExistencias(){
        this.existencias = this.existenciasCompra;
    }
    public void ajustarExistenciasCompra(){
        this.existenciasCompra = this.existencias;
    }

    //convertire el contenido de Partners en un elemento XML
    public Element toElement(Document document) {
        ArrayList<Element> elements = new ArrayList<>(Arrays.asList(
                document.createElement("producto"),
                document.createElement("nombre"),
                document.createElement("imagen"),
                document.createElement("descripcion"),
                document.createElement("precio"),
                document.createElement("existencias")
        ));

        elements.get(0).setAttribute("cod", cod);
        elements.get(1).setTextContent(nombre);
        elements.get(2).setTextContent(imagen);
        elements.get(3).setTextContent(descripcion);
        elements.get(4).setTextContent(String.valueOf(pr_unidad));
        elements.get(5).setTextContent(String.valueOf(existencias));

        //el elemento 0 es el elemento padre
        for(int i = 1; i < elements.size(); i++) {
            elements.get(0).appendChild(elements.get(i));
        }
        return elements.get(0);
    }


}
