package com.example.retocomerciales.Clases;

public class DescripcionPedido {
    private String fecha, nombrePartner, prTotal;

    public DescripcionPedido(String fecha, String nombrePartner, String prTotal) {
        this.fecha = fecha;
        this.nombrePartner = nombrePartner;
        this.prTotal = prTotal;
    }

    public String getFecha() {return fecha;}
    public String getNombrePartner() {return nombrePartner;}
    public String getPrTotal() {return prTotal;}
}
