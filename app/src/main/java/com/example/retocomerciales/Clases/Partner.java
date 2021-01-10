/**
 * Partner para quien será el pedido.
 */

package com.example.retocomerciales.Clases;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class Partner {

    private String id, nombre, direccion, Poblacion, CIF, telefono, email, idComercial;

    //Constructor
    public Partner(String id, String nombre, String direccion, String CIF, String Poblacion, String telefono, String email, String idComercial) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.CIF = CIF;
        this.Poblacion = Poblacion;
        this.telefono = telefono;
        this.email = email;
        this.idComercial = idComercial;
    }

    //Getters

    public String getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDireccion() {return direccion;}
    public String getCIF() {return CIF;}
    public String getPoblacion() {return Poblacion;}
    public String getTelefono() {return telefono;}
    public String getEmail() {return email;}

    //convertire el contenido de Partners en un elemento XML
    public Element toElement(Document document) {
        ArrayList<Element> elements = new ArrayList<>(Arrays.asList(
                document.createElement("partner"),
                document.createElement("nombre"),
                document.createElement("direccion"),
                document.createElement("poblacion"),
                document.createElement("CIF"),
                document.createElement("telefono"),
                document.createElement("email"),
                document.createElement("id_comercial")
        ));

        elements.get(0).setAttribute("id_partner", id);
        elements.get(1).setTextContent(nombre);
        elements.get(2).setTextContent(direccion);
        elements.get(3).setTextContent(Poblacion);
        elements.get(4).setTextContent(CIF);
        elements.get(5).setTextContent(telefono);
        elements.get(6).setTextContent(email);
        elements.get(7).setTextContent(idComercial);

        //el elemento 0 es el elemento padre
        for(int i = 1; i < elements.size(); i++) {
            elements.get(0).appendChild(elements.get(i));
        }
        return elements.get(0);
    }

}
