package com.example.retocomerciales.Clases;

import android.content.res.Resources;
import android.os.Environment;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Datos {

    private static Datos datos;
    //atributos:
    private Producto[] productos;
    private Partner[] partners;
    private Comercial[] comerciales;
    private Pedido pedido;
    private int posComercial, posPartner;
    private static Resources resources;


    private Datos(Resources resources) {

        this.resources = resources;

        productos = new Producto[]{
                new Producto("PPB_", "PistachoB", "movil", 79.95f, 10),
                new Producto("PPA_", "PistachoA", "movil", 125.95f, 10),
                new Producto("PPA+", "PistachoA+", "movil", 153.45f, 10),
                new Producto("PPO_", "PistachoO", "movil", 279.95f, 10),
                new Producto("PPO+", "PistachoO+", "movil", 293.45f, 10),
                new Producto("PPod", "PistachoPods", "airpodsPistacho", 24.95f, 10),
                new Producto("Carg", "Cargador Pistacho", "cargadorPistacho", 12.34f, 10),
                new Producto("FPB_", "Funda PistachoB", "Funda diseñada para proteger de tu PistachoPhone Beta", "fundaPistacho", 7f, 10),
                new Producto("FPA_", "Funda PistachoA", "Funda diseñada para proteger de tu PistachoPhone Alfa", "fundaPistacho", 7f, 10),
                new Producto("FPA+", "Funda PistachoA+", "Funda diseñada para proteger de tu PistachoPhone Alfa+", "fundaPistacho", 8.54f, 10),
                new Producto("FPO_", "Funda PistachoO", "Funda diseñada para proteger de tu PistachoPhone Omega", "fundaPistacho", 8f, 10),
                new Producto("FPO+", "Funda PistachoO+", "Funda diseñada para proteger de tu PistachoPhone Omega+", "fundaPistacho", 9.54f, 10)
        };
        partners = new Partner[]{
                new Partner("1", "Cebanc", "Berio Pasealekua, 50, 20018 Donostia, Gipuzkoa", "A20045548", "Donostia", "943316900", "contacto@cebanc.com", "1"),
                new Partner("2", "Ibermática", "Mikeletegi Pasealekua, 5, 20009 Donostia, Gipuzkoa", "A20038915", "Donostia", "943413500", "contacto@ibermatica.com", "1"),
                new Partner("3", "Dosystem S.L.", "Sagardotegi Kalea, 1, 20160 Lasarte-Oria, SS", "A20040547", "Lasarte-Oria", "943369533", "contacto@dosystem.com", "2")
        };
        comerciales = new Comercial[]{
                new Comercial("1", "ikerperez@pistacho.com", "Iker", "Perez", "Albacete", "978645123", "pistachoAlbacete@pistacho.com"),
                new Comercial("2", "joncastander@pistacho.com", "Jon", "Castander", "Gipuzkoa", "943454320", "pistachoGipuzkoa@pistacho.com"),
                new Comercial("3", "mikelinsausti@pistacho.com", "Mikel", "Insausti", "Bizkaia", "945457512", "pistachoBizkaia@pistacho.com")
        };
    }

    private Datos() {
    }

    public static Datos getInstance(Resources resources) {
        if (datos == null) {
            datos = new Datos(resources);
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
            nombres[i] = this.comerciales[i].getNombre();
        }
        return nombres;
    }

    // Escritura en xml //

    //para poder escribir en raw
    private InputStream rawFileToChar(int fileId) throws IOException {
        InputStream is = resources.openRawResource(fileId);
        String str = null;
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        str = new String(buffer, 0, buffer.length, StandardCharsets.UTF_8);

        return new ByteArrayInputStream(str.getBytes());
    }


    public void escribirPartner(Partner p) throws JDOMException, IOException, ParserConfigurationException, SAXException {
        //Lee XML
        SAXBuilder builder = new SAXBuilder();
        File archivo = new File(Environment.getExternalStorageDirectory() + "/partners.xml");//ruta
        Document doc = (Document) builder.build(archivo);

        org.jdom2.Element root = (org.jdom2.Element) doc.getDocumentElement();

        org.jdom2.Element partner = new org.jdom2.Element("partner");
        root.addContent(partner);


        partner.setAttribute("id", p.getId());

        org.jdom2.Element nom = new org.jdom2.Element("nombre");
        partner.addContent(nom);
        nom.setText(p.getNombre());
        org.jdom2.Element dir = new org.jdom2.Element("direccion");
        partner.addContent(dir);
        dir.setText(p.getDireccion());
        org.jdom2.Element pob = new org.jdom2.Element("poblacion");
        partner.addContent(pob);
        pob.setText(p.getPoblacion());
        org.jdom2.Element CIF = new org.jdom2.Element("CIF");
        partner.addContent(CIF);
        CIF.setText(p.getCIF());
        org.jdom2.Element tel = new org.jdom2.Element("telefono");
        partner.addContent(tel);
        tel.setText(p.getTelefono());
        org.jdom2.Element mail = new org.jdom2.Element("email");
        partner.addContent(mail);
        mail.setText(p.getEmail());
        org.jdom2.Element id_comercial = new org.jdom2.Element("id_comercial");
        partner.addContent(id_comercial);
        id_comercial.setText("1");//cambiar


        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        outputter.output((org.jdom2.Document) doc, new FileWriter(archivo));
    }

    public void escribirPedido(Pedido p) throws JDOMException, IOException {
        //Lee XML
        SAXBuilder builder = new SAXBuilder();
        File archivo = new File(Environment.getExternalStorageDirectory() + "/pedidos.xml");//ruta
        Document doc = (Document) builder.build(archivo);

        //Obtiene nodo raiz
        Element root = (Element) doc.getDocumentElement();


        //Añade un nuevo nodo al nodo raiz
        org.jdom2.Element pedido = new org.jdom2.Element("pedido");

        root.addContent(pedido);


        //Añadir los elementos del partner.

        pedido.setAttribute("fecha", p.getFecha());
        pedido.setAttribute("idcomercial", p.getComercial().getId());
        pedido.setAttribute("idpartner", p.getPartner().getId());

        Double prTotal = 0.;
        for (Linea l : p.getLineas()) {
            org.jdom2.Element linea = new org.jdom2.Element("linea");
            pedido.addContent(linea);
            linea.setAttribute("codArticulo", l.getProducto().getCod());
            linea.setAttribute("cantidad", String.valueOf(l.getCantidad()));
            linea.setAttribute("precioLinea", String.valueOf(l.getPr_total()));
            prTotal += l.getPr_total();
        }

        pedido.setAttribute("precioTotal", String.valueOf(prTotal));
        //Crea un fichero XML
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        outputter.output((org.jdom2.Document) doc, new FileWriter(archivo));
    }

    // lectura xmls //

    public static Producto[] leeProductos() {
        Producto[] listProducto = null;

        try {
            SAXBuilder builder = new SAXBuilder();
            File xml = new File("productos.xml"); //error de ruta
            org.jdom2.Document document = builder.build(xml);
            org.jdom2.Element root = document.getRootElement();

            List<org.jdom2.Element> list = root.getChildren();
            listProducto = new Producto[list.size()];

            String cod = "", nombre = "", descripcion = "", imagen = "";
            float pr_unidad = 0f;
            int existencias = 0;

            for (int i = 0; i < list.size(); i++) {
                org.jdom2.Element producto = list.get(i);

                cod = producto.getAttributeValue("cod");
                nombre = producto.getChildTextTrim("nombre");
                descripcion = producto.getChildTextTrim("descripcion");
                imagen = producto.getChildTextTrim("imagen");


                try {
                    pr_unidad = Float.parseFloat(producto.getChildTextTrim("precio"));
                } catch (Exception e) {
                    pr_unidad = 0f;
                }

                try {
                    existencias = Integer.parseInt(producto.getChildTextTrim("existencias"));
                } catch (Exception e) {
                    existencias = 0;
                }

                if (descripcion == null) {
                    descripcion = "";
                }
                if (descripcion.length() == 0) {
                    listProducto[i] = new Producto(cod, nombre, imagen, pr_unidad, existencias);
                } else {
                    listProducto[i] = new Producto(cod, nombre, descripcion, imagen, pr_unidad, existencias);
                }
            }

        } catch (JDOMException | IOException e) {
            System.out.println("Error al encontrar el xml interno");
        }
        return listProducto;
    }

    public static Partner[] leePartners() {
        Partner[] listPartners;

        SAXBuilder builder = new SAXBuilder();

        File xml = new File("partners.xml"); //error de ruta
        org.jdom2.Document document = null;

        try {
            document = builder.build(xml);
        } catch (JDOMException | IOException e) {
            System.out.println("Error al encontrar el xml interno");
        }
        org.jdom2.Element root = document.getRootElement();

        List<org.jdom2.Element> list = root.getChildren();
        listPartners = new Partner[list.size()];

        for (int i = 0; i < list.size(); i++) {
            org.jdom2.Element partner = list.get(i);

            String id = "", nombre = "", direccion = "", poblacion = "", cif = "", telefono = "", email = "", idPartner = "";

            org.jdom2.Element campo = list.get(i);

            id = campo.getAttributeValue("id");
            nombre = campo.getChildTextTrim("nombre");
            direccion = campo.getChildTextTrim("direccion");
            poblacion = campo.getChildTextTrim("poblacion");
            cif = campo.getChildTextTrim("CIF");
            telefono = campo.getChildTextTrim("telefono");
            email = campo.getChildTextTrim("email");
            idPartner = campo.getChildTextTrim("idPartner");

            listPartners[i] = new Partner(id, nombre, direccion, poblacion, cif, telefono, email, idPartner);
        }
        return listPartners;
    }

    public static Comercial[] leeComerciales() {
        Comercial[] listComercial;

        SAXBuilder builder = new SAXBuilder();

        File xml = new File("comerciales.xml"); //error de ruta
        org.jdom2.Document document = null;

        try {
            document = builder.build(xml);
        } catch (JDOMException | IOException e) {
            System.out.println("Error al encontrar el xml interno");
        }
        org.jdom2.Element root = document.getRootElement();

        List<org.jdom2.Element> list = root.getChildren();
        listComercial = new Comercial[list.size()];

        for (int i = 0; i < list.size(); i++) {
            org.jdom2.Element partner = list.get(i);

            String id = "", email, nombre = "", apellidos = "", delegacion = "", telefono = "", emailDelegacion = "";

            Element campo = list.get(i);

            id = campo.getAttributeValue("id");
            email = campo.getChildTextTrim("email");
            nombre = campo.getChildTextTrim("nombre");
            apellidos = campo.getChildTextTrim("apellidos");
            delegacion = campo.getChildTextTrim("delegacion");
            telefono = campo.getChildTextTrim("telefono");
            emailDelegacion = campo.getChildTextTrim("emailDelegacion");

            listComercial[i] = new Comercial(id, email, nombre, apellidos, delegacion, telefono,  emailDelegacion);
        }
        return listComercial;
    }
}