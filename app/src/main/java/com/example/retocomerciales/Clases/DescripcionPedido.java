package com.example.retocomerciales.Clases;

public class DescripcionPedido {
    private int id;
    private String fecha, nombrePartner, prTotal;

    public DescripcionPedido(int id, String fecha, String nombrePartner, String prTotal) {
        this.id = id;
        this.fecha = fecha;
        this.nombrePartner = nombrePartner;
        this.prTotal = prTotal;
    }

    public int getId() {return id;}
    public String getFecha() {return fecha;}
    public String getNombrePartner() {return nombrePartner;}
    public String getPrTotal() {return prTotal;}
}
