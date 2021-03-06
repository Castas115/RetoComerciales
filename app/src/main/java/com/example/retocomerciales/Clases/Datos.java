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
import android.provider.Telephony;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Comercial comercial;
    private Pedido pedido;                  //El pedido que se esté produciendo
    private int posPartner;   //la posición del partner en su respectivo array. Se inicializa en cada spinner que se modifique.
    private static Resources resources;     //para poder acceder a resources y cargar los xmls al instalar la aplicación.
    private Context context;
    private File XML_FILE_LOCATION_PATH;    //carpeta de la memoria interna del movil.
    private SQLiteDatabase db;
    private ArrayList<DescripcionPedido> listPedidos;

    private Datos(){}

    public void setMainActivityElements(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
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

    //Dato posComercial
    /*public int getPosComercial() {
        return this.posComercial;
    }

    public void setPosComercial(int posComercial) {
        this.posComercial = posComercial;
    }
*/

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
            prod.ajustarExistencias();//ajustamos las existencias tanto en instancia como en la base de datos
            String sql= "UPDATE PRODUCTOS SET EXISTENCIAS = " +prod.getExistencias() + " WHERE COD_PRODUCTO = '"+prod.getCod() + "'";
                    db.execSQL(sql);
        }
        //escribirPedidoDOM();
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
    public Partner modifyPartner(int pos, String nombre, String direccion, String cif, String telefono, String email){
        this.partners[pos].setNombre(nombre);
        this.partners[pos].setDireccion(direccion);
        this.partners[pos].setPoblacion(cif);
        this.partners[pos].setCIF(telefono);
        this.partners[pos].setTelefono(email);
        return partners[pos];
    }

    public void addPartner(Partner partner){
        Partner[] _partners = new Partner[partners.length + 1];
        for (int i = 0; i < partners.length; i++){
            _partners[i] = partners[i];
        }
        _partners[_partners.length - 1] = partner;
        partners = _partners;
    }

    //métodos para comerciales


    public Comercial getComercial(){return comercial;}
    public void setComercial(int id){this.comercial = selectComercial(id);}


    public void setComercial(Comercial comercial) {
        this.comercial = comercial;
    }
    /*public Comercial getComercial(int pos) {
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
    public Comercial[] getComerciales() {return comerciales;}
*/

    //Datos de listaPedidos
    public ArrayList<DescripcionPedido> cargarListaPedidos(){
            this.listPedidos = new ArrayList<>();
            if (comercial != null) {
                this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getWritableDatabase();
                String sql = "SELECT PE.ID, PE.FECHA, PA.NOMBRE FROM PEDIDOS PE INNER JOIN PARTNERS PA ON PE.ID_PARTNER = PA.ID "
                        + " WHERE PE.id_comercial = " + Integer.parseInt(comercial.getId());
                Cursor c = db.rawQuery(sql, null);
                if (c.moveToFirst()) {
                    do {
                        int idPedido = c.getInt(0);
                        String fecha = c.getString(1);
                        String nomPartner = c.getString(2);

                        String sql2 = "SELECT sum(pr_unidad * cantidad) as prTotal FROM LINEAS WHERE ID_PEDIDO = " + idPedido; //no se como hacer esto
                        Cursor c2 = db.rawQuery(sql2, null);
                        float prTotal = c2.getColumnIndex("prTotal");
                        listPedidos.add(new DescripcionPedido(idPedido, fecha, nomPartner, String.valueOf(prTotal)));

                    } while (c.moveToNext());
                }
            }
        return listPedidos;
    }
    public void borrarPedido(int pos){

        db.execSQL("DELETE FROM LINEAS WHERE id = " + listPedidos.get(pos).getId());
        db.execSQL("DELETE FROM PEDIDOS WHERE id = " + listPedidos.get(pos).getId());
        this.listPedidos.remove(pos);
    }

    //public void listPedidosNull() {this.listPedidos = null;}

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

    /*public void escribirPartnerDOM(Partner partner) {
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
    }*/
    public void escribirNewPartnerDOM(Partner partner) {
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

    /*private void escribirPedidoDOM(){
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
    }*/
    /*private void escribirProductoDOM(){
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
    }*/

    private Producto[] leeProductos(String fileName) {
        Producto[] listProducto = null;

        try {
            DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(XML_FILE_LOCATION_PATH, fileName);
            org.w3c.dom.Document document = builder.parse(new FileInputStream(file));

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

    private void leeComerciales(String fileName) {

        try {

            DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(XML_FILE_LOCATION_PATH, fileName);
            org.w3c.dom.Document document = builder.parse(new FileInputStream(file));
            org.w3c.dom.Element root = document.getDocumentElement();


            NodeList list = document.getElementsByTagName("comercial");

            String  usuario, password , email, nombre, apellidos, delegacion, telefono, emailDelegacion;

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

                insert(new Comercial(String.valueOf(i+1), usuario, password, email, nombre, apellidos, delegacion, telefono, emailDelegacion), getDb());
            }
        }catch (Exception e){
            System.out.println("Error");
        }
    }

    /*private Pedido[] leePedidos(String fileName) {
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
    }*/

    //select

    public int loggedUser(){
        int id = -1;//por defecto si no encuentra ninguno seguira siendo -1
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getWritableDatabase();
        String sql= "SELECT * FROM COMERCIALES WHERE LOGGEADO = 1";
        Cursor c = db.rawQuery(sql, null);

        if(c.moveToFirst()){
            //c = db.rawQuery("SELECT * FROM COMERCIALES", null);
            //c.moveToFirst();
            //do{
            //    if(c.getInt(9) == 1){
                    id = c.getInt(0);
            //        break;
            //    }
            //}while (c.moveToNext());
        }
        return id;
    }

    public String[][] selectNombresComerciales(){
        String[][] nombresComerciales = null;

        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getWritableDatabase();
        String sql= "SELECT * FROM COMERCIALES";
        Cursor c = db.rawQuery(sql, null);
        nombresComerciales = new String[2][c.getCount()];
        if(c.moveToFirst()){
            int i = 0;
            do{
                nombresComerciales[0][i] = String.valueOf(c.getInt(0));
                nombresComerciales[1][i] = c.getString(3);
                i++;
            }while (c.moveToNext());
        }
        return nombresComerciales;
    }

    public Comercial selectComercial(int id){
        Comercial comercial = null;

        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getWritableDatabase();
        String sql= "SELECT * FROM COMERCIALES WHERE id = " + id;
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()){
                comercial = new Comercial(String.valueOf(c.getInt(0)), c.getString(1), c.getString(2), c.getString(5),
                        c.getString(3), c.getString(4), c.getString(6), c.getString(7), c.getString(8));
        }
        return comercial;
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
        //INSERT INTO PEDIDOS (FECHA,ID_PARTNER,ID_COMERCIAL) VALUES (...,...,...)
        ContentValues values = new ContentValues();
        values.put("fecha", pedido.getFecha());
        values.put("id_partner", pedido.getPartner().getId());
        values.put("id_comercial", pedido.getComercial().getId());

        id_pedido = db.insert("PEDIDOS", null, values);//Nos devuelve la ID recien creada por la base de datos del pedido

        for (Linea linea : pedido.getLineas()){
            insert(linea,id_pedido, db);//Insert normal con todas las lineas
        }
    }

    public void insert(Linea linea, long id_pedido, SQLiteDatabase db){
        String sql= "INSERT INTO LINEAS ( id_pedido, cod_producto, cantidad, pr_unidad) values ( " +
                id_pedido + ", '"  + linea.getProducto().getCod() + "', "  + linea.getCantidad() + ", "  + linea.getProducto().getPr_unidad() + ")";
        db.execSQL(sql);
    }




    public void insertAll(SQLiteDatabase db){
        //Hace los inserts de la informacion instanciada en Datos
        for (Producto producto: productos){
            datos.insert(producto, db);
        }
        /*
        for (Comercial comercial: comerciales){
            datos.insert(comercial, db);
        }*/

        for (Partner partner: partners){
            datos.insert(partner, db);
        }

    }

    //UPDATE
    public Comercial logginUser(String user, String password){
        Comercial comercial = null;
        int id;
        String usuario, contrasena, nombre, apellidos, email, delegacion, telefono_delegacion, email_delegacion;
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM COMERCIALES WHERE usuario = '" + user +"' AND password = '" + password + "'", null);

        if(c.moveToFirst()){
            comercial = new Comercial(String.valueOf(c.getInt(0)), c.getString(1), c.getString(2), c.getString(5),
                    c.getString(3), c.getString(4), c.getString(6), c.getString(7), c.getString(8));

            db.execSQL("UPDATE COMERCIALES SET loggeado = 1 where id = " + Integer.parseInt(comercial.getId()));
        }
        c.close();

        return comercial;
    }
    public void logoutUser(){
        String sql= "UPDATE COMERCIALES SET loggeado = 0 where loggeado = 1";
        db.execSQL(sql);
    }

    public void update (Partner partner){

        String sql= "UPDATE PARTNERS SET NOMBRE = '" + partner.getNombre() + "', DIRECCION= '" + partner.getDireccion() + "', CIF = '" + partner.getCIF() + "', TELEFONO= '" + partner.getTelefono() + "',  EMAIL = '" + partner.getEmail() + "', ID_COMERCIAL = " + Integer.parseInt(partner.getIdComercial()) + " where id = " + partner.getId();
        db.execSQL(sql);
    }

    //DELETE

   public void borrarPartner(String id){
        String sql= "DELETE FROM PARTNERS WHERE id = "+Integer.parseInt(id);
        db.execSQL(sql);
        for(int i = 0; i<this.partners.length; i++){
            if(partners[i].getId() == id){
                partners[i] = partners[partners.length -1];
                partners = Arrays.copyOf(partners, partners.length -1);
                break;
            }
        }

    }

    public void cargarAssets(){
        this.productos = leeProductos("productos.xml");
        this.partners = leePartners("partners.xml");
        leeComerciales("comerciales.xml");
    }

    public Producto[] cargarProductosDesdeBD() {
        Producto[] listProducto ;
        String cod, nombre, descripcion, imagen;
        float pr_unidad = 0f;
        int existencias = 0;
        int i = 0;
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM PRODUCTOS ", null);
        listProducto = new Producto[c.getCount()];
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
                listProducto[i] = new Producto(cod, nombre ,descripcion, imagen, pr_unidad, existencias);
                i+=1;
            } while(c.moveToNext());
        }
        c.close();
        return listProducto;
    }

    public Partner[] cargarPartnersDesdeBD() {
        Partner[] listPartner;
        String nombre, direccion, cif, telefono, email;
        int id, id_comercial;
        int i = 0;
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM PARTNERS ", null);
        listPartner = new Partner[c.getCount()];
        if(c.moveToFirst()){
            do {
                id = c.getInt(0);
                nombre = c.getString(1);
                direccion = c.getString(2);
                cif = c.getString(3);
                telefono = c.getString(4);
                email = c.getString(5);
                id_comercial = c.getInt(6);
                listPartner[i] = new Partner( String.valueOf(id), nombre ,direccion, cif,"" ,telefono, email, String.valueOf(id_comercial));
                i+=1;
            } while(c.moveToNext());
        }
        c.close();
        return listPartner;
    }

    public Comercial[] cargarComercialesDesdeBD() {
        Comercial[] listComercial;
        String usuario, password, nombre, apellidos, email, delegacion, telefono_delegacion, email_delegacion;
        int i =0, id;
        this.db = new RetoComercialesSQLiteHelper(context, "dbRetoComerciales", null, 1).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM COMERCIALES ", null);
        listComercial = new Comercial[c.getCount()];
        if(c.moveToFirst()){
            do {
                id = c.getInt(0);
                usuario = c.getString(1);
                password = c.getString(2);
                nombre = c.getString(3);
                apellidos = c.getString(4);
                email = c.getString(5);
                delegacion = c.getString(6);
                telefono_delegacion = c.getString(7);
                email_delegacion = c.getString(8);
                listComercial[i] = new Comercial(String.valueOf(id),usuario,password,email,nombre,apellidos,delegacion,telefono_delegacion,email_delegacion);
                i+=1;
            } while(c.moveToNext());
        }
        c.close();
        return listComercial;
    }

    public void cargarDatosDesdeBD(){
        this.productos = cargarProductosDesdeBD();
        this.partners = cargarPartnersDesdeBD();
        cargarComercialesDesdeBD(); //mirar esto
    }

}