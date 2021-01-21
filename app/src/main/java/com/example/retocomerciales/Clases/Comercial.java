/**
 * El comercial, el que inicia sesión en la app
 */

package com.example.retocomerciales.Clases;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class Comercial  {

    private String id, usuario, password, email, nombre, apellidos, delegacion, telefonoDelegacion, emailDelegacion;

    //constructor
    public Comercial(String id, String usuario, String password, String email, String nombre, String apellidos, String delegacion, String telefonoDelegacion, String emailDelegacion) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.delegacion = delegacion;
        this.telefonoDelegacion = telefonoDelegacion;
        this.emailDelegacion = emailDelegacion;
    }

    //getters

    public String getId() {return id;}
    public String getUsuario() {return usuario;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public String getNombre() {return nombre;}
    public String getApellidos() {return apellidos;}
    public String getDelegacion() {return delegacion;}
    public String getTelefonoDelegacion() {return telefonoDelegacion;}
    public String getEmailDelegacion() {return emailDelegacion;}

    //convertire el contenido de Comerciales en un elemento XML
    /*public Element toElement(Document document) {
        ArrayList<Element> elements = new ArrayList<>(Arrays.asList(
                document.createElement("comercial"),
                document.createElement("usuario"),
                document.createElement("password"),
                document.createElement("email"),
                document.createElement("nombre"),
                document.createElement("apellidos"),
                document.createElement("delegacion"),
                document.createElement("telefono"),
                document.createElement("emailDelegacion")

        ));

        elements.get(0).setAttribute("id", id);
        elements.get(1).setTextContent(usuario);
        elements.get(2).setTextContent(password);
        elements.get(3).setTextContent(email);
        elements.get(4).setTextContent(nombre);
        elements.get(5).setTextContent(apellidos);
        elements.get(6).setTextContent(delegacion);
        elements.get(7).setTextContent(telefonoDelegacion);
        elements.get(8).setTextContent(emailDelegacion);


        //el elemento 0 es el elemento padre
        for(int i = 1; i < elements.size(); i++) {
            elements.get(0).appendChild(elements.get(i));
        }
        return elements.get(0);
    }*/
}
