package org.example.satdwn.model;

public class Descripcion {
    private String cliente;
    private String fecha;
    private String cantidad;

    public Descripcion() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Descripcion{" +
                "cliente='" + cliente + '\'' +
                ", fecha='" + fecha + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
