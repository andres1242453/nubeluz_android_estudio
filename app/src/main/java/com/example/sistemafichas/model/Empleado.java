package com.example.sistemafichas.model;

public class Empleado {

    private int id;
    private String nombre;
    private String documento;
    private String telefono;
    private String cargo;

    public Empleado() {
    }

    public Empleado(String nombre, String documento, String telefono, String cargo) {
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.cargo = cargo;
    }

    public Empleado(int id, String nombre, String documento,
                    String telefono, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.cargo = cargo;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}