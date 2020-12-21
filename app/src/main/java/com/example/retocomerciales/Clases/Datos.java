package com.example.retocomerciales.Clases;

public class Datos {

    private static Datos datos;
    //atributos:
    private Producto[] productos;
    private Partner[] partners;
    private Comercial[] comerciales;
    private Pedido pedido;
    private int posComercial;


    private Datos(){

        comerciales = new Comercial[]{
            new Comercial("jcastander@mlarramendi.com", "Jon", "Castander Illarramendi", "Guipuzcoa", "627739436", "delegGuipuzcoa@pistacho.es"),
            new Comercial("iperez@pistachito.com", "Iker", "Pérez Juez", "Navarra", "688847776", "delegNavarra@pistacho.es")
        };

        productos = new Producto[]{
                new Producto("PPB_", "PistachoB", "movil", 79.95f, 10),
                new Producto("PPA_", "PistachoA", "movil", 125.95f, 10),
                new Producto("PPA+", "PistachoA+", "movil", 153.45f, 10),
                new Producto("PPO_", "PistachoO", "movil", 279.95f, 10),
                new Producto("PPO+", "PistachoO+", "movil", 293.45f, 10),
                new Producto("PPod", "PistachoPods", "airpodsPistacho", 24.95f, 10),
                new Producto("Carg", "Cargador Pistacho", "cargadorPistacho", 12.34f, 10),
                new Producto("FPB_", "Funda PistachoB", "Funda diseñada para proteger de tu PistachoPhone Beta",  "fundaPistacho", 7f, 10),
                new Producto("FPA_", "Funda PistachoA", "Funda diseñada para proteger de tu PistachoPhone Alfa", "fundaPistacho", 7f, 10),
                new Producto("FPA+", "Funda PistachoA+", "Funda diseñada para proteger de tu PistachoPhone Alfa+", "fundaPistacho", 8.54f, 10),
                new Producto("FPO_", "Funda PistachoO", "Funda diseñada para proteger de tu PistachoPhone Omega", "fundaPistacho", 8f, 10),
                new Producto("FPO+", "Funda PistachoO+", "Funda diseñada para proteger de tu PistachoPhone Omega+", "fundaPistacho", 9.54f, 10)
        };
        partners = new Partner[]{
                new Partner("1", "Cebanc", "Berio Pasealekua, 50, 20018 Donostia, Gipuzkoa", "A20045548", "943316900", "contacto@cebanc.com"),
                new Partner("2", "Ibermática", "Mikeletegi Pasealekua, 5, 20009 Donostia, Gipuzkoa", "A20038915", "943413500", "contacto@ibermatica.com"),
                new Partner("3", "Dosystem S.L.", "Sagardotegi Kalea, 1, 20160 Lasarte-Oria, SS", "A20040547", "943369533", "contacto@dosystem.com")
        };
        comerciales = new Comercial[]{
                new Comercial("Ejemplo1" , "1", "1", "1" , "1", "1"),
                new Comercial("Ejemplo2" , "2", "2", "2" , "2", "2"),
                new Comercial("Ejemplo3" , "3", "3", "3" , "3", "3")
        };
    }

    public static Datos getInstance(){
        if (datos == null){
            datos = new Datos();
        }
        return datos;
    }

    //Dato posComercial
    public int getPosComercial(){
        return  this.posComercial;
    }
    public void setPosComercial(int posComercial){
        this.posComercial = posComercial;
    }
    //dato pedido


    public Pedido getPedido() {return pedido;}

    public void nuevoPedido(Comercial comercial){
        pedido = new Pedido(comercial);
    }

    /**
     * Métodos para lista de productos
     */
    public Producto[] getProductos(){
        return productos;
    }
    public Producto getProducto(int posicion){
        return productos[posicion];
    }

    public void restaExistenciasCompra(int posicion, int existenciasRestadas){
        productos[posicion].setExistenciasCompra(productos[posicion].getExistenciasCompra() - existenciasRestadas);
    }
    public void cargarExistencias(){
        for (Producto producto : productos){
            producto.setExistenciasCompra(producto.getExistencias());
        }
    }

    /**
     * Métodos para lista de Partners
     */
    public Partner[] getPartners(){
        return partners;
    }

    public Partner getPartner(int posicion){
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
}