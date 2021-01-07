package com.example.retocomerciales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retocomerciales.Clases.Datos;
import com.example.retocomerciales.Clases.Partner;

import javax.xml.parsers.ParserConfigurationException;

public class activity_addPartner extends AppCompatActivity {

    EditText nombrePartner, direccionPartner, poblacionPartner, cifPartner, telefonoPartner, emailPartner;
    Button anadir, volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_partner);
        nombrePartner = findViewById(R.id.tbNombreAddPartner);
        direccionPartner = findViewById(R.id.tbDireccionAddPartner);
        poblacionPartner = findViewById(R.id.tbPoblacionAddPartner);
        cifPartner = findViewById(R.id.tbCifAddPartner);
        telefonoPartner = findViewById(R.id.tbTelefonoAddPartner);
        emailPartner = findViewById(R.id.tbEmailAddPartner);
        anadir = findViewById(R.id.btnAnadir);
        volver = findViewById(R.id.btnVolver2);

        final Datos datos = Datos.getInstance();

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombrePartner.getText().toString().isEmpty() || !direccionPartner.getText().toString().isEmpty() || !cifPartner.getText().toString().isEmpty() || !poblacionPartner.getText().toString().isEmpty() || !telefonoPartner.getText().toString().isEmpty() || !emailPartner.getText().toString().isEmpty()){

                    new Partner("4", nombrePartner.toString(), direccionPartner.toString(), cifPartner.toString(), poblacionPartner.toString() ,telefonoPartner.toString(), emailPartner.toString(), "0");
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Añadido", Toast.LENGTH_SHORT);
                    toast1.show();
                    try {
                        datos.anadirPartner(R.raw.newpartners, new Partner("4", nombrePartner.toString(), direccionPartner.toString(), cifPartner.toString(), poblacionPartner.toString() ,telefonoPartner.toString(), emailPartner.toString(), "0"));
                    } catch (JDOMException | IOException | ParserConfigurationException | SAXException e) {
                        Toast toast2 =
                                Toast.makeText(getApplicationContext(),
                                        "Error", Toast.LENGTH_SHORT);

                        toast2.show();
                    }

                }else{

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Hay campos vacios!", Toast.LENGTH_SHORT);

                    toast1.show();

                }

            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /*public static void anadirPartner(String id, String nombre, String direccion, String cif, String poblacion, String telefono, String email) throws JDOMException, IOException {
        //Lee XML
        SAXBuilder builder = new SAXBuilder();
        File archivo = new File("newpartners.xml");
        Document doc = builder.build(archivo);

        //Obtiene nodo raiz
        Element root = doc.getRootElement();


        //Añade un nuevo nodo al nodo raiz
        Element partner = new Element("partner");
        //newChild.setText("partner");
        root.addContent(partner);


        //Añadir los elementos del partner.

        partner.setAttribute("id", id);

        Element nom = new Element("nombre");
        partner.addContent(nom);
        nom.setText(nombre);
        Element dir = new Element("direccion");
        partner.addContent(dir);
        dir.setText(direccion);
        Element pob = new Element("poblacion");
        partner.addContent(pob);
        pob.setText(poblacion);
        Element CIF = new Element("CIF");
        partner.addContent(CIF);
        CIF.setText(cif);
        Element tel = new Element("telefono");
        partner.addContent(tel);
        tel.setText(telefono);
        Element mail = new Element("email");
        partner.addContent(mail);
        mail.setText(email);
        Element id_comercial = new Element("id_comercial");
        partner.addContent(id_comercial);
        id_comercial.setText("1");//cambiar



        //Crea un fichero XML
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        outputter.output(doc, new FileWriter(archivo));
    }*/
}