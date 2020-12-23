/**
 * Partner para quien será el pedido.
 */

package com.example.retocomerciales.Clases;

import java.io.Serializable;

public class Partner implements Serializable {

    final static long serialVersionUID = 1l;
    private String id, nombre, direccion,Poblacion, CIF, telefono, email;

    //Constructor
    public Partner(String id, String nombre, String direccion, String CIF,String Poblacion, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.CIF = CIF;
        this.Poblacion = Poblacion;
        this.telefono = telefono;
        this.email = email;
    }

    //Getters

    public String getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDireccion() {return direccion;}
    public String getCIF() {return CIF;}
    public String getPoblacion() {return Poblacion;}
    public String getTelefono() {return telefono;}
    public String getEmail() {return email;}

}
