/**
 * Esta clase, es un singelton. Es una clase que se instancia una sola vez y se puede acceder a esa instancia durante la ejecución.
 * A traves de esta linea, centralizamos la información y varios métodos de nuestra aplicación. Nos facilita el diseño y hace que sea más óptimo
 *
 */

package com.example.retocomerciales.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.retocomerciales.RetoComercialesSQLiteHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Datos {

    private static Datos datos;             //la propia instancia de la clase
    //atributos:
    private Producto[] productos;           //Array de los productos. se carga del xml
    private Partner[] partners;             //Array de los partners. se carga del xml
    private Comercial[] comerciales;        //Array de los comerciales. se carga del xml
    private Pedido pedido;                  //El pedido que se esté produciendo
    private int posComercial, posPartner;   //la posición del comercial y el partner en su respectivo array. Se inicializa en cada spinner que se modifique.
    private static Resources resources;     //para poder acceder a resources y cargar los xmls al instalar la aplicación.
    private File XML_FILE_LOCATION_PATH;    //carpeta de la memoria interna del movil.
    private SQLiteDatabase db;
    private boolean dbExist = false;

    private Datos(){}

    public void setMainActivityElements(Resources resources, Context context) {
        this.resources = resources;

        //RetoComercialesSQLiteHelper dbh = ;
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getWritableDatabase();

        this.XML_FILE_LOCATION_PATH = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        try {
            loadFilesFromAssetsToLocal();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Datos class error" , "Error a la hora de leer la información desde un XML");
        }
    }

    public static Datos getInstance() {
        if (datos == null){
            datos = new Datos();
        }
        return datos;
    }

    public void crearDB(){
        datos = new Datos();
        this.dbExist = true;
    }

    public boolean isDbExist() {return dbExist;}

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
            prod.ajustarExistencias();
            String sql= "UPDATE PRODUCTOS SET EXISTENCIAS = " +prod.getExistencias() + " WHERE COD_PRODUCTO = '"+prod.getCod() + "'";
                    db.execSQL(sql);
        }
        escribirPedidoDOM();
        //escribirProductoDOM();





       datos.insert(pedido, db);

    }

    /**
     * Métodos para lista de productos
     */


    public Producto[] getProductos() {return productos;}
    public Producto getProducto(int posicion) {
        return productos[posicion];
    }

    public Producto getProducto(String cod){
        for(Producto producto : productos){
            if (producto.getCod().equals(cod)){
                return producto;
            }
        }
        return null;
    }

    public void restaExistenciasCompra(int posicion, int existenciasRestadas) {
        productos[posicion].setExistenciasCompra(productos[posicion].getExistenciasCompra() - existenciasRestadas);
    }

    public void cargarExistencias() {
        for (Producto producto : productos) {
            producto.ajustarExistenciasCompra();
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

    public Partner getPartner(String id){
        for(Partner partner : partners){
            if (partner.getId().equals(id)){
                return partner;
            }
        }
        return null;
    }

    /**
     * métodos para comerciales
     */

    public Comercial getComercial(int pos) {
        return comerciales[pos];
    }

    public Comercial getComercial() {
        return comerciales[posComercial];
    }

    public Comercial getComercial(String id){
        for(Comercial comercial : comerciales){
            if (comercial.getId().equals(id)){
                return comercial;
            }
        }
        return null;
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
        String files [] = {"comerciales.xml", "partners.xml", "pedidos.xml", "productos.xml", "newpartners.xml"};
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
        escribirNewPartnerDOM(partner);
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

    private void escribirNewPartnerDOM(Partner partner) {
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
            File file = new File(XML_FILE_LOCATION_PATH, "newpartners.xml");
            Result result = new StreamResult(file);

            //agregar todos los elementos XML al elemento principal
            for (Partner element : leePartners("newpartners.xml")) {
                rootElement.appendChild(element.toElement(document));
            }
            rootElement.appendChild(partner.toElement(document));

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

            for (Pedido element : leePedidos("pedidos.xml")) {
                rootElement.appendChild(element.toElement(document));
            }
            rootElement.appendChild(pedido.toElement(document));

            //escribir el contenido de Document a un archivo local
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    private void escribirProductoDOM(){
        try {
            //generar el nuevo documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.newDocument();

            //generar el elemento principal
            org.w3c.dom.Element rootElement = document.createElementNS("PistachoPhone", "productos");
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

            String  usuario = "", password = "" , email= "", nombre = "", apellidos = "", delegacion = "", telefono = "", emailDelegacion = "";

            for (int i = 0; i < list.getLength(); i++) {
                org.w3c.dom.Element elementosComercial = (org.w3c.dom.Element) list.item(i);


                usuario = elementosComercial.getAttribute("usuario");
                password = elementosComercial.getAttribute("password");


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

                listComercial[i] = new Comercial(String.valueOf(i+1), usuario, password, email, nombre, apellidos, delegacion, telefono, emailDelegacion);
            }
        }catch (Exception e){
            System.out.println("Error");
        }
        return listComercial;
    }

    private Pedido[] leePedidos(String fileName) {
        Pedido[] listPedidos = null;

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(XML_FILE_LOCATION_PATH, fileName);
            Document document = builder.parse(new FileInputStream(file));
            Element root = document.getDocumentElement();


            NodeList list = document.getElementsByTagName("pedido");
            listPedidos = new Pedido[list.getLength()];

            String fecha = "", idPartner = "", idComercial = "";
            ArrayList<Linea> lineas;

            for (int i = 0; i < list.getLength(); i++) {
                Element elementosPedido = (Element) list.item(i);

                //fecha = elementosPedido.getAttributeNode("fecha").getTextContent();  //getAttribute("fecha").toString();
                fecha = elementosPedido.getAttribute("fecha");
                idPartner = elementosPedido.getAttribute("idpartner");
                idComercial = elementosPedido.getAttribute("idcomercial");

                listPedidos[i] = new Pedido(fecha, getPartner(idPartner), getComercial(idComercial));

                NodeList listLineas = elementosPedido.getElementsByTagName("linea");
                for(int j = 0; j < listLineas.getLength(); j++){
                    Element elementosLinea = (Element) listLineas.item(j);

                    Producto prod = getProducto(elementosLinea.getAttribute("codArticulo"));
                    int cantidad = Integer.parseInt(elementosLinea.getAttribute("cantidad"));

                    listPedidos[i].addLinea(new Linea(prod, cantidad));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return listPedidos;
    }

    //inserts

    public SQLiteDatabase getDb() {return db;}



    public void insert(Producto producto, SQLiteDatabase db){
        String sql= "INSERT INTO PRODUCTOS (cod_producto, nombre, descripcion, imagen, existencias, pr_unidad) values ( '"
                + producto.getCod() + "', '"  + producto.getNombre() + "', '"  + producto.getDescripcion() + "', '"  + producto.getImagen() + "', "  + producto.getExistencias() + ", "  + producto.getPr_unidad() +  " )";
        db.execSQL(sql);
    }
    public void insert(Comercial comercial, SQLiteDatabase db){
        String sql= "INSERT INTO COMERCIALES ( usuario, password, nombre, apellidos, email, delegacion, telefono_delegacion, email_delegacion) values ( '" +
                comercial.getUsuario() + "', '"  + comercial.getPassword() + "', '"  + comercial.getNombre() + "', '"  + comercial.getApellidos() + "', '" +
                comercial.getEmail() + "', '"  + comercial.getDelegacion() + "', '"  + comercial.getTelefonoDelegacion() + "', '"  + comercial.getEmailDelegacion() + "' )";
        db.execSQL(sql);
    }

    public void insert(Partner partner, SQLiteDatabase db){
        String sql= "INSERT INTO PARTNERS ( nombre, direccion, cif, telefono, email, id_comercial) values ( '" +
                partner.getNombre() + "', '"  + partner.getDireccion() + "', '"  + partner.getCIF() + "', '"  + partner.getTelefono() + "', '" +
                partner.getEmail() + "', '"  + partner.getIdComercial() + "')";
        db.execSQL(sql);
    }

    public void insert(Pedido pedido, SQLiteDatabase db){
        long id_pedido;

        //valores de los inserts (values en sql)
        ContentValues values = new ContentValues();
        values.put("fecha", pedido.getFecha());
        values.put("id_partner", pedido.getPartner().getId());
        values.put("id_comercial", pedido.getComercial().getId());

        id_pedido = db.insert("PEDIDOS", null, values);

        for (Linea linea : pedido.getLineas()){
            insert(linea,id_pedido, db);
        }
    }

    public void insert(Linea linea, long id_pedido, SQLiteDatabase db){
        String sql= "INSERT INTO LINEAS ( id_pedido, cod_producto, cantidad, pr_unidad) values ( " +
                id_pedido + ", '"  + linea.getProducto().getCod() + "', "  + linea.getCantidad() + ", "  + linea.getProducto().getPr_unidad() + ")";
        db.execSQL(sql);
    }




    public void insertAll(SQLiteDatabase db){
        for (Producto producto: productos){
            datos.insert(producto, db);
        }

        for (Comercial comercial: comerciales){
            datos.insert(comercial, db);
        }

        for (Partner partner: partners){
            datos.insert(partner, db);
        }

    }


    public void cargarAssets(){
        this.productos = leeProductos("productos.xml");
        this.partners = leePartners("partners.xml");
        this.comerciales = leeComerciales("comerciales.xml");
    }

    public Producto[] cargarProductosDesdeBD(Context context) {
        Producto[] listProducto = null;
        String cod = "", nombre = "", descripcion = "", imagen = "";
        float pr_unidad = 0f;
        int existencias = 0;
        int i = 0;
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PRODUCTOS ", null);
        if(c.moveToFirst()){
            do {
                cod = c.getString(0);
                nombre = c.getString(1);
                try{
                    descripcion = c.getString(2);
                }catch(Exception e){
                    descripcion = "";
                }
                imagen = c.getString(3);
                existencias = c.getInt(4);
                pr_unidad = c.getFloat(5);
                //listProducto[i] = new Producto(cod, nombre ,descripcion, imagen, pr_unidad, existencias);
                i+=1;
                System.out.println("----------------------------------------------------------------------------");
                System.out.println(cod + nombre + descripcion +imagen +pr_unidad+ existencias);
            } while(c.moveToNext());
        }
        c.close();
        return listProducto;
    }

}