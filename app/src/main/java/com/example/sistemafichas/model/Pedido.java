package com.example.sistemafichas.model;

public class Pedido {

    private int id;
    private String clienteDocumento;
    private String fecha;
    private String estado;
    private double total;

    public Pedido() {
    }

    public Pedido(
            int id,
            String clienteDocumento,
            String fecha,
            String estado,
            double total
    ) {
        this.id = id;
        this.clienteDocumento = clienteDocumento;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    // ==============================
    // ID
    // ==============================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ==============================
    // DOCUMENTO DEL CLIENTE
    // ==============================

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    // ==============================
    // FECHA
    // ==============================

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // ==============================
    // ESTADO
    // ==============================

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ==============================
    // TOTAL
    // ==============================

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido #" + id +
                " - Cliente: " + clienteDocumento +
                " - Fecha: " + fecha +
                " - Estado: " + estado +
                " - Total: $" + total;
    }
}