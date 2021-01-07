/**
 * Partner para quien será el pedido.
 */

package com.example.retocomerciales.Clases;


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

}
