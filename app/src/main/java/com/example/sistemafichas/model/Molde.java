package com.example.sistemafichas.model;

public class Molde {

    private int id;
    private String nombre;
    private String talla;
    private String tipoPrenda;
    private double precio;

    public Molde() {
    }

    public Molde(int id, String nombre, String talla,
                 String tipoPrenda, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.talla = talla;
        this.tipoPrenda = tipoPrenda;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}