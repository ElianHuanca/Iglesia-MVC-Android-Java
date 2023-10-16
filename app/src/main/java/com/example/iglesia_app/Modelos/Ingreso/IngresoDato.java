package com.example.iglesia_app.Modelos.Ingreso;

public class IngresoDato {
    private int id;
    private String nombre;
    private Double monto;
    private int idActividad;

    public IngresoDato(int id, String nombre, Double monto, int idActividad) {
        this.id = id;
        this.nombre = nombre;
        this.monto = monto;
        this.idActividad = idActividad;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
}
