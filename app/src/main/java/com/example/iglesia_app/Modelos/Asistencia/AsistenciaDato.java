package com.example.iglesia_app.Modelos.Asistencia;

public class AsistenciaDato {
    private int id;
    private String hora;
    private int idUsuario;
    private int idActividad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public AsistenciaDato(int id, String hora, int idUsuario, int idActividad) {
        this.id = id;
        this.hora = hora;
        this.idUsuario = idUsuario;
        this.idActividad=idActividad;
    }
}
