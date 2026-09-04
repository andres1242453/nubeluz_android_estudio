package com.example.sistemafichas.model;

public class DetallePedido {

    private int id;
    private int pedidoId;
    private int moldeId;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetallePedido() {
    }

    public DetallePedido(int id, int pedidoId, int moldeId,
                         int cantidad, double precioUnitario) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.moldeId = moldeId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getMoldeId() {
        return moldeId;
    }

    public void setMoldeId(int moldeId) {
        this.moldeId = moldeId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    private void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}