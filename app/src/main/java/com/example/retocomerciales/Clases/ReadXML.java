package com.example.retocomerciales.Clases;

import android.provider.Contacts;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadXML {

    public Producto[] cargaProductos() {

        Producto[] listProducto;

        SAXBuilder builder = new SAXBuilder();
        File xml = new File("productos.xml");
        Document document = null;
        try {
            document = builder.build(xml);
        } catch (JDOMException | IOException e) {
            System.out.println("Error al encontrar el xml interno");
        }
        Element root = document.getRootElement();

        List<Element> list = root.getChildren("catalogo");
        listProducto = new Producto[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Element coche = list.get(i);
            List<Element> valores_coche = coche.getChildren();

            String cod, nombre, descripcion, imagen;
            float pr_unidad;
            int existencias;

            for (int j = 0; j < valores_coche.size(); j++) {

                Element campo = valores_coche.get(j);

                cod = campo.getAttributeValue("cod");
                nombre = campo.getChildTextTrim("nombre");
                descripcion = campo.getChildTextTrim("descripcion");
                imagen = campo.getChildTextTrim("imagen");


                try {
                    pr_unidad = Float.parseFloat(campo.getChildTextTrim("precio"));
                } catch (Exception e) {
                    pr_unidad = 0f;
                }

                try {
                    existencias = Integer.parseInt(campo.getChildTextTrim("existencias"));
                } catch (Exception e) {
                    existencias = 0;
                }

                if (descripcion.length()==0){
                    listProducto[j] = new Producto(cod, nombre, imagen, pr_unidad, existencias);
                }else{
                    listProducto[j] = new Producto(cod, nombre, descripcion, imagen, pr_unidad, existencias);
                }
            }
        }
        return listProducto;
    }
}
