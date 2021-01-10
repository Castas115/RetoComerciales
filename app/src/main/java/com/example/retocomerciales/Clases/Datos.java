package com.example.retocomerciales.Clases;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Datos {

    private static Datos datos;
    //atributos:
    private Producto[] productos;
    private Partner[] partners;
    private Comercial[] comerciales;
    private Pedido pedido;
    private int posComercial, posPartner;
    private static Resources resources;
    private Context context;
    private File XML_FILE_LOCATION_PATH;


    private Datos(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
        XML_FILE_LOCATION_PATH = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        try {
            loadFilesFromAssetsToLocal();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Datos class error" , "Error a la hora de leer la información desde un XML");
        }
    }

    public static Datos getInstance(Resources resources, Context context) {
        if (datos == null) {
            datos = new Datos(resources, context);
        }
        return datos;
    }

    public static Datos getInstance() {
        return datos;
    }

    //Dato posComercial
    public int getPosComercial() {
        return this.posComercial;
    }

    public void setPosComercial(int posComercial) {
        this.posComercial = posComercial;
    }

    //Dato posPartner
    public int getPosPartner() {
        return this.posPartner;
    }

    public void setPosPartner(int posPartner) {
        this.posPartner = posPartner;
    }

    //dato pedido


    public Pedido getPedido() {
        return pedido;
    }

    public void nuevoPedido(Comercial comercial) {
        pedido = new Pedido(comercial);
    }

    public void realizarPedido(){
        for(Producto prod: productos){
            prod.setExistencias(prod.getExistenciasCompra());
        }
        escribirPedidoDOM();
        escribirProductoDOM();
    }

    /**
     * Métodos para lista de productos
     */
    public Producto[] getProductos() {
        return productos;
    }

    public Producto getProducto(int posicion) {
        return productos[posicion];
    }

    public void restaExistenciasCompra(int posicion, int existenciasRestadas) {
        productos[posicion].setExistenciasCompra(productos[posicion].getExistenciasCompra() - existenciasRestadas);
    }

    public void cargarExistencias() {
        for (Producto producto : productos) {
            producto.setExistenciasCompra(producto.getExistencias());
        }
    }

    /**
     * Métodos para lista de Partners
     */
    public Partner[] getPartners() {
        return partners;
    }

    public Partner getPartner(int posicion) {
        return partners[posicion];
    }

    /**
     * métodos para comerciales
     */
    public Comercial[] getComerciales() {
        return comerciales;
    }

    public Comercial getComercial(int pos) {
        return comerciales[pos];
    }

    public Comercial getComercial() {
        return comerciales[posComercial];
    }

    //devolver lista de strings con los nombres de cada lista (comerciales, partners y productos)
    public String[] getNombresPartners() {
        String[] nombres = new String[this.partners.length];

        for (int i = 0; i < this.partners.length; i++) {
            nombres[i] = this.partners[i].getNombre();
        }
        return nombres;
    }

    public String[] getNombresProductos() {
        String[] nombres = new String[this.productos.length];

        for (int i = 0; i < this.productos.length; i++) {
            nombres[i] = this.productos[i].getNombre();
        }
        return nombres;
    }

    public String[] getNombresComerciales() {
        String[] nombres = new String[this.comerciales.length];

        for (int i = 0; i < this.comerciales.length; i++) {
            nombres[i] = this.comerciales[i].getNombre() + " " + this.comerciales[i].getApellidos();
        }
        return nombres;
    }

    // Escritura en xml //

    //Si no existen los archivos locales se carga el contenido desde assets
    private void loadFilesFromAssetsToLocal() throws IOException {
        String files [] = {"comerciales.xml", "partners.xml", "pedidos.xml", "productos.xml"};
        for(String name : files) {
                File file = new File(XML_FILE_LOCATION_PATH, name);
                if(!file.exists()) {
                    FileOutputStream fos = new FileOutputStream(file);
                    InputStream is = new BufferedInputStream(resources.getAssets().open(name));
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    fos.write(buffer,0,buffer.length);
                }
        }
    }

    public void escribirPartnerDOM(Partner partner) {
        //añadir nuevo partner
        Partner[] newPartners = new Partner[partners.length + 1];
        for(int i = 0; i < partners.length; i++)
            newPartners[i] = partners[i];
        newPartners[newPartners.length - 1] = partner;
        partners= newPartners;

        try {
            //generar el nuevo documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.newDocument();

            //generar el elemento principal
            org.w3c.dom.Element rootElement = document.createElementNS("PistachoPhone", "partners");
            document.appendChild(rootElement);

            //cargar el archivo de destino
            Source source = new DOMSource(document);
            File file = new File(XML_FILE_LOCATION_PATH, "partners.xml");
            Result result = new StreamResult(file);

            //agregar todos los elementos XML al elemento principal
            for (Partner element : partners) {
                rootElement.appendChild(element.toElement(document));
            }

            //escribir el contenido de Document a un archivo local
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Datos class error", "Error a la hora de escribir a un archvo XML");
        }
    }


    public void escribirExistencias() throws JDOMException, IOException, ParserConfigurationException, SAXException {
        Producto[] p = datos.productos;
        try {

            String filePath = Environment.getExternalStorageDirectory() + "/productos.xml";
            File xmlFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();

            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList productos = doc.getElementsByTagName("producto");
            org.w3c.dom.Element producto;
            // loop for each producto
            for (int i = 0; i < productos.getLength(); i++) {
                producto = (org.w3c.dom.Element) productos.item(i);
                Node exist = producto.getElementsByTagName("existencias").item(0).getFirstChild();
                exist.setTextContent(String.valueOf(p[i].getExistencias()));

            }

            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Environment.getExternalStorageDirectory() + "/productos.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    private void escribirPedidoDOM(){
        try {
            //generar el nuevo documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.newDocument();

            //generar el elemento principal
            org.w3c.dom.Element rootElement = document.createElementNS("PistachoPhone", "pedidos");
            document.appendChild(rootElement);

            //cargar el archivo de destino
            Source source = new DOMSource(document);
            File file = new File(XML_FILE_LOCATION_PATH, "pedidos.xml");
            Result result = new StreamResult(file);

            //agregar todos los elementos XML al elemento principal
            rootElement.appendChild(pedido.toElement(document));

            //escribir el contenido de Document a un archivo local
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Datos class error", "Error a la hora de escribir a un archvo XML");
        }
    }

    private void escribirProductoDOM(){
        try {
            //generar el nuevo documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.newDocument();

            //generar el elemento principal
            org.w3c.dom.Element rootElement = document.createElementNS("PistachoPhone", "producto");
            document.appendChild(rootElement);

            //cargar el archivo de destino
            Source source = new DOMSource(document);
            File file = new File(XML_FILE_LOCATION_PATH, "productos.xml");
            Result result = new StreamResult(file);

            //agregar todos los elementos XML al elemento principal
            for (Producto element : productos) {
                rootElement.appendChild(element.toElement(document));
            }

            //escribir el contenido de Document a un archivo local
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Datos class error", "Error a la hora de escribir a un archvo XML");
        }
    }

    // lectura xmls //
    private static org.w3c.dom.Document readXml(InputStream is) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        // dbf.setCoalescing(true);
        // dbf.setExpandEntityReferences(true);

        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        db.setEntityResolver(new NullResolver());

        // db.setErrorHandler( new MyErrorHandler());

        return db.parse(is);
    }

    private Producto[] leeProductos(String fileName) {
        Producto[] listProducto = null;

        try {
            DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(XML_FILE_LOCATION_PATH, fileName);
            org.w3c.dom.Document document = builder.parse(new FileInputStream(file));
            org.w3c.dom.Element root = document.getDocumentElement();

            NodeList list = document.getElementsByTagName("producto");
            listProducto = new Producto[list.getLength()];

            String cod = "", nombre = "", descripcion = "", imagen = "";
            float pr_unidad = 0f;
            int existencias = 0;

            for (int i = 0; i < list.getLength(); i++) {
                org.w3c.dom.Element elementosProducto = (org.w3c.dom.Element) list.item(i);
                cod = elementosProducto.getAttribute("cod");

                Node nNombre = elementosProducto.getElementsByTagName("nombre").item(0).getFirstChild();
                nombre = nNombre.getNodeValue();

                try {
                    Node nDescripcion = elementosProducto.getElementsByTagName("descripcion").item(0).getFirstChild();
                    descripcion = nDescripcion.getNodeValue();
                } catch (Exception e) {
                    descripcion = "";
                }

                Node nImagen = elementosProducto.getElementsByTagName("imagen").item(0).getFirstChild();
                imagen = nImagen.getNodeValue();


                try {
                    Node iPr_unidad = elementosProducto.getElementsByTagName("precio").item(0).getFirstChild();
                    pr_unidad = Float.parseFloat(iPr_unidad.getNodeValue());
                } catch (Exception e) {
                    pr_unidad = 0f;
                }

                try {
                    Node iExistencias = elementosProducto.getElementsByTagName("existencias").item(0).getFirstChild();
                    existencias = Integer.parseInt(iExistencias.getNodeValue());
                } catch (Exception e) {
                    existencias = 0;
                }

                if (descripcion.length() == 0) {
                    listProducto[i] = new Producto(cod, nombre, imagen, pr_unidad, existencias);
                } else {
                    listProducto[i] = new Producto(cod, nombre, descripcion, imagen, pr_unidad, existencias);
                }
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("Error al encontrar el xml interno");
        }

        return listProducto;
    }

    private Partner[] leePartners(String fileName){
        Partner[] listPartners = null;

        try {
            DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(XML_FILE_LOCATION_PATH, fileName);
            org.w3c.dom.Document document = builder.parse(new FileInputStream(file));
            org.w3c.dom.Element root = document.getDocumentElement();

            NodeList list = document.getElementsByTagName("partner");
            listPartners = new Partner[list.getLength()];

            String id = "", nombre = "", direccion = "", poblacion = "", cif = "", telefono = "", email = "", idComercial = "";

            for (int i = 0; i < list.getLength(); i++) {
                org.w3c.dom.Element elementosPartner = (org.w3c.dom.Element) list.item(i);


                id = elementosPartner.getAttribute("id_partner");

                Node nNombre = elementosPartner.getElementsByTagName("nombre").item(0).getFirstChild();
                nombre = nNombre.getNodeValue();
                Node nDireccion = elementosPartner.getElementsByTagName("direccion").item(0).getFirstChild();
                direccion = nDireccion.getNodeValue();
                Node nPoblacion = elementosPartner.getElementsByTagName("poblacion").item(0).getFirstChild();
                poblacion = nPoblacion.getNodeValue();
                Node nCIF = elementosPartner.getElementsByTagName("CIF").item(0).getFirstChild();
                cif = nCIF.getNodeValue();
                Node nTelefono = elementosPartner.getElementsByTagName("telefono").item(0).getFirstChild();
                telefono = nTelefono.getNodeValue();
                Node nEmail = elementosPartner.getElementsByTagName("email").item(0).getFirstChild();
                email = nEmail.getNodeValue();
                Node nIdComercial = elementosPartner.getElementsByTagName("id_comercial").item(0).getFirstChild();
                idComercial = nIdComercial.getNodeValue();

                listPartners[i] = new Partner(id, nombre, direccion, poblacion, cif, telefono, email, idComercial);
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("Error");
        }
        return listPartners;
    }

    private Comercial[] leeComerciales(String fileName) {
        Comercial[] listComercial = null;

        try {

            DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(XML_FILE_LOCATION_PATH, fileName);
            org.w3c.dom.Document document = builder.parse(new FileInputStream(file));
            org.w3c.dom.Element root = document.getDocumentElement();


            NodeList list = document.getElementsByTagName("comercial");
            listComercial = new Comercial[list.getLength()];

            String id = "", email, nombre = "", apellidos = "", delegacion = "", telefono = "", emailDelegacion = "";

            for (int i = 0; i < list.getLength(); i++) {
                org.w3c.dom.Element elementosComercial = (org.w3c.dom.Element) list.item(i);


                id = elementosComercial.getAttribute("id");


                Node nEmail = elementosComercial.getElementsByTagName("email").item(0).getFirstChild();
                email = nEmail.getNodeValue();
                Node nNombre = elementosComercial.getElementsByTagName("nombre").item(0).getFirstChild();
                nombre = nNombre.getNodeValue();
                Node nApellidos = elementosComercial.getElementsByTagName("apellidos").item(0).getFirstChild();
                apellidos = nApellidos.getNodeValue();
                Node nDelegacion = elementosComercial.getElementsByTagName("delegacion").item(0).getFirstChild();
                delegacion = nDelegacion.getNodeValue();
                Node nTelefono = elementosComercial.getElementsByTagName("telefono").item(0).getFirstChild();
                telefono = nTelefono.getNodeValue();
                Node nEmailDelegacion = elementosComercial.getElementsByTagName("emailDelegacion").item(0).getFirstChild();
                emailDelegacion = nEmailDelegacion.getNodeValue();

                listComercial[i] = new Comercial(id, email, nombre, apellidos, delegacion, telefono, emailDelegacion);
            }
        }catch (Exception e){
            System.out.println("Error");
        }
        return listComercial;
    }

    public void cargarAssets(){
        this.productos = leeProductos("productos.xml");
        this.partners = leePartners("partners.xml");
        this.comerciales = leeComerciales("comerciales.xml");
    }


}















//nada que ver aquí

//...



class NullResolver implements EntityResolver {
    public InputSource resolveEntity(String publicId, String systemId) {
        return new InputSource(new StringReader(""));
    }
}