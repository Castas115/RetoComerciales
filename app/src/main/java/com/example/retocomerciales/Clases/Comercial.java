/**
 * El comercial, el que inicia sesión en la app
 */

package com.example.retocomerciales.Clases;

public class Comercial  {

    private String id, email, nombre, apellidos, delegacion, telefonoDelegacion, emailDelegacion;

    //constructor
    public Comercial(String id, String email, String nombre, String apellidos, String delegacion, String telefonoDelegacion, String emailDelegacion) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.delegacion = delegacion;
        this.telefonoDelegacion = telefonoDelegacion;
        this.emailDelegacion = emailDelegacion;
    }

    //getters

    public String getId() {return id;}
    public String getEmail() {return email;}
    public String getNombre() {return nombre;}
    public String getApellidos() {return apellidos;}
    public String getDelegacion() {return delegacion;}
    public String getTelefonoDelegacion() {return telefonoDelegacion;}
    public String getEmailDelegacion() {return emailDelegacion;}
}
