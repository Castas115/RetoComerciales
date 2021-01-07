package com.example.retocomerciales.Clases;

import android.content.res.Resources;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
                new Partner("1", "Cebanc", "Berio Pasealekua, 50, 20018 Donostia, Gipuzkoa", "A20045548", "Donostia", "943316900", "contacto@cebanc.com"),
                new Partner("2", "Ibermática", "Mikeletegi Pasealekua, 5, 20009 Donostia, Gipuzkoa", "A20038915", "Donostia", "943413500", "contacto@ibermatica.com"),
                new Partner("3", "Dosystem S.L.", "Sagardotegi Kalea, 1, 20160 Lasarte-Oria, SS", "A20040547", "Lasarte-Oria", "943369533", "contacto@dosystem.com")
        };
        comerciales = new Comercial[]{
                new Comercial("ikerperez@pistacho.com", "Iker", "Perez", "Albacete", "978645123", "pistachoAlbacete@pistacho.com"),
                new Comercial("joncastander@pistacho.com", "Jon", "Castander", "Gipuzkoa", "943454320", "pistachoGipuzkoa@pistacho.com"),
                new Comercial("mikelinsausti@pistacho.com", "Mikel", "Insausti", "Bizkaia", "945457512", "pistachoBizkaia@pistacho.com")
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

    /* Escritura en xml */

    //para poder escribir en raw
    private InputStream rawFileToChar(int fileId) throws IOException {
        InputStream is = resources.openRawResource(fileId);
        String str = null;
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        str = new String(buffer, 0, buffer.length, StandardCharsets.UTF_8);

        return new ByteArrayInputStream(str.getBytes());
    }

    private void populateList(int fileId, List list, Class type) throws ParserConfigurationException, IOException, SAXException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //preparar el documento para poder leer de el
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(rawFileToChar(fileId));
        document.setXmlStandalone(true); //we might not need this
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName() != "#text") {
                list.add(type.getConstructor(Node.class).newInstance(nodeList.item(i)));
            }
        }
    }

    /*public void anadirPartner(int fileId, Partner p) throws JDOMException, IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(rawFileToChar(fileId));

        org.jdom2.Element root = (org.jdom2.Element) document.getDocumentElement();

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
        outputter.output(document, new FileWriter(rawFileToChar(fileId)));
        outputter.output(doc, new FileWriter(archivo));
    }*/

}