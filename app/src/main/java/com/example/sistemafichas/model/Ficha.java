package com.example.sistemafichas.model;

public class Ficha {
    private String codigo;
    private String nombre;
    private String categoria;
    private String tela;
    private String observaciones;

    public Ficha() {
    }

    public Ficha(String codigo, String nombre, String categoria, String tela, String observaciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tela = tela;
        this.observaciones = observaciones;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
