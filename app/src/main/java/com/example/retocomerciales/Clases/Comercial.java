/**
 * El comercial, el que inicia sesión en la app
 */

package com.example.retocomerciales.Clases;

import java.io.Serializable;

public class Comercial implements Serializable {

    final static long serialVersionUID = 1l;
    private String email, nombre, apellidos, delegacion, telefonoDelegacion, emailDelegacion;

    //constructor
    public Comercial(String email, String nombre, String apellidos, String delegacion, String telefonoDelegacion, String emailDelegacion) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.delegacion = delegacion;
        this.telefonoDelegacion = telefonoDelegacion;
        this.emailDelegacion = emailDelegacion;
    }

    //getters
    public String getEmail() {return email;}
    public String getNombre() {return nombre;}
    public String getApellidos() {return apellidos;}
    public String getDelegacion() {return delegacion;}
}
