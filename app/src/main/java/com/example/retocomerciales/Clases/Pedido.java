/**
 * Pedido final. Tras haberse creado correctamente exportará los datos a un XML de pedidos
 */
package com.example.retocomerciales.Clases;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class Pedido  {

    private String fecha;
    private Partner partner;
    private Comercial comercial;
    private ArrayList<Linea> lineas;

    //constructor por defecto con comercial
    public Pedido(Comercial comercial) {
        this.comercial = comercial;
        this.lineas = new ArrayList<>();
    }

    //constructor (el array list empieza vacio)
    public Pedido(String fecha, Partner partner, Comercial comercial) {
        this.fecha = fecha;
        this.partner = partner;
        this.comercial = comercial;
        this.lineas = new ArrayList<>();
    }


    //convertire el contenido de Partners en un elemento XML
    public Element toElement(Document document) {
        ArrayList<Element> elements = new ArrayList<>(Arrays.asList(
                document.createElement("pedido")
        ));

        elements.get(0).setAttribute("fecha", fecha);
        elements.get(0).setAttribute("idcomercial", comercial.getId());
        elements.get(0).setAttribute("idpartner", partner.getId());
        for(int i=1; i<=lineas.size(); i++) {
            elements.add(document.createElement("linea"));
            elements.get(i).setAttribute("cantidad", String.valueOf(lineas.get(i-1).getCantidad()));
            elements.get(i).setAttribute("codArticulo", lineas.get(i-1).getProducto().getCod());
            elements.get(i).setAttribute("precioLinea", String.valueOf(lineas.get(i-1).getPr_total()));

            elements.get(0).appendChild(elements.get(i));
        }

        return elements.get(0);
    }

    //Getters
    public String getFecha() {return fecha;}
    public Partner getPartner() {return partner;}
    public Comercial getComercial() {return comercial;}
    public ArrayList<Linea> getLineas() {return lineas;}
    public Linea getLinea(int i){ return lineas.get(i);}

    //Setters
    //public void setComercial(Comercial comercial) {this.comercial = comercial;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public void setPartner(Partner partner) {this.partner = partner;}

    //borrarLinea
    public void deleteLinea(int pos) {this.lineas.remove(pos);}

    /**Añadir una linea. Para que solo haya una linea para cada producto se comprueba si existe este producto, si es así se suma la cantidad de la linea a introducir a la linea existente
     *
     * @param linea: linea a añadir
     */
    public void addLinea(Linea linea) {
        int posProd = existeProd(linea.getProducto());
        if (posProd < 0){
            lineas.add(linea);
        }else{
            setLineaCantidad(posProd, this.lineas.get(posProd).getCantidad() + linea.getCantidad());
        }
    }

    /**Editar cantidad de una linea
     *
     * @param pos: posición de la linea
     * @param cantidad: cantidad que se quiere asignar a la linea
     */
    public void setLineaCantidad(int pos, int cantidad) {
        this.lineas.get(pos).setCantidad(cantidad);
    }

    /**Comprobar si existe una linea con el producto.
     *
     * @param prod: producto que se quere comprobar
     * @returm posición del producto existente (-1 si no coincide).
     * */
    public int existeProd(Producto prod){
        for(int i=0; i<this.getLineas().size(); i++){
            if (this.getLineas().get(i).getProducto().getCod() == prod.getCod()){
                return i;
            }
        }
        return -1;
    }
}